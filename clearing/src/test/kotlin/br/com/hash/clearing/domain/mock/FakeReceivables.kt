package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import java.time.LocalDate

class FakeReceivables {

    fun createSimple(): Receivable {
        return Receivable(
            amount = 0,
            holder = "merchant",
            installment = 1,
            merchantId = "5ae0c5077e70c30004c907b2",
            originAt = LocalDate.parse("2021-10-14"),
            paymentNetwork = "VCD",
            settlementDate = LocalDate.parse("2021-10-14"),
            transactionId = "1539985"
        )
    }

    fun createSimpleList(): List<Receivable> {
        return listOf(
            Receivable(amount = 6666, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-10-14"), transactionId = "1539985"),
            Receivable(amount = 6666, holder = "merchant", installment = 2, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-11-14"), transactionId = "1539985"),
            Receivable(amount = 6668, holder = "merchant", installment = 3, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-12-14"), transactionId = "1539985"),
        )
    }

    fun createCreditPurchaseReversalList(): List<Receivable> {
        return listOf(
            Receivable(amount = -6666, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-02-28"), transactionId = "1539985"),
            Receivable(amount = -6666, holder = "merchant", installment = 2, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-03-31"), transactionId = "1539985"),
            Receivable(amount = -6668, holder = "merchant", installment = 3, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-04-30"), transactionId = "1539985"),
        )
    }

    fun createDebitExpectExampleList(): List<Receivable> {
        return listOf(
            Receivable(amount = 6666, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-10-15"), transactionId = "1539985")
        )
    }

    fun createCreditExpectExampleList(): List<Receivable> {
        return listOf(
            Receivable(amount = 6666, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-11-14"), transactionId = "1539985"),
            Receivable(amount = 6666, holder = "merchant", installment = 2, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2021-12-14"), transactionId = "1539985"),
            Receivable(amount = 6668, holder = "merchant", installment = 3, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2021-10-14"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-01-14"), transactionId = "1539985"),
        )
    }

    fun createCreditExpectInLastDayOfMonthList(): List<Receivable> {
        return listOf(
            Receivable(amount = 6666, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-02-28"), transactionId = "1539985"),
            Receivable(amount = 6666, holder = "merchant", installment = 2, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-03-31"), transactionId = "1539985"),
            Receivable(amount = 6668, holder = "merchant", installment = 3, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-01-31"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-04-30"), transactionId = "1539985"),
        )
    }

    fun createReceivableScheduleTest(): List<Receivable> {
        return listOf(
            Receivable(amount = 1000, holder = "merchant", installment = 1, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-02-01"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-03-01"), transactionId = "1539985"),
            Receivable(amount = 1000, holder = "merchant", installment = 2, merchantId = "5ae0c5077e70c30004c907b2", originAt = LocalDate.parse("2022-02-01"), paymentNetwork = "VCD", settlementDate = LocalDate.parse("2022-04-01"), transactionId = "1539985"),
        )
    }
}
