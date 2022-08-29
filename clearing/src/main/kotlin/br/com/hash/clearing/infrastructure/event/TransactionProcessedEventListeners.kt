package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.transaction.TransactionProcessedEvent
import org.springframework.context.event.EventListener as SpringEventListener
import org.springframework.stereotype.Component

@Component
class TransactionProcessedEventListeners(
    private val metaAccountingEventListener: EventListener<TransactionProcessedEvent>,
    private val receivablesScheduleEventListener: EventListener<TransactionProcessedEvent>
) {

    @SpringEventListener
    fun handleEventInMetaAccounting(event: TransactionProcessedEvent) {
        metaAccountingEventListener.handleEvent(event)
    }

    @SpringEventListener
    fun handleEventInReceivablesSchedule(event: TransactionProcessedEvent) {
        receivablesScheduleEventListener.handleEvent(event)
    }
}
