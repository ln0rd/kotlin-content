package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus

class FakeProcessableReceivables {

    fun createSimple(): ProcessableReceivables {
        return ProcessableReceivables(
            id = "d49a233f-8596-325c-2d46-b100065aLE0c",
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createSimple(FakeTransactionData().createDebitType().id),
            transactionData = FakeTransactionData().createDebitType(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createSimpleList()
        )
    }

    fun createCreditReversalPurchaseType(): ProcessableReceivables {
        return ProcessableReceivables(
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createCreditPurchaseReversal(FakeTransactionData().createCreditReversalPurchaseType().id),
            transactionData = FakeTransactionData().createCreditReversalPurchaseType(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createCreditPurchaseReversalList()
        )
    }

    fun createCreditInLastDayOfMonth(): ProcessableReceivables {
        return ProcessableReceivables(
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditTypeInLastDayOfMonth().id),
            transactionData = FakeTransactionData().createCreditTypeInLastDayOfMonth(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createCreditExpectInLastDayOfMonthList()
        )
    }

    fun createDebitExpectExample(): ProcessableReceivables {
        return ProcessableReceivables(
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createSimple(FakeTransactionData().createDebitType().id),
            transactionData = FakeTransactionData().createDebitType(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createDebitExpectExampleList()
        )
    }

    fun createCreditSimple(): ProcessableReceivables {
        return ProcessableReceivables(
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createCreditSimple(FakeTransactionData().createDebitType().id),
            transactionData = FakeTransactionData().createCreditType(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createSimpleList()
        )
    }

    fun createCreditExpectExample(): ProcessableReceivables {
        return ProcessableReceivables(
            transactionId = "1539985",
            pricingDetails = FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditType().id),
            transactionData = FakeTransactionData().createCreditType(),
            status = ProcessableReceivablesStatus.WAITING_TO_SEND,
            receivables = FakeReceivables().createCreditExpectExampleList()
        )
    }

    fun createSimpleList(): List<ProcessableReceivables> {
        return listOf(createSimple(), createSimple())
    }

    fun createDebitTransactionExpectExampleList(): List<ProcessableReceivables> {
        return listOf(createDebitExpectExample(), createDebitExpectExample())
    }

    fun createCreditSimpleList(): List<ProcessableReceivables> {
        return listOf(createCreditSimple(), createCreditSimple())
    }

    fun createCreditTransactionExpectExampleList(): List<ProcessableReceivables> {
        return listOf(createCreditExpectExample(), createCreditExpectExample())
    }

    fun createCreditInLastDayOfMonthList(): List<ProcessableReceivables> {
        return listOf(createCreditInLastDayOfMonth(), createCreditInLastDayOfMonth())
    }

    fun createCreditReversalPurchaseTypeList(): List<ProcessableReceivables> {
        return listOf(createCreditReversalPurchaseType(), createCreditReversalPurchaseType())
    }
}
