package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.transaction.PricingEngineGateway
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.DescribeSpec
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class HttpPricingEngineGatewayTests(
    pricingEngineGateway: PricingEngineGateway
) : DescribeSpec() {
    companion object {
        private val mockWebServer = MockWebServer()

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
        describe("Pricing engine gateway tests") {
            afterSpec {
                runCatching {
                    mockWebServer.shutdown()
                }
            }

            it("Successful request") {
                val jsonResponse = resourceAsText("pricing-engine/pricing-details.json")
                val transactionDataFromJson = resourceAsText("pricing-engine/request-body.json")
                val transactionDataList = DefaultObjectMapper().readValue<List<TransactionData>>(transactionDataFromJson)

                mockWebServer.enqueue(MockResponse().setBody(jsonResponse).addHeader("Content-Type", "application/json"))

                val pricingDetailsList: List<PricingDetails> = pricingEngineGateway.calculate(transactionDataList)

                assertEquals(2, pricingDetailsList.size)
                assertNotNull(pricingDetailsList[0])
                assertNotNull(pricingDetailsList[1])

                var pricingDetail = pricingDetailsList[0]

                assertNotNull(pricingDetail.splitDetail)
                assertNotNull(pricingDetail.hashRevenueDetail)
                assertNotNull(pricingDetail.isoRevenueDetail)

                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.splitDetail[0].merchantId)
                assertEquals(1, pricingDetail.splitDetail[0].installmentNumber)
                assertEquals(6666000, pricingDetail.splitDetail[0].splitAmount)
                assertEquals(0, pricingDetail.splitDetail[0].isoRevenueAmount)

                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.splitDetail[1].merchantId)
                assertEquals(2, pricingDetail.splitDetail[1].installmentNumber)
                assertEquals(6666000, pricingDetail.splitDetail[1].splitAmount)
                assertEquals(0, pricingDetail.splitDetail[1].isoRevenueAmount)

                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.splitDetail[2].merchantId)
                assertEquals(3, pricingDetail.splitDetail[2].installmentNumber)
                assertEquals(6668000, pricingDetail.splitDetail[2].splitAmount)
                assertEquals(0, pricingDetail.splitDetail[2].isoRevenueAmount)

                assertEquals(1, pricingDetail.hashRevenueDetail[0].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.hashRevenueDetail[0].merchantId)
                assertEquals(0, pricingDetail.hashRevenueDetail[0].amount)

                assertEquals(2, pricingDetail.hashRevenueDetail[1].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.hashRevenueDetail[1].merchantId)
                assertEquals(0, pricingDetail.hashRevenueDetail[1].amount)

                assertEquals(3, pricingDetail.hashRevenueDetail[2].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.hashRevenueDetail[2].merchantId)
                assertEquals(0, pricingDetail.hashRevenueDetail[2].amount)

                assertEquals(1, pricingDetail.isoRevenueDetail[0].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.isoRevenueDetail[0].merchantId)
                assertEquals(0, pricingDetail.isoRevenueDetail[0].amount)

                assertEquals(2, pricingDetail.isoRevenueDetail[1].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.isoRevenueDetail[1].merchantId)
                assertEquals(0, pricingDetail.isoRevenueDetail[1].amount)

                assertEquals(3, pricingDetail.isoRevenueDetail[2].installmentNumber)
                assertEquals("5ae0c5077e70c30004c907b2", pricingDetail.isoRevenueDetail[2].merchantId)
                assertEquals(0, pricingDetail.isoRevenueDetail[2].amount)

                pricingDetail = pricingDetailsList[1]

                assertNotNull(pricingDetail.splitDetail)
                assertNotNull(pricingDetail.hashRevenueDetail)
                assertNotNull(pricingDetail.isoRevenueDetail)

                assertEquals("5b61b02cf3ce4f0007076519", pricingDetail.splitDetail[0].merchantId)
                assertEquals(1, pricingDetail.splitDetail[0].installmentNumber)
                assertEquals(6440000, pricingDetail.splitDetail[0].splitAmount)
                assertEquals(0, pricingDetail.splitDetail[0].isoRevenueAmount)

                assertEquals(1, pricingDetail.hashRevenueDetail[0].installmentNumber)
                assertEquals("5b61b02cf3ce4f0007076519", pricingDetail.hashRevenueDetail[0].merchantId)
                assertEquals(0, pricingDetail.hashRevenueDetail[0].amount)

                assertEquals(1, pricingDetail.isoRevenueDetail[0].installmentNumber)
                assertEquals("5b61b02cf3ce4f0007076519", pricingDetail.isoRevenueDetail[0].merchantId)
                assertEquals(0, pricingDetail.isoRevenueDetail[0].amount)

                it("This request need to contains body and to be a POST") {
                    val recordedRequest: RecordedRequest = mockWebServer.takeRequest()
                    val transactionDataFromBody = DefaultObjectMapper().readValue<List<TransactionData>>(recordedRequest.body.readUtf8())
                    assertEquals(transactionDataList, transactionDataFromBody)
                    assertEquals("POST", recordedRequest.method)
                    assertEquals("/calculate_pricing", recordedRequest.path)
                }
            }

            it("When we have a bad request from pricing-engine") {
                mockWebServer.enqueue(MockResponse().setResponseCode(400))

                val transactionDataFromJson = resourceAsText("pricing-engine/request-body.json")
                val transactionDataList = DefaultObjectMapper().readValue<List<TransactionData>>(transactionDataFromJson)
                val pricingDetailsList: List<PricingDetails> = pricingEngineGateway.calculate(transactionDataList)

                assertEquals(0, pricingDetailsList.size)
            }
        }
    }
}
