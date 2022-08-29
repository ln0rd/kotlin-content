package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import java.time.OffsetDateTime
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessableTransactionCrudRepository : CrudRepository<ProcessableTransactionEntity, String> {
    @Query(
        """
        from #{#entityName} e
       where e.status = :status
         and (owner = :owner or createdAt < :staleDateTime)
         and createdAt = (select max(createdAt) from #{#entityName} p where p.transactionId = e.transactionId)
        """
    )
    fun findByStatusAndOwner(
        status: ProcessableTransactionStatus,
        owner: String,
        staleDateTime: OffsetDateTime
    ): List<ProcessableTransactionEntity>
}
