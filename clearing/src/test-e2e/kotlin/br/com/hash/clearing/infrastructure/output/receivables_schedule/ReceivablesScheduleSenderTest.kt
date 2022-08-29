package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.InstallmentTransactionData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.RevenueDetail
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.SplitDetail
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleSender
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.DescribeSpec
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class ReceivablesScheduleSenderTest(
    private val processableReceivablesRepository: ProcessableReceivablesRepository,
    private val receivablesScheduleSender: ReceivablesScheduleSender,
    private val jdbcTemplate: JdbcTemplate
) : DescribeSpec() {
    companion object {
        private val mockWebServer = MockWebServer()

        init {
            mockWebServer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("RECEIVABLE_SCHEDULE_URL") { "http://localhost:${mockWebServer.port}" }
        }
    }

    init {
        describe("Receivable Schedule gateway tests") {
            afterSpec {
                runCatching {
                    mockWebServer.shutdown()
                }
            }

            DatabaseState.reset()
            createProcessableReceivablesInDatabase()

            it("We need send the list receivable from the two processableReceivables in database with WAITING_TO_SEND status") {
                val response = resourceAsText("receivable-schedule/responseReceivableScheduleSenderTest.json")
                mockWebServer.enqueue(MockResponse().setBody(response).addHeader("Content-Type", "application/json"))
                mockWebServer.enqueue(MockResponse().setBody(response).addHeader("Content-Type", "application/json"))
                receivablesScheduleSender.send()

                val recordedRequest: RecordedRequest = mockWebServer.takeRequest()
                val receivablesFromBody = DefaultObjectMapper().readValue<Receivables>(recordedRequest.body.readUtf8())

                assertEquals(3, receivablesFromBody.receivables.size)

                assertEquals(6666, receivablesFromBody.receivables[0].amount)
                assertEquals("merchant", receivablesFromBody.receivables[0].holder)
                assertEquals(null, receivablesFromBody.receivables[0].isoId)
                assertEquals(1, receivablesFromBody.receivables[0].installment)
                assertEquals("7778880c-9999-4ecf-957b-1965e4172cea", receivablesFromBody.receivables[0].merchantId)
                assertEquals(OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[0].originAt)
                assertEquals("ECC", receivablesFromBody.receivables[0].paymentNetwork)
                assertEquals(OffsetDateTime.parse("2021-12-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[0].settlementDate)
                assertEquals("188a0l16-22e6-4f1a-870c-6f8e2d53545h", receivablesFromBody.receivables[0].transactionId)

                assertEquals(6666, receivablesFromBody.receivables[1].amount)
                assertEquals("merchant", receivablesFromBody.receivables[1].holder)
                assertEquals(null, receivablesFromBody.receivables[1].isoId)
                assertEquals(2, receivablesFromBody.receivables[1].installment)
                assertEquals("7778880c-9999-4ecf-957b-1965e4172cea", receivablesFromBody.receivables[1].merchantId)
                assertEquals(OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[1].originAt)
                assertEquals("ECC", receivablesFromBody.receivables[1].paymentNetwork)
                assertEquals(OffsetDateTime.parse("2022-01-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[1].settlementDate)
                assertEquals("188a0l16-22e6-4f1a-870c-6f8e2d53545h", receivablesFromBody.receivables[1].transactionId)

                assertEquals(6668, receivablesFromBody.receivables[2].amount)
                assertEquals("merchant", receivablesFromBody.receivables[2].holder)
                assertEquals(null, receivablesFromBody.receivables[2].isoId)
                assertEquals(3, receivablesFromBody.receivables[2].installment)
                assertEquals("7778880c-9999-4ecf-957b-1965e4172cea", receivablesFromBody.receivables[2].merchantId)
                assertEquals(OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[2].originAt)
                assertEquals("ECC", receivablesFromBody.receivables[2].paymentNetwork)
                assertEquals(OffsetDateTime.parse("2022-02-16T17:14:06-03:00").toLocalDate(), receivablesFromBody.receivables[2].settlementDate)
                assertEquals("188a0l16-22e6-4f1a-870c-6f8e2d53545h", receivablesFromBody.receivables[2].transactionId)

                it("After sent to receivable schedule it can't be sent again") {
                    val processableReceivablesNotSent: List<ProcessableReceivables> =
                        processableReceivablesRepository.findNotSent()

                    assertEquals(0, processableReceivablesNotSent.size)
                }
            }
        }
    }

    private fun createProcessableReceivablesInDatabase() {
        val processableReceivable = listOf(
            ProcessableReceivables(
                transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            ),
            ProcessableReceivables(
                transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h",
                pricingDetails = createPricingDetails(),
                transactionData = createFakeTransactionData(),
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables()
            )
        )

        processableReceivablesRepository.save(processableReceivable)
    }

    private fun createFakeTransactionData(): TransactionData {
        return TransactionData(
            id = "188a0l16-22e6-4f1a-870c-6f8e2d53545h",
            hashCorrelationKey = "7142eaec-abfd-457d-a8cb-000054415049",
            isoID = "1111f364-2222-4040-ab41-b461adfa74fb",
            merchantID = "7778880c-9999-4ecf-957b-1965e4172cea",
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
            amount = 20000000,
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
            installmentTransactionData = InstallmentTransactionData(installmentCount = 3, installmentQualifier = null),
            referenceTransactionId = null
        )
    }

    private fun createPricingDetails(): PricingDetails {
        return PricingDetails(
            transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h",
            isoRevenueDetail = listOf(
                RevenueDetail(
                    installmentNumber = 1,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                ),
                RevenueDetail(
                    installmentNumber = 2,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                ),
                RevenueDetail(
                    installmentNumber = 3,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                )
            ),
            hashRevenueDetail = listOf(
                RevenueDetail(
                    installmentNumber = 1,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                ),
                RevenueDetail(
                    installmentNumber = 2,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                ),
                RevenueDetail(
                    installmentNumber = 3,
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    amount = 0
                )
            ),
            splitDetail = listOf(
                SplitDetail(
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    installmentNumber = 1,
                    splitAmount = 0,
                    isoRevenueAmount = 6666
                ),
                SplitDetail(
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    installmentNumber = 2,
                    splitAmount = 0,
                    isoRevenueAmount = 6666
                ),
                SplitDetail(
                    merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                    installmentNumber = 3,
                    splitAmount = 0,
                    isoRevenueAmount = 6668
                )
            )
        )
    }

    private fun createReceivables(): List<Receivable> {
        return listOf(
            Receivable(
                amount = 6666,
                holder = "merchant",
                installment = 1,
                isoId = null,
                merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                originAt = OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(),
                paymentNetwork = "ECC",
                settlementDate = OffsetDateTime.parse("2021-12-16T17:14:06-03:00").toLocalDate(),
                transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h"
            ),
            Receivable(
                amount = 6666,
                holder = "merchant",
                installment = 2,
                isoId = null,
                merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                originAt = OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(),
                paymentNetwork = "ECC",
                settlementDate = OffsetDateTime.parse("2022-01-16T17:14:06-03:00").toLocalDate(),
                transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h"
            ),
            Receivable(
                amount = 6668,
                holder = "merchant",
                installment = 3,
                isoId = null,
                merchantId = "7778880c-9999-4ecf-957b-1965e4172cea",
                originAt = OffsetDateTime.parse("2021-11-16T17:14:06-03:00").toLocalDate(),
                paymentNetwork = "ECC",
                settlementDate = OffsetDateTime.parse("2022-02-16T17:14:06-03:00").toLocalDate(),
                transactionId = "188a0l16-22e6-4f1a-870c-6f8e2d53545h"
            )
        )
    }
}
