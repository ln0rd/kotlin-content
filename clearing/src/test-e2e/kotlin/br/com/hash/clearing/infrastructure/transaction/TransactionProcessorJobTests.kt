package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.PaymentLinkData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.RevenueDetail
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.SplitDetail
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.output.receivables_schedule.TransactionProcessedEventListener
import br.com.hash.clearing.domain.transaction.ProcessableTransaction
import br.com.hash.clearing.domain.transaction.ProcessableTransactionRepository
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockkObject
import java.sql.SQLException
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class TransactionProcessorJobTests(
    processableTransactionRepository: ProcessableTransactionRepository,
    transactionProcessedEventListener: TransactionProcessedEventListener,
    transactionJobs: TransactionJobs,
) : DescribeSpec() {
    companion object {
        val mockWebServer = MockWebServer()

        init {
            mockWebServer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("PRICING_ENGINE_URL") { "http://localhost:${mockWebServer.port}" }
        }
    }

    init {
        describe("TransactionPreProcessor tests") {
            beforeTest {
                DatabaseState.reset()
            }

            afterSpec {
                runCatching {
                    mockWebServer.shutdown()
                }
            }

            it("should process successfully") {
                processableTransactionRepository.save(processableTransactions())

                val readyToProcessProcessableTransactions = processableTransactionRepository.findReadyToProcess()
                assertPreProcessed(readyToProcessProcessableTransactions)

                val jsonResponse = resourceAsText("pricing-engine/pricing-details.json")
                mockWebServer.enqueue(MockResponse().setBody(jsonResponse).addHeader("Content-Type", "application/json"))

                transactionJobs.transactionProcessorJob()

                val remainingProcessableTransactions = processableTransactionRepository.findReadyToProcess()
                assertTrue(remainingProcessableTransactions.isEmpty())
            }

            it("should rollback if event emitting fails") {
                processableTransactionRepository.save(processableTransactions())

                val readyToProcessProcessableTransactions = processableTransactionRepository.findReadyToProcess()
                assertPreProcessed(readyToProcessProcessableTransactions)

                val jsonResponse = resourceAsText("pricing-engine/pricing-details.json")
                mockWebServer.enqueue(MockResponse().setBody(jsonResponse).addHeader("Content-Type", "application/json"))

                mockkObject(transactionProcessedEventListener)
                every { transactionProcessedEventListener.handleEvent(any()) } throws SQLException()

                shouldThrow<Exception> { transactionJobs.transactionProcessorJob() }

                clearMocks(transactionProcessedEventListener)

                val remainingProcessableTransactions = processableTransactionRepository.findReadyToProcess()
                assertPreProcessed(remainingProcessableTransactions)
            }
        }
    }

    fun assertPreProcessed(processableTransactions: List<ProcessableTransaction>) {
        assertEquals(1, processableTransactions.size)
        assertEquals("1539985", processableTransactions[0].transactionId)
        assertEquals(ProcessableTransactionStatus.PREPROCESSED, processableTransactions[0].status)
    }

    fun processableTransactions(): List<ProcessableTransaction> {
        return listOf(
            ProcessableTransaction(
                transactionId = "1539985",
                status = ProcessableTransactionStatus.PREPROCESSED,
                transactionData = TransactionData(
                    id = "1539985",
                    hashCorrelationKey = "f1cd85a8-6e1c-43b5-977b-000054415049",
                    isoID = "606382a65b80110006d7f709",
                    merchantID = "6176e22e4ad7d40007d090ab",
                    merchantCategoryCode = "5912",
                    terminalID = "6178559dd8b89900075cb907",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "D450C90F-F10E-416D-A2F5-FA8AEAD8227C",
                        dateTime = OffsetDateTime.parse("2021-11-24T13:58:56-03:00"),
                        responseCode = "00",
                        authorizationCode = "150336",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "Ourocard Cartão de Débito",
                        numericCode = "047",
                        alphaCode = "OCD"
                    ),
                    dateTime = OffsetDateTime.parse("2021-11-24T13:58:56-03:00"),
                    transactionType = "purchase",
                    accountType = "debit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 2595,
                    currencyCode = "986",
                    installmentTransactionData = null,
                    CardholderData(
                        panFirstDigits = "516292",
                        panLastDigits = "8627",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "GRAND MOFF TARKIN",
                        verificationMethod = "offiline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
            ),
            ProcessableTransaction(
                transactionId = "N657N601FM01ZXCPBXYT57RXT8",
                status = ProcessableTransactionStatus.RECEIVED,
                transactionData = TransactionData(
                    id = "N657N601FM01ZXCPBXYT57RXT8",
                    hashCorrelationKey = "f1cd85a8-6e1c-43b5-977b-000054415049",
                    isoID = "2a660630110085b870906d7f",
                    merchantID = "26176e2e4ad7d4090ab0007d",
                    merchantCategoryCode = "5912",
                    terminalID = "75cb9076178559dd8b899000",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "C90FD450-0F1E-16D4-2AF5-27CFA828AEAD",
                        dateTime = OffsetDateTime.parse("2021-11-25T15:57:51-03:00"),
                        responseCode = "00",
                        authorizationCode = "510336",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "",
                        numericCode = "",
                        alphaCode = ""
                    ),
                    dateTime = OffsetDateTime.parse("2021-11-25T15:57:51-03:00"),
                    transactionType = "purchase",
                    accountType = "debit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 51295,
                    currencyCode = "986",
                    installmentTransactionData = null,
                    CardholderData(
                        panFirstDigits = "516216",
                        panLastDigits = "1627",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "BOBA FETT",
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
            ),
            ProcessableTransaction(
                transactionId = "57N1FM60NXCPB6017RXT8ZXYT5",
                status = ProcessableTransactionStatus.PROCESSED,
                transactionData = TransactionData(
                    id = "57N1FM60NXCPB6017RXT8ZXYT5",
                    hashCorrelationKey = "8f1cd5a8-6ce1-3b54-527b-512054415049",
                    isoID = "06302a687611008590b06d7f",
                    merchantID = "ae2e42617ad7d4090ab0d007",
                    merchantCategoryCode = "5912",
                    terminalID = "c07615cb97d8b8559d899512",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "FDC90450-10FE-446D-2BC1-67EA28AA8EAD",
                        dateTime = OffsetDateTime.parse("2021-12-02T15:15:31-03:00"),
                        responseCode = "00",
                        authorizationCode = "510336",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "Amex Cartão de Crédito",
                        numericCode = "006",
                        alphaCode = "ACC"
                    ),
                    dateTime = OffsetDateTime.parse("2021-12-02T15:15:31-03:00"),
                    transactionType = "purchase",
                    accountType = "credit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 99873,
                    currencyCode = "986",
                    installmentTransactionData = null,
                    CardholderData(
                        panFirstDigits = "516290",
                        panLastDigits = "8615",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "MACE WINDU",
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
                pricingDetails = PricingDetails(
                    transactionId = "57N1FM60NXCPB6017RXT8ZXYT5",
                    splitDetail = listOf(SplitDetail("ae2e42617ad7d4090ab0d007", 1, 99873, 2800)),
                    isoRevenueDetail = listOf(RevenueDetail(1, "ae2e42617ad7d4090ab0d007", 2800)),
                    hashRevenueDetail = listOf(RevenueDetail(1, "ae2e42617ad7d4090ab0d007", 700))
                )
            ),
        )
    }
}
