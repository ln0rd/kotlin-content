package br.com.hash.clearing.domain.model

data class RevenueDetail(
    val installmentNumber: Int,
    val merchantId: String,
    val amount: Int
)
