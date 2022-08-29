package br.com.hash.clearing.domain.model

data class SplitDetail(
    val merchantId: String,
    val installmentNumber: Int,
    val splitAmount: Int,
    val isoRevenueAmount: Int
)
