package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.TransactionData

class TransactionPreProcessor(
    private val processableTransactionRepository: ProcessableTransactionRepository,
    private val transactionServiceGateway: TransactionServiceGateway,
    logger: Logger
) {
    private val log = logger.withContext(TransactionPreProcessor::class)

    fun process() {
        val processableTransactions = processableTransactionRepository.findUnprocessed()

        if (processableTransactions.isEmpty()) {
            log.warn("No processable transactions found to pre-process")
            return
        }

        val updatedProcessableTransactions = updateFromTransactionService(processableTransactions)

        if (updatedProcessableTransactions.isEmpty()) {
            log.warn("No transactions found in transaction service")
            return
        }

        processableTransactionRepository.save(updatedProcessableTransactions)
    }

    private fun updateFromTransactionService(processableTransactions: List<ProcessableTransaction>): List<ProcessableTransaction> {
        val fetchedTransactionDatas = fetchFromTransactionService(processableTransactions)

        return processableTransactions
            .asSequence()
            .map { processableTransaction ->
                createProcessableTransactionResponsePair(processableTransaction, fetchedTransactionDatas)
            }
            .filterNotNull()
            .map { (processableTransaction, fetchedTransactionData) ->
                updateTransactionData(processableTransaction, fetchedTransactionData)
            }
            .filter { transactionData ->
                transactionData.hasPaymentNetworkData()
            }
            .map { transactionData ->
                ProcessableTransaction(
                    transactionId = transactionData.id,
                    transactionData = transactionData,
                    status = ProcessableTransactionStatus.PREPROCESSED
                )
            }
            .toList()
    }

    private fun fetchFromTransactionService(processableTransactions: List<ProcessableTransaction>): List<TransactionData> {
        val ids = processableTransactions.mapNotNull { processableTransaction -> processableTransaction.transactionId }
        return transactionServiceGateway.findByIds(ids)
    }

    private fun createProcessableTransactionResponsePair(
        processableTransaction: ProcessableTransaction,
        fetchedTransactionDatas: List<TransactionData>
    ): Pair<ProcessableTransaction, TransactionData>? {
        val fetchedTransactionData = fetchedTransactionDatas.find { r -> r.id == processableTransaction.transactionId }

        return if (fetchedTransactionData != null) {
            Pair(processableTransaction, fetchedTransactionData)
        } else {
            log.warn("No transaction data found in transaction service for transaction ${processableTransaction.transactionId}")
            null
        }
    }

    private fun updateTransactionData(
        processableTransaction: ProcessableTransaction,
        fetchedTransactionData: TransactionData
    ): TransactionData {
        return processableTransaction.transactionData.copy(paymentNetworkData = fetchedTransactionData.paymentNetworkData)
    }
}
