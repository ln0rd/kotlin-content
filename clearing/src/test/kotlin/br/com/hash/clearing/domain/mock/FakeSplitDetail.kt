package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.model.SplitDetail

class FakeSplitDetail {

    fun createSimple(): SplitDetail {
        return SplitDetail(
            merchantId = "5ae0c5077e70c30004c907b2",
            installmentNumber = 1,
            splitAmount = 1,
            isoRevenueAmount = 1,
        )
    }

    fun createSimpleList(): List<SplitDetail> {
        return listOf(
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 1, splitAmount = 6666000, isoRevenueAmount = 0)
        )
    }

    fun createCreditSimpleList(): List<SplitDetail> {
        return listOf(
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 1, splitAmount = 6666000, isoRevenueAmount = 0),
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 2, splitAmount = 6666000, isoRevenueAmount = 0),
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 3, splitAmount = 6668000, isoRevenueAmount = 0)
        )
    }

    fun createCreditPurchaseReversalList(): List<SplitDetail> {
        return listOf(
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 1, splitAmount = -6666000, isoRevenueAmount = 0),
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 2, splitAmount = -6666000, isoRevenueAmount = 0),
            SplitDetail(merchantId = "5ae0c5077e70c30004c907b2", installmentNumber = 3, splitAmount = -6668000, isoRevenueAmount = 0)
        )
    }
}
