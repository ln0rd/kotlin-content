package br.com.hash.clearing.domain.model

data class TransactionProcessResult(
    val transactionData: TransactionData,
    val pricingDetails: PricingDetails
)
