package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import br.com.hash.clearing.infrastructure.transaction.Instance
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.OffsetDateTime
import org.springframework.stereotype.Service

@Service
class ProcessableReceivablesRepositoryAdapter(private val processableReceivablesCrudRepository: ProcessableReceivablesCrudRepository) : ProcessableReceivablesRepository {

    private val mapper = DefaultObjectMapper()

    override fun findNotSent(): List<ProcessableReceivables> {
        return findByStatus(ProcessableReceivablesStatus.WAITING_TO_SEND)
    }

    private fun findByStatus(status: ProcessableReceivablesStatus): List<ProcessableReceivables> {
        val staleDateTime = OffsetDateTime.now().minusHours(1)
        val processableReceivablesEntity: List<ProcessableReceivablesEntity> = processableReceivablesCrudRepository.findByStatusAndOwner(
            status, Instance.INSTANCE_ID, staleDateTime
        )
        return createProcessablesReceivablesDomain(processableReceivablesEntity)
    }

    override fun save(processableReceivables: List<ProcessableReceivables>): List<ProcessableReceivables> {
        val processablesReceivablesCreated =
            processableReceivablesCrudRepository.saveAll(createProcessablesReceivablesEntity(processableReceivables))
        return createProcessablesReceivablesDomain(processablesReceivablesCreated.toList())
    }

    private fun createProcessablesReceivablesEntity(processableReceivables: List<ProcessableReceivables>): List<ProcessableReceivablesEntity> {
        return processableReceivables.map { processableReceivable ->
            ProcessableReceivablesEntity(
                transactionData = mapper.writer().writeValueAsString(processableReceivable.transactionData),
                pricingDetails = mapper.writer().writeValueAsString(processableReceivable.pricingDetails),
                transactionId = processableReceivable.transactionId,
                status = processableReceivable.status,
                receivables = mapper.writer().writeValueAsString(processableReceivable.receivables),
                owner = Instance.INSTANCE_ID
            )
        }
    }

    private fun createProcessablesReceivablesDomain(processableReceivablesEntity: List<ProcessableReceivablesEntity>): List<ProcessableReceivables> {
        return processableReceivablesEntity.map { processableReceivableEntity ->
            ProcessableReceivables(
                id = processableReceivableEntity.id,
                transactionData = mapper.readValue(processableReceivableEntity.transactionData),
                pricingDetails = mapper.readValue(processableReceivableEntity.pricingDetails),
                transactionId = processableReceivableEntity.transactionId,
                status = processableReceivableEntity.status,
                receivables = mapper.readValue(processableReceivableEntity.receivables)
            )
        }
    }
}
