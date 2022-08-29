package br.com.hash.clearing.domain.input

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.model.TransactionData

class TransactionReader(private val eventPublisher: EventPublisher) : TransactionQueueReader {

    override fun read(transactionDataList: List<TransactionData>) {
        val transactionReadEvent = TransactionReadEvent(transactionDataList)
        eventPublisher.publish(transactionReadEvent)
    }
}
