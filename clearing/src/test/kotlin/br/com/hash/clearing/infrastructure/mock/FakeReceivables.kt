package br.com.hash.clearing.infrastructure.mock

import br.com.hash.clearing.infrastructure.output.receivables_schedule.Receivable
import br.com.hash.clearing.infrastructure.output.receivables_schedule.Receivables
import java.time.LocalDate

class FakeReceivables {

    fun createExpectedInAdapterTest(): Receivables {
        return Receivables(
            listOf(
                Receivable(
                    amount = 1000,
                    holder = "merchant",
                    installment = 1,
                    merchantId = "5ae0c5077e70c30004c907b2",
                    originAt = LocalDate.parse("2022-02-01"),
                    paymentNetwork = "VCD",
                    settlementDate = LocalDate.parse("2022-03-01"),
                    transactionId = "1539985"
                ),
                Receivable(
                    amount = 1000,
                    holder = "merchant",
                    installment = 2,
                    merchantId = "5ae0c5077e70c30004c907b2",
                    originAt = LocalDate.parse("2022-02-01"),
                    paymentNetwork = "VCD",
                    settlementDate = LocalDate.parse("2022-04-01"),
                    transactionId = "1539985"
                )
            )
        )
    }
}
