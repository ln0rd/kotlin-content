package br.com.hash.clearing.domain.output.receivables_schedule

import java.time.LocalDate
import java.time.OffsetDateTime

object BaseSettlementDate {
    fun defineBaseSettlementDate(transactionDateTime: OffsetDateTime, installmentNumber: Int, accountType: String): LocalDate {
        if (accountType == "credit") {
            return transactionDateTime.plusMonths((installmentNumber).toLong()).toLocalDate()
        }
        return transactionDateTime.plusDays(1).toLocalDate()
    }
}
