package br.com.hash.clearing.domain.event

interface EventPublisher {
    fun publish(event: Event)
}
