package br.com.hash.clearing.domain.output.receivables_schedule

import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionData

data class ProcessableReceivables(
    val id: String? = null,
    val transactionId: String,
    val pricingDetails: PricingDetails,
    val transactionData: TransactionData,
    val status: ProcessableReceivablesStatus,
    val receivables: List<Receivable>
)
