package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.transaction.ProcessableTransaction
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import java.time.OffsetDateTime

class FakeProcessableTransaction {

    fun createSimple(): ProcessableTransaction {
        return ProcessableTransaction(
            transactionData = FakeTransactionData().createDebitType(),
            pricingDetails = FakePricingDetails().createEmpty("1539985"),
            transactionId = "1539985",
            status = ProcessableTransactionStatus.RECEIVED
        )
    }

    fun expectedInProcessableTransactionAdapter(): ProcessableTransaction {
        return ProcessableTransaction(
            id = "f49a233e-9596-425c-8d45-b000062af36b",
            transactionData = FakeTransactionData().createDebitType(),
            pricingDetails = FakePricingDetails().createEmpty("1539985"),
            transactionId = "1539985",
            status = ProcessableTransactionStatus.RECEIVED,
            createdAt = OffsetDateTime.parse("2021-10-14T16:03:21-03:00"),
        )
    }

    fun expectedInTransactionReadEventListener(): ProcessableTransaction {
        return ProcessableTransaction(
            transactionData = FakeTransactionData().createDebitType(),
            transactionId = "1539985",
            status = ProcessableTransactionStatus.RECEIVED
        )
    }
}
