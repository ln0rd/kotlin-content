package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionProcessResult

class TransactionProcessor(
    private val eventPublisher: EventPublisher,
    private val processableTransactionRepository: ProcessableTransactionRepository,
    private val pricingEngineGateway: PricingEngineGateway,
    logger: Logger
) {
    private val log = logger.withContext(TransactionProcessor::class)

    fun process() {
        val processableTransactions = processableTransactionRepository.findReadyToProcess()

        if (processableTransactions.isEmpty()) {
            log.warn("No processable transactions found to process")
            return
        }

        val updatedProcessableTransactions = updateFromPricingEngine(processableTransactions)

        if (updatedProcessableTransactions.isEmpty()) {
            log.warn("No pricing calculations found to process")
            return
        }

        processableTransactionRepository.save(updatedProcessableTransactions)

        emitTransactionProcessedEvent(updatedProcessableTransactions)
    }

    private fun updateFromPricingEngine(processableTransactions: List<ProcessableTransaction>): List<ProcessableTransaction> {
        val pricingDetails = fetchFromPricingEngine(processableTransactions)

        return processableTransactions
            .asSequence()
            .map { processableTransaction ->
                createProcessableTransactionResponsePair(processableTransaction, pricingDetails)
            }
            .filterNotNull()
            .map { (processableTransaction, pricingDetails) ->
                updateProcessableTransaction(processableTransaction, pricingDetails)
            }
            .toList()
    }

    private fun fetchFromPricingEngine(processableTransactions: List<ProcessableTransaction>): List<PricingDetails> {
        val transactionDataList = processableTransactions.map { processableTransaction ->
            processableTransaction.transactionData.withFiveDecimalPlaces()
        }
        return pricingEngineGateway.calculate(transactionDataList)
    }

    private fun createProcessableTransactionResponsePair(
        processableTransaction: ProcessableTransaction,
        pricingDetails: List<PricingDetails>
    ): Pair<ProcessableTransaction, PricingDetails>? {
        val fetchedPricingDetails = pricingDetails.find { p ->
            p.transactionId == processableTransaction.transactionId
        }

        return if (fetchedPricingDetails != null) {
            Pair(processableTransaction, fetchedPricingDetails)
        } else {
            log.warn("No pricing calculation found for transaction ${processableTransaction.transactionId}")
            null
        }
    }

    private fun updateProcessableTransaction(
        processableTransaction: ProcessableTransaction,
        pricingDetails: PricingDetails
    ): ProcessableTransaction {
        return processableTransaction.copy(
            pricingDetails = pricingDetails,
            status = ProcessableTransactionStatus.PROCESSED
        )
    }

    private fun emitTransactionProcessedEvent(processableTransactions: List<ProcessableTransaction>) {
        val transactionProcessResults = processableTransactions.map { processableTransaction ->
            TransactionProcessResult(
                processableTransaction.transactionData,
                checkNotNull(processableTransaction.pricingDetails)
            )
        }
        val event = TransactionProcessedEvent(transactionProcessResults)
        eventPublisher.publish(event)
    }
}
