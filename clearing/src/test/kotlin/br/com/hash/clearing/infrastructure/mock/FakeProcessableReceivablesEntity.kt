package br.com.hash.clearing.infrastructure.mock

import br.com.hash.clearing.domain.mock.FakePricingDetails
import br.com.hash.clearing.domain.mock.FakeReceivables
import br.com.hash.clearing.domain.mock.FakeTransactionData
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import br.com.hash.clearing.infrastructure.output.receivables_schedule.ProcessableReceivablesEntity
import br.com.hash.clearing.infrastructure.transaction.Instance
import java.time.OffsetDateTime

class FakeProcessableReceivablesEntity {

    private val mapper = DefaultObjectMapper()
    private val transactionData = FakeTransactionData().createDebitType()
    private val transactionDataJson = mapper.writer().writeValueAsString(transactionData)
    private val pricingDetails = mapper.writer().writeValueAsString(FakePricingDetails().createSimple(transactionData.id))
    private val receivablesJson = mapper.writer().writeValueAsString(FakeReceivables().createSimpleList())

    fun createSimple(): ProcessableReceivablesEntity {
        return ProcessableReceivablesEntity(
            id = "d49a233f-8596-325c-2d46-b100065aLE0c",
            transactionData = transactionDataJson,
            pricingDetails = pricingDetails,
            transactionId = transactionData.id,
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = receivablesJson,
            createdAt = OffsetDateTime.parse("2021-10-14T16:03:21-03:00"),
            owner = "c7becd56-8038-4385-9f90-e7762849ff44"
        )
    }

    fun createExpectedInParameter(): ProcessableReceivablesEntity {
        return ProcessableReceivablesEntity(
            id = null,
            transactionData = transactionDataJson,
            pricingDetails = pricingDetails,
            transactionId = transactionData.id,
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = receivablesJson,
            createdAt = null,
            owner = Instance.INSTANCE_ID
        )
    }

    fun createSimpleList(): List<ProcessableReceivablesEntity> {
        return listOf(createSimple(), createSimple())
    }

    fun createExpectedInParameterList(): List<ProcessableReceivablesEntity> {
        return listOf(createExpectedInParameter(), createExpectedInParameter())
    }
}
