package br.com.hash.clearing.domain.model

data class PricingDetails(
    val transactionId: String,
    val isoRevenueDetail: List<RevenueDetail>,
    val hashRevenueDetail: List<RevenueDetail>,
    val splitDetail: List<SplitDetail>
)
