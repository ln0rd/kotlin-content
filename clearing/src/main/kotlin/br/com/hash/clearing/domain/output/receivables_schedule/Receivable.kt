package br.com.hash.clearing.domain.output.receivables_schedule

import java.time.LocalDate

data class Receivable(
    val amount: Int,
    val holder: String,
    val installment: Int,
    val isoId: String? = null,
    val merchantId: String? = null,
    val originAt: LocalDate,
    val originFrom: String? = "clearing",
    val paymentNetwork: String,
    val settlementDate: LocalDate,
    val transactionId: String
) {
    fun withTwoDecimalPlaces(): Receivable {
        return copy(amount = amount / 1000)
    }
}
