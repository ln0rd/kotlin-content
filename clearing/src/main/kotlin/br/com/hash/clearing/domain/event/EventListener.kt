package br.com.hash.clearing.domain.event

interface EventListener<T : Event> {
    fun handleEvent(event: T)
}
