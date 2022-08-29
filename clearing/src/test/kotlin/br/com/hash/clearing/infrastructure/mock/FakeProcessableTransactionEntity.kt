package br.com.hash.clearing.infrastructure.mock

import br.com.hash.clearing.domain.mock.FakePricingDetails
import br.com.hash.clearing.domain.mock.FakeTransactionData
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import br.com.hash.clearing.infrastructure.transaction.Instance
import br.com.hash.clearing.infrastructure.transaction.ProcessableTransactionEntity
import java.time.OffsetDateTime

class FakeProcessableTransactionEntity {

    private val mapper = DefaultObjectMapper()
    private val transactionData = FakeTransactionData().createDebitType()
    private val transactionDataJson = mapper.writer().writeValueAsString(transactionData)
    private val pricingDetails = mapper.writer().writeValueAsString(FakePricingDetails().createEmpty(transactionData.id))

    fun createSimple(): ProcessableTransactionEntity {
        return ProcessableTransactionEntity(
            id = "f49a233e-9596-425c-8d45-b000062af36b",
            transactionData = transactionDataJson,
            pricingDetails = pricingDetails,
            transactionId = "1539985",
            status = ProcessableTransactionStatus.RECEIVED,
            createdAt = OffsetDateTime.parse("2021-10-14T16:03:21-03:00"),
            owner = "c7becd56-8038-4385-9f90-e7762849ff44"
        )
    }

    fun createSimpleExpectedInTransactionRepositoryParameters(): ProcessableTransactionEntity {
        return ProcessableTransactionEntity(
            id = null,
            transactionData = transactionDataJson,
            pricingDetails = pricingDetails,
            transactionId = "1539985",
            status = ProcessableTransactionStatus.RECEIVED,
            createdAt = null,
            owner = Instance.INSTANCE_ID
        )
    }
}
