package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.ProcessableTransaction
import br.com.hash.clearing.domain.transaction.ProcessableTransactionRepository
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.OffsetDateTime
import org.springframework.stereotype.Service

@Service
class ProcessableTransactionRepositoryAdapter(
    private val processableTransactionCrudRepository: ProcessableTransactionCrudRepository
) : ProcessableTransactionRepository {
    val mapper = DefaultObjectMapper()

    override fun findUnprocessed(): List<ProcessableTransaction> {
        return findByStatus(ProcessableTransactionStatus.RECEIVED)
    }

    override fun findReadyToProcess(): List<ProcessableTransaction> {
        return findByStatus(ProcessableTransactionStatus.PREPROCESSED)
    }

    private fun findByStatus(status: ProcessableTransactionStatus): List<ProcessableTransaction> {
        val staleDateTime = OffsetDateTime.now().minusHours(1)
        val processableTransactionEntities = processableTransactionCrudRepository.findByStatusAndOwner(
            status, Instance.INSTANCE_ID, staleDateTime
        )
        return createProcessableTransactionDomain(processableTransactionEntities)
    }

    override fun save(transactionDataList: List<ProcessableTransaction>): List<ProcessableTransaction> {
        val processableTransactionsSaved =
            processableTransactionCrudRepository.saveAll(createProcessableTransactionEntity(transactionDataList))
        return createProcessableTransactionDomain(processableTransactionsSaved.toList())
    }

    private fun createProcessableTransactionEntity(processableTransactionDomainList: List<ProcessableTransaction>): List<ProcessableTransactionEntity> {
        return processableTransactionDomainList.map { processableTransaction ->
            ProcessableTransactionEntity(
                transactionId = processableTransaction.transactionId,
                transactionData = mapper.writer().writeValueAsString(processableTransaction.transactionData),
                pricingDetails = mapper.writer().writeValueAsString(processableTransaction.pricingDetails),
                status = processableTransaction.status,
                owner = Instance.INSTANCE_ID
            )
        }
    }

    private fun createProcessableTransactionDomain(processableTransactionEntityList: List<ProcessableTransactionEntity>): List<ProcessableTransaction> {
        return processableTransactionEntityList.map { processableTransaction ->
            ProcessableTransaction(
                id = processableTransaction.id,
                transactionData = mapper.readValue(processableTransaction.transactionData),
                pricingDetails = processableTransaction.pricingDetails?.let { pricingDetails -> mapper.readValue(pricingDetails) },
                transactionId = processableTransaction.transactionId,
                status = processableTransaction.status,
                createdAt = processableTransaction.createdAt
            )
        }
    }
}
