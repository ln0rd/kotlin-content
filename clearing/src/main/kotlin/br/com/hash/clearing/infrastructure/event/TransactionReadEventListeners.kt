package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.input.TransactionReadEvent
import org.springframework.context.event.EventListener as SpringEventListener
import org.springframework.stereotype.Component

@Component
class TransactionReadEventListeners(private val transactionReadEventListener: EventListener<TransactionReadEvent>) {

    @SpringEventListener
    fun handleEvent(event: TransactionReadEvent) {
        transactionReadEventListener.handleEvent(event)
    }
}
