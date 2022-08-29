package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionData
import java.time.OffsetDateTime

data class ProcessableTransaction(
    val id: String? = null,
    val transactionData: TransactionData,
    val pricingDetails: PricingDetails? = null,
    val transactionId: String,
    val status: ProcessableTransactionStatus,
    val createdAt: OffsetDateTime? = null
)
