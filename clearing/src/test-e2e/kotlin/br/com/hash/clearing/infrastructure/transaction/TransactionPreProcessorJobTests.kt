package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.PaymentLinkData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.transaction.ProcessableTransaction
import br.com.hash.clearing.domain.transaction.ProcessableTransactionRepository
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import io.kotest.core.spec.style.DescribeSpec
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class TransactionPreProcessorJobTests(
    processableTransactionRepository: ProcessableTransactionRepository,
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
            registry.add("TRANSACTION_SERVICE_URL") { "http://localhost:${mockWebServer.port}" }
        }
    }

    init {
        describe("TransactionPreProcessor tests") {
            DatabaseState.reset()

            afterSpec {
                runCatching {
                    mockWebServer.shutdown()
                }
            }

            it("successful processing") {
                processableTransactionRepository.save(processableTransactions())

                val receivedProcessableTransactions = processableTransactionRepository.findUnprocessed()
                assertEquals(1, receivedProcessableTransactions.size)
                assertEquals("01FMN657XCPBN601ZRXT8XYT57", receivedProcessableTransactions[0].transactionId)
                assertNotNull(receivedProcessableTransactions[0].createdAt)

                val jsonResponse = resourceAsText("transaction-service/transaction-events-by-id-response.json")
                mockWebServer.enqueue(MockResponse().setBody(jsonResponse).addHeader("Content-Type", "application/json"))

                transactionJobs.transactionPreProcessorJob()

                val requestPath = mockWebServer.takeRequest(5, TimeUnit.SECONDS)?.path
                assertEquals("/transaction-events/01FMN657XCPBN601ZRXT8XYT57", requestPath)

                val remainingProcessableTransactions = processableTransactionRepository.findUnprocessed()
                assertTrue(remainingProcessableTransactions.isEmpty())
            }
        }
    }

    fun processableTransactions(): List<ProcessableTransaction> {
        return listOf(
            ProcessableTransaction(
                transactionId = "01FMN657XCPBN601ZRXT8XYT57",
                status = ProcessableTransactionStatus.RECEIVED,
                transactionData = TransactionData(
                    id = "01FMN657XCPBN601ZRXT8XYT57",
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
                        name = "",
                        numericCode = "",
                        alphaCode = ""
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
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
            ),
            ProcessableTransaction(
                transactionId = "N657N601FM01ZXCPBXYT57RXT8",
                status = ProcessableTransactionStatus.PREPROCESSED,
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
        )
    }
}
