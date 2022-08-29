package br.com.hash.clearing.domain.output.meta_accounting

class MetaAccountingSender(
    private val metaAccountingGateway: MetaAccountingGateway,
    private val outboxRepository: OutboxRepository
) {
    fun send() {
        outboxRepository.findUnprocessed()

        // TODO implement

        metaAccountingGateway.send()
    }
}
