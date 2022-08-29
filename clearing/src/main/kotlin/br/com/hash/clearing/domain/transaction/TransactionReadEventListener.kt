package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.input.TransactionReadEvent
import br.com.hash.clearing.domain.model.TransactionData

class TransactionReadEventListener(private val processableTransactionRepository: ProcessableTransactionRepository) :
    EventListener<TransactionReadEvent> {

    override fun handleEvent(event: TransactionReadEvent) {
        val transactionDataList = event.transactionDataList

        processableTransactionRepository.save(createListOfProcessableTransaction(transactionDataList))
    }

    private fun createListOfProcessableTransaction(transactionDataList: List<TransactionData>): List<ProcessableTransaction> {
        return transactionDataList.map { transactionData ->
            ProcessableTransaction(
                transactionData = transactionData,
                transactionId = transactionData.id,
                status = ProcessableTransactionStatus.RECEIVED
            )
        }
    }
}
