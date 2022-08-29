package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.TransactionServiceGateway
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import io.kotest.core.spec.style.DescribeSpec
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class HttpTransactionServiceGatewayTests(
    transactionServiceGateway: TransactionServiceGateway
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
        describe("Transaction Service tests") {
            afterSpec {
                runCatching {
                    mockWebServer.shutdown()
                }
            }

            it("Successful request") {
                val jsonResponse = resourceAsText("transaction-service/transaction-events-by-id-response.json")
                mockWebServer.enqueue(MockResponse().setBody(jsonResponse).addHeader("Content-Type", "application/json"))

                val transactionDataList = transactionServiceGateway.findByIds(listOf("01FMN657XCPBN601ZRXT8XYT57"))

                assertEquals(1, transactionDataList.size)

                val transactionData = transactionDataList[0]

                assertNotNull(transactionData)
                assertEquals("01FMN657XCPBN601ZRXT8XYT57", transactionData.id)
                assertEquals("7142eaec-abfd-457d-a8cb-000054415049", transactionData.hashCorrelationKey)
                assertEquals("606382a65b80110006d7f709", transactionData.isoID)
                assertEquals("6176e2174ad7d40007d09048", transactionData.merchantID)
                assertEquals("5912", transactionData.merchantCategoryCode)
                assertEquals("6177ff0a8e5eb6000756ae67", transactionData.terminalID)
                assertEquals("pagseguro", transactionData.authorizerData.name)
                assertEquals("0D7CCD4D-CC95-4217-877A-30F8D7356FFA", transactionData.authorizerData.uniqueID)
                assertEquals(OffsetDateTime.parse("2021-11-16T17:14:06-03:00"), transactionData.authorizerData.dateTime)
                assertEquals("00", transactionData.authorizerData.responseCode)
                assertEquals("499252", transactionData.authorizerData.authorizationCode)
                assertEquals("163562222", transactionData.authorizerData.specificData.affiliationID)
                assertEquals("Elo Cartão de Crédito", transactionData.paymentNetworkData.name)
                assertEquals("008", transactionData.paymentNetworkData.numericCode)
                assertEquals("ECC", transactionData.paymentNetworkData.alphaCode)
                assertEquals(OffsetDateTime.parse("2021-11-16T17:14:06-03:00"), transactionData.dateTime)
                assertEquals("purchase", transactionData.transactionType)
                assertEquals("credit", transactionData.accountType)
                assertEquals(true, transactionData.approved)
                assertEquals(false, transactionData.crossBorder)
                assertEquals("icc", transactionData.entryMode)
                assertEquals(7390, transactionData.amount)
                assertEquals("986", transactionData.currencyCode)
                assertEquals("650516", transactionData.cardholderData.panFirstDigits)
                assertEquals("1234", transactionData.cardholderData.panLastDigits)
                assertEquals("OBI-WAN KENOBI", transactionData.cardholderData.cardholderName)
                assertEquals("online-pin", transactionData.cardholderData.verificationMethod)
                assertEquals("BRA", transactionData.cardholderData.issuerCountryCode)
                assertEquals("hash-pos", transactionData.captureChannelData?.name)
                assertNull(transactionData.captureChannelData?.paymentLinkData?.creationTimestamp)
            }

            it("Non existent transaction id") {
                mockWebServer.enqueue(MockResponse().setResponseCode(404))

                val transactionDataList = transactionServiceGateway.findByIds(listOf("non-existent-id"))

                assertEquals(0, transactionDataList.size)
            }
        }
    }
}
