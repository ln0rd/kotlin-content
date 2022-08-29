package br.com.hash.clearing.infrastructure.output.receivables_schedule

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Receivable(
    @JsonProperty("amount")
    val amount: Int,

    @JsonProperty("holder")
    val holder: String,

    @JsonProperty("installment")
    val installment: Int,

    @JsonProperty("iso_id")
    val isoId: String? = null,

    @JsonProperty("merchant_id")
    val merchantId: String? = null,

    @JsonProperty("origin_at")
    val originAt: LocalDate,

    @JsonProperty("origin_from")
    val originFrom: String? = "clearing",

    @JsonProperty("payment_network")
    val paymentNetwork: String,

    @JsonProperty("settlement_date")
    val settlementDate: LocalDate,

    @JsonProperty("transaction_id")
    val transactionId: String
)
