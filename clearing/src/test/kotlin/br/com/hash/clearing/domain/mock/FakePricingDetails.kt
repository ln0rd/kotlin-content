package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.model.PricingDetails

class FakePricingDetails {

    fun createEmpty(transactionId: String): PricingDetails {
        return PricingDetails(
            transactionId = transactionId,
            isoRevenueDetail = listOf(),
            hashRevenueDetail = listOf(),
            splitDetail = listOf()
        )
    }

    fun createSimple(transactionId: String): PricingDetails {
        return PricingDetails(
            transactionId = transactionId,
            isoRevenueDetail = FakeRevenueDetail().createSimpleList(),
            hashRevenueDetail = FakeRevenueDetail().createSimpleList(),
            splitDetail = FakeSplitDetail().createSimpleList()
        )
    }

    fun createCreditPurchaseReversal(transactionId: String): PricingDetails {
        return PricingDetails(
            transactionId = transactionId,
            isoRevenueDetail = FakeRevenueDetail().createCreditPurchaseReversalExampleList(),
            hashRevenueDetail = FakeRevenueDetail().createCreditPurchaseReversalExampleList(),
            splitDetail = FakeSplitDetail().createCreditPurchaseReversalList()
        )
    }

    fun createCreditSimple(transactionId: String): PricingDetails {
        return PricingDetails(
            transactionId = transactionId,
            isoRevenueDetail = FakeRevenueDetail().createCreditExampleList(),
            hashRevenueDetail = FakeRevenueDetail().createCreditExampleList(),
            splitDetail = FakeSplitDetail().createCreditSimpleList()
        )
    }
}
