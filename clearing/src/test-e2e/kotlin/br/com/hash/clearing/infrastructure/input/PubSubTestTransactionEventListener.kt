package br.com.hash.clearing.infrastructure.input

import br.com.hash.clearing.domain.input.TransactionReadEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PubSubTestTransactionEventListener {
    var event: TransactionReadEvent? = null

    @EventListener
    fun handleEvent(event: TransactionReadEvent) {
        this.event = event
    }
}
