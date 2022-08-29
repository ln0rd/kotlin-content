package br.com.hash.clearing.domain.input

import br.com.hash.clearing.domain.event.Event
import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.mock.FakeTransactionData
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlin.test.assertEquals

class TransactionReaderTests : BehaviorSpec({
    Given("a transaction list from queue") {
        val transactionDataList = listOf(FakeTransactionData().createDebitType(), FakeTransactionData().createDebitType())

        And("an event publisher") {
            val eventPublisher = spyk<EventPublisher>()
            val capturingSlot = slot<Event>()
            every { eventPublisher.publish(capture(capturingSlot)) } just Runs

            When("the TransactionQueueReader receives the transaction data list") {
                val transactionQueueReader: TransactionQueueReader = TransactionReader(eventPublisher)
                transactionQueueReader.read(transactionDataList)

                Then("it should send an event with the read transaction data list") {
                    val transactionReadEvent =
                        capturingSlot.captured as TransactionReadEvent

                    verify {
                        eventPublisher invoke "publish" withArguments listOf(
                            transactionReadEvent
                        )
                    }

                    assertEquals(transactionDataList, transactionReadEvent.transactionDataList)
                }

                Then("it should have called all the mocks") {
                    confirmVerified(eventPublisher)
                }
            }
        }
    }
})
