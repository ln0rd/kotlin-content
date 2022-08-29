package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.model.RevenueDetail

class FakeRevenueDetail {

    fun createSimple(): RevenueDetail {
        return RevenueDetail(
            installmentNumber = 1,
            merchantId = "5ae0c5077e70c30004c907b2",
            amount = 1
        )
    }

    fun createSimpleList(): List<RevenueDetail> {
        return listOf(
            RevenueDetail(installmentNumber = 1, merchantId = "5ae0c5077e70c30004c907b2", amount = 0)
        )
    }

    fun createCreditExampleList(): List<RevenueDetail> {
        return listOf(
            RevenueDetail(installmentNumber = 1, merchantId = "5ae0c5077e70c30004c907b2", amount = 0),
            RevenueDetail(installmentNumber = 2, merchantId = "5ae0c5077e70c30004c907b2", amount = 0),
            RevenueDetail(installmentNumber = 3, merchantId = "5ae0c5077e70c30004c907b2", amount = 0)
        )
    }

    fun createCreditPurchaseReversalExampleList(): List<RevenueDetail> {
        return listOf(
            RevenueDetail(installmentNumber = 1, merchantId = "5ae0c5077e70c30004c907b2", amount = 0),
            RevenueDetail(installmentNumber = 2, merchantId = "5ae0c5077e70c30004c907b2", amount = 0),
            RevenueDetail(installmentNumber = 3, merchantId = "5ae0c5077e70c30004c907b2", amount = 0)
        )
    }
}
