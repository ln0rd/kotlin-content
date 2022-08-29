package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.event.Event
import br.com.hash.clearing.domain.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(private val applicationEventPublisher: ApplicationEventPublisher) : EventPublisher {
    override fun publish(event: Event) {
        applicationEventPublisher.publishEvent(event)
    }
}
