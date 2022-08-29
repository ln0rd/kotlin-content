package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import java.time.OffsetDateTime
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessableReceivablesCrudRepository : CrudRepository<ProcessableReceivablesEntity, String> {
    @Query(
        """
        from #{#entityName} e
       where e.status = :status
         and (owner = :owner or createdAt < :staleDateTime)
         and createdAt = (select max(createdAt) from #{#entityName} p where p.transactionId = e.transactionId)
        """
    )
    fun findByStatusAndOwner(
        status: ProcessableReceivablesStatus,
        owner: String,
        staleDateTime: OffsetDateTime
    ): List<ProcessableReceivablesEntity>
}
