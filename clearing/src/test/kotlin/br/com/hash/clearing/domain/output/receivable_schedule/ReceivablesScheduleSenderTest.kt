package br.com.hash.clearing.domain.output.receivable_schedule

import br.com.hash.clearing.domain.logger.Log
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.mock.FakeProcessableReceivables
import br.com.hash.clearing.domain.mock.FakeReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleGateway
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleSender
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import java.time.LocalDate
import kotlin.test.assertEquals

class ReceivablesScheduleSenderTest : BehaviorSpec({
    Given("Processable receivables to send") {
        val processableReceivablesNotSent = FakeProcessableReceivables().createSimpleList()

        val logger = spyk<Logger>()
        val log = spyk<Log>()
        every { logger.withContext(any()) } returns log

        When("It comes to repository to find some processableReceivables to send") {
            val processableReceivableRepository = spyk<ProcessableReceivablesRepository>()
            every { processableReceivableRepository.findNotSent() } returns processableReceivablesNotSent

            And("it comes to gateway") {
                val receivablesListSentFromGateway: List<Receivable> = FakeReceivables().createSimpleList()

                val receivableScheduleGateway = spyk<ReceivablesScheduleGateway>()
                val capturingSlot = slot<List<Receivable>>()
                val capturingSecondSlot = slot<String>()
                every { receivableScheduleGateway.send(capture(capturingSlot), capture(capturingSecondSlot)) } returns receivablesListSentFromGateway

                When("send the list of receivables from a processable receivable") {
                    val receivableScheduleSender = ReceivablesScheduleSender(receivableScheduleGateway, processableReceivableRepository, logger)
                    receivableScheduleSender.send()

                    Then("the gateway need receive the receivables from the processableReceivables found") {
                        val listOfGatewayReceivableCaptured = capturingSlot.captured
                        val idempotencyCaptured = capturingSecondSlot.captured

                        assertEquals("d49a233f-8596-325c-2d46-b100065aLE0c", idempotencyCaptured)

                        assertEquals(6666, listOfGatewayReceivableCaptured[0].amount)
                        assertEquals("merchant", listOfGatewayReceivableCaptured[0].holder)
                        assertEquals(1, listOfGatewayReceivableCaptured[0].installment)
                        assertEquals(null, listOfGatewayReceivableCaptured[0].isoId)
                        assertEquals("5ae0c5077e70c30004c907b2", listOfGatewayReceivableCaptured[0].merchantId)
                        assertEquals(LocalDate.parse("2021-10-14"), listOfGatewayReceivableCaptured[0].originAt)
                        assertEquals("clearing", listOfGatewayReceivableCaptured[0].originFrom)
                        assertEquals("VCD", listOfGatewayReceivableCaptured[0].paymentNetwork)
                        assertEquals(LocalDate.parse("2021-10-14"), listOfGatewayReceivableCaptured[0].settlementDate)
                        assertEquals("1539985", listOfGatewayReceivableCaptured[0].transactionId)

                        assertEquals(6666, listOfGatewayReceivableCaptured[1].amount)
                        assertEquals("merchant", listOfGatewayReceivableCaptured[1].holder)
                        assertEquals(2, listOfGatewayReceivableCaptured[1].installment)
                        assertEquals(null, listOfGatewayReceivableCaptured[1].isoId)
                        assertEquals("5ae0c5077e70c30004c907b2", listOfGatewayReceivableCaptured[1].merchantId)
                        assertEquals(LocalDate.parse("2021-10-14"), listOfGatewayReceivableCaptured[1].originAt)
                        assertEquals("clearing", listOfGatewayReceivableCaptured[1].originFrom)
                        assertEquals("VCD", listOfGatewayReceivableCaptured[1].paymentNetwork)
                        assertEquals(LocalDate.parse("2021-11-14"), listOfGatewayReceivableCaptured[1].settlementDate)
                        assertEquals("1539985", listOfGatewayReceivableCaptured[1].transactionId)

                        assertEquals(6668, listOfGatewayReceivableCaptured[2].amount)
                        assertEquals("merchant", listOfGatewayReceivableCaptured[2].holder)
                        assertEquals(3, listOfGatewayReceivableCaptured[2].installment)
                        assertEquals(null, listOfGatewayReceivableCaptured[2].isoId)
                        assertEquals("5ae0c5077e70c30004c907b2", listOfGatewayReceivableCaptured[2].merchantId)
                        assertEquals(LocalDate.parse("2021-10-14"), listOfGatewayReceivableCaptured[2].originAt)
                        assertEquals("clearing", listOfGatewayReceivableCaptured[2].originFrom)
                        assertEquals("VCD", listOfGatewayReceivableCaptured[2].paymentNetwork)
                        assertEquals(LocalDate.parse("2021-12-14"), listOfGatewayReceivableCaptured[2].settlementDate)
                        assertEquals("1539985", listOfGatewayReceivableCaptured[2].transactionId)
                    }
                }
            }
        }
    }
})
