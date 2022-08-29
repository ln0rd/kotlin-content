package br.com.hash.clearing.infrastructure.output.meta_accounting

import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.output.meta_accounting.OutboxRepository
import org.springframework.stereotype.Repository

/**
 * This class should be changed to a Spring Data implementation
 */
@Repository
class MetaAccountingOutboxRepository : OutboxRepository {
    override fun findUnprocessed(): List<TransactionData> {
        // TODO Not yet implemented
        return listOf()
    }

    override fun save(transactionDataList: List<TransactionData>) {
    }
}
