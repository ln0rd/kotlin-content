package br.com.hash.clearing.domain.output.meta_accounting

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.transaction.TransactionProcessedEvent

class TransactionProcessedEventListener(private val outboxRepository: OutboxRepository) :
    EventListener<TransactionProcessedEvent> {

    override fun handleEvent(event: TransactionProcessedEvent) {
        val transactionProcessResults = event.transactionProcessResults

        // TODO implement process to turn a transactionDataList list in a meta account object to sending

        val transactionDataList = transactionProcessResults.map { transactionProcessResult ->
            transactionProcessResult.transactionData
        }
        outboxRepository.save(transactionDataList)
    }
}
