package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.RevenueDetail
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.SplitDetail
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import io.kotest.core.spec.style.DescribeSpec
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class ProcessableReceivablesRepositoryAdapterTests(
    private val processableReceivablesRepositoryAdapter: ProcessableReceivablesRepositoryAdapter,
    private val jdbcTemplate: JdbcTemplate
) : DescribeSpec() {
    init {
        describe("Processable Receivables Repository Adapter tests") {
            DatabaseState.reset()

            createProcessableReceivablesInDatabase()
            updateProcessableReceivablesWithDifferentOwner()
            updateProcessableReceivablesWithStaleCreatedAtAndDifferentOwner()

            it("should find by status RECEIVED with current owner or stale created_at") {
                val processableReceivables = processableReceivablesRepositoryAdapter.findNotSent().sortedBy { it.transactionId }
                assertEquals(3, processableReceivables.size)
                assertEquals("277c0a16-97e6-4f9a-952c-6f8e2d53545e", processableReceivables[0].transactionId)
                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, processableReceivables[0].status)
                assertEquals("287c0a16-9999-4g2a-952c-6f8ehd53545e", processableReceivables[1].transactionId)
                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, processableReceivables[1].status)
                assertEquals("afb1a70d-c8f9-4487-a40c-53b435e41cbc", processableReceivables[2].transactionId)
                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, processableReceivables[2].status)
            }
        }
    }

    private fun createProcessableReceivablesInDatabase() {
        val processableReceivable = listOf(
            ProcessableReceivables(
                transactionId = "afb1a70d-c8f9-4487-a40c-53b435e41cbc",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            ),
            ProcessableReceivables(
                transactionId = "277c0a16-97e6-4f9a-952c-6f8e2d53545e",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            ),
            ProcessableReceivables(
                transactionId = "277c0a16-97e6-4f9a-952c-6f8e2d53545e",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.SENT,
                receivables = createReceivables()
            ),
            ProcessableReceivables(
                transactionId = "227c0a16-8888-4f0a-952c-6fpe2d53545a",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            ),
            ProcessableReceivables(
                transactionId = "287c0a16-9999-4g2a-952c-6f8ehd53545e",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            )
        )

        processableReceivablesRepositoryAdapter.save(processableReceivable)
    }

    private fun updateProcessableReceivablesWithDifferentOwner() {
        val sql = """
                update processable_receivables
                set owner = 'f7840379-ddc5-4d0e-a3b7-5a161f5c59b6'
                where transaction_id = '227c0a16-8888-4f0a-952c-6fpe2d53545a'
                """
        jdbcTemplate.update(sql)
    }

    private fun updateProcessableReceivablesWithStaleCreatedAtAndDifferentOwner() {
        val sql = """
                update processable_receivables
                set
                    created_at = '2022-02-14T12:03:21-03:00',
                    owner = 'abc40379-adc1-0d0e-c3b1-5a161f5c59b6'
                where transaction_id = '287c0a16-9999-4g2a-952c-6f8ehd53545e'
                """
        jdbcTemplate.update(sql)
    }

    private fun createFakeTransactionData(): TransactionData {
        return TransactionData(
            id = "01FMN657XCPBN601ZRXT8XYT57",
            hashCorrelationKey = "7142eaec-abfd-457d-a8cb-000054415049",
            isoID = "606382a65b80110006d7f709",
            merchantID = "6176e2174ad7d40007d09048",
            merchantCategoryCode = "5912",
            terminalID = "6177ff0a8e5eb6000756ae67",
            authorizerData = AuthorizerData(
                name = "pagseguro",
                uniqueID = "0D7CCD4D-CC95-4217-877A-30F8D7356FFA",
                dateTime = OffsetDateTime.parse("2021-11-16T17:14:06-03:00"),
                responseCode = "00",
                authorizationCode = "499252",
                specificData = SpecificData(
                    affiliationID = "163562222",
                )
            ),
            paymentNetworkData = PaymentNetworkData(
                name = "Elo Cartão de Crédito",
                numericCode = "008",
                alphaCode = "ECC"
            ),
            dateTime = OffsetDateTime.parse("2021-11-16T17:14:06-03:00"),
            transactionType = "purchase",
            accountType = "credit",
            approved = true,
            crossBorder = false,
            entryMode = "icc",
            amount = 7390,
            currencyCode = "986",
            cardholderData = CardholderData(
                panFirstDigits = "650516",
                panLastDigits = "1234",
                cardholderName = "OBI-WAN KENOBI",
                verificationMethod = "online-pin",
                issuerCountryCode = "BRA",
                cardExpirationDate = null,
                panSequenceNumber = null
            ),
            captureChannelData = CaptureChannelData(
                name = "hash-pos",
                paymentLinkData = null
            ),
            antifraudData = null,
            installmentTransactionData = null,
            referenceTransactionId = null
        )
    }

    private fun createPricingDetails(): PricingDetails {
        return PricingDetails(
            transactionId = "01FMN657XCPBN601ZRXT8XYT57",
            isoRevenueDetail = listOf(
                RevenueDetail(
                    installmentNumber = 1,
                    merchantId = "6176e2174ad7d40007d09048",
                    amount = 0
                )
            ),
            hashRevenueDetail = listOf(
                RevenueDetail(
                    installmentNumber = 1,
                    merchantId = "6176e2174ad7d40007d09048",
                    amount = 0
                )
            ),
            splitDetail = listOf(
                SplitDetail(
                    merchantId = "6176e2174ad7d40007d09048",
                    installmentNumber = 1,
                    splitAmount = 0,
                    isoRevenueAmount = 0
                )
            )
        )
    }

    private fun createReceivables(): List<Receivable> {
        return listOf(
            Receivable(
                amount = 0,
                holder = "merchant",
                installment = 1,
                isoId = null,
                merchantId = "6176e2174ad7d40007d09048",
                originAt = OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(),
                paymentNetwork = "ECC",
                settlementDate = OffsetDateTime.parse("2021-12-16T17:14:06-03:00").toLocalDate(),
                transactionId = "01FMN657XCPBN601ZRXT8XYT57"
            )
        )
    }
}
