package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.model.TransactionProcessResult

class FakeTransactionProcessResults {

    fun createSimple(): TransactionProcessResult {
        return TransactionProcessResult(
            transactionData = FakeTransactionData().createDebitType(),
            pricingDetails = FakePricingDetails().createSimple(transactionId = FakeTransactionData().createDebitType().id)
        )
    }

    fun createCreditReversalPurchaseType(): TransactionProcessResult {
        return TransactionProcessResult(
            transactionData = FakeTransactionData().createCreditReversalPurchaseType(),
            pricingDetails = FakePricingDetails().createCreditPurchaseReversal(transactionId = FakeTransactionData().createCreditReversalPurchaseType().id)
        )
    }

    fun createCreditTransactionType(): TransactionProcessResult {
        return TransactionProcessResult(
            transactionData = FakeTransactionData().createCreditType(),
            pricingDetails = FakePricingDetails().createCreditSimple(transactionId = FakeTransactionData().createCreditType().id)
        )
    }

    fun createCreditTransactionTypeInLastDayOfMonth(): TransactionProcessResult {
        return TransactionProcessResult(
            transactionData = FakeTransactionData().createCreditTypeInLastDayOfMonth(),
            pricingDetails = FakePricingDetails().createCreditSimple(transactionId = FakeTransactionData().createCreditTypeInLastDayOfMonth().id)
        )
    }

    fun createSimpleList(): List<TransactionProcessResult> {
        return listOf(
            createSimple(),
            createSimple()
        )
    }

    fun createCreditTransactionTypeList(): List<TransactionProcessResult> {
        return listOf(
            createCreditTransactionType(),
            createCreditTransactionType()
        )
    }

    fun createCreditTransactionTypeInLastDayOfMonthList(): List<TransactionProcessResult> {
        return listOf(
            createCreditTransactionTypeInLastDayOfMonth(),
            createCreditTransactionTypeInLastDayOfMonth()
        )
    }

    fun createTransactionPurchaseReversalCreditTypeList(): List<TransactionProcessResult> {
        return listOf(
            createCreditReversalPurchaseType(),
            createCreditReversalPurchaseType()
        )
    }
}
