package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleGateway
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.DescribeSpec
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class HttpReceivableScheduleGatewayTests(receivablesScheduleGateway: ReceivablesScheduleGateway) : DescribeSpec() {
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

            it("Successful request") {
                val response = resourceAsText("receivable-schedule/response.json")
                val fakeReceivableList: List<Receivable> = createFakeReceivableList()

                mockWebServer.enqueue(MockResponse().setBody(response).addHeader("Content-Type", "application/json"))

                val sentReceivableList: List<Receivable>? = receivablesScheduleGateway.send(
                    fakeReceivableList,
                    "29e75e22-e801-4768-82c0-95766e03fe53"
                )

                assertNotNull(sentReceivableList)
                assertEquals(2, sentReceivableList.size)

                val firstReceivable = sentReceivableList[0]
                assertEquals(1000, firstReceivable.amount)
                assertEquals("merchant", firstReceivable.holder)
                assertEquals(1, firstReceivable.installment)
                assertEquals("5ef1f364-fb6c-4de0-ab41-b461adfa74fb", firstReceivable.isoId)
                assertEquals(null, firstReceivable.merchantId)
                assertEquals(LocalDate.parse("2022-02-01"), firstReceivable.originAt)
                assertEquals("VCD", firstReceivable.paymentNetwork)
                assertEquals(LocalDate.parse("2022-03-01"), firstReceivable.settlementDate)
                assertEquals("ba73533f-13ec-4245-a4f8-891e55c0072d", firstReceivable.transactionId)

                val secondReceivable = sentReceivableList[1]
                assertEquals(1000, secondReceivable.amount)
                assertEquals("merchant", secondReceivable.holder)
                assertEquals(2, secondReceivable.installment)
                assertEquals(null, secondReceivable.isoId)
                assertEquals("6b194e0c-c945-4ecf-957b-1965e4172cea", secondReceivable.merchantId)
                assertEquals(LocalDate.parse("2022-02-01"), secondReceivable.originAt)
                assertEquals("VCD", secondReceivable.paymentNetwork)
                assertEquals(LocalDate.parse("2022-04-01"), secondReceivable.settlementDate)
                assertEquals("ba73533f-13ec-4245-a4f8-891e55c0072d", secondReceivable.transactionId)

                it("This request need to contains correctly body with receivables to sent") {
                    val recordedRequest: RecordedRequest = mockWebServer.takeRequest()
                    val transactionDataFromBody = DefaultObjectMapper().readValue<Receivables>(recordedRequest.body.readUtf8())

                    val firstReceivableFromBody = transactionDataFromBody.receivables[0]
                    assertEquals(1000, firstReceivableFromBody.amount)
                    assertEquals("merchant", firstReceivableFromBody.holder)
                    assertEquals(1, firstReceivableFromBody.installment)
                    assertEquals("5ef1f364-fb6c-4de0-ab41-b461adfa74fb", firstReceivableFromBody.isoId)
                    assertEquals(null, firstReceivableFromBody.merchantId)
                    assertEquals(LocalDate.parse("2022-02-01"), firstReceivableFromBody.originAt)
                    assertEquals("VCD", firstReceivableFromBody.paymentNetwork)
                    assertEquals(LocalDate.parse("2022-03-01"), firstReceivableFromBody.settlementDate)
                    assertEquals("ba73533f-13ec-4245-a4f8-891e55c0072d", firstReceivableFromBody.transactionId)

                    val secondReceivableFromBody = transactionDataFromBody.receivables[1]
                    assertEquals(1000, secondReceivableFromBody.amount)
                    assertEquals("merchant", secondReceivableFromBody.holder)
                    assertEquals(2, secondReceivableFromBody.installment)
                    assertEquals(null, secondReceivableFromBody.isoId)
                    assertEquals("6b194e0c-c945-4ecf-957b-1965e4172cea", secondReceivableFromBody.merchantId)
                    assertEquals(LocalDate.parse("2022-02-01"), secondReceivableFromBody.originAt)
                    assertEquals("VCD", secondReceivableFromBody.paymentNetwork)
                    assertEquals(LocalDate.parse("2022-04-01"), secondReceivableFromBody.settlementDate)
                    assertEquals("ba73533f-13ec-4245-a4f8-891e55c0072d", secondReceivableFromBody.transactionId)

                    it("This is a POST and sent to correct path") {
                        assertEquals("POST", recordedRequest.method)
                        assertEquals("/receivables", recordedRequest.path)

                        assertEquals("29e75e22-e801-4768-82c0-95766e03fe53", recordedRequest.getHeader("idempotency"))
                    }
                }
            }

            it("When we have a bad request from receivable schedule") {
                mockWebServer.enqueue(MockResponse().setResponseCode(400))
                val fakeReceivableList: List<Receivable> = createFakeReceivableList()

                val sentReceivableListExpected: List<Receivable>? = receivablesScheduleGateway.send(
                    fakeReceivableList,
                    "29e75e22-e801-4768-82c0-95766e03fe53"
                )
                assertNull(sentReceivableListExpected)
            }

            it("Deserialization its working") {
                val response = resourceAsText("receivable-schedule/response.json")
                val receivableScheduleResponse = DefaultObjectMapper().readValue<ReceivableScheduleResponse>(response)

                assertEquals("receivables created", receivableScheduleResponse.message)
            }
        }
    }

    fun createFakeReceivableList(): List<Receivable> {
        return listOf(
            Receivable(
                amount = 1000,
                holder = "merchant",
                installment = 1,
                isoId = "5ef1f364-fb6c-4de0-ab41-b461adfa74fb",
                merchantId = null,
                originAt = LocalDate.parse("2022-02-01"),
                paymentNetwork = "VCD",
                settlementDate = LocalDate.parse("2022-03-01"),
                transactionId = "ba73533f-13ec-4245-a4f8-891e55c0072d"
            ),
            Receivable(
                amount = 1000,
                holder = "merchant",
                installment = 2,
                isoId = null,
                merchantId = "6b194e0c-c945-4ecf-957b-1965e4172cea",
                originAt = LocalDate.parse("2022-02-01"),
                paymentNetwork = "VCD",
                settlementDate = LocalDate.parse("2022-04-01"),
                transactionId = "ba73533f-13ec-4245-a4f8-891e55c0072d"
            )
        )
    }
}
