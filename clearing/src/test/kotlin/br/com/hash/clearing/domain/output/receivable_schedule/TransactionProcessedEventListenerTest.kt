package br.com.hash.clearing.domain.output.receivable_schedule

import br.com.hash.clearing.domain.logger.Log
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.mock.FakePricingDetails
import br.com.hash.clearing.domain.mock.FakeProcessableReceivables
import br.com.hash.clearing.domain.mock.FakeTransactionData
import br.com.hash.clearing.domain.mock.FakeTransactionProcessResults
import br.com.hash.clearing.domain.model.TransactionProcessResult
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import br.com.hash.clearing.domain.output.receivables_schedule.TransactionProcessedEventListener
import br.com.hash.clearing.domain.transaction.TransactionProcessedEvent
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import java.time.LocalDate
import kotlin.test.assertEquals

class TransactionProcessedEventListenerTest() : BehaviorSpec({
    Given(" a transactionProcessResult") {
        When("transaction is a debit type") {
            val fakeDebitProcessableReceivables: List<ProcessableReceivables> = FakeProcessableReceivables().createDebitTransactionExpectExampleList()
            val debitTransactionProcessResults: List<TransactionProcessResult> = FakeTransactionProcessResults().createSimpleList()

            And("it comes to repository") {
                val processableReceivableRepository = spyk<ProcessableReceivablesRepository>()
                val capturingSlot = slot<List<ProcessableReceivables>>()
                every { processableReceivableRepository.save(capture(capturingSlot)) } returns fakeDebitProcessableReceivables

                val logger = spyk<Logger>()
                val log = spyk<Log>()
                every { logger.withContext(any()) } returns log

                And("listener receive the event") {
                    val transactionReadEvent = TransactionProcessedEvent(debitTransactionProcessResults)
                    val transactionProcessedEventListener =
                        TransactionProcessedEventListener(processableReceivableRepository, logger)
                    transactionProcessedEventListener.handleEvent(transactionReadEvent)

                    Then("it should save in the repository") {
                        val savedProcessableReceivable: List<ProcessableReceivables> = capturingSlot.captured

                        verify {
                            processableReceivableRepository invoke "save" withArguments listOf(
                                savedProcessableReceivable
                            )
                        }
                        assertEquals(fakeDebitProcessableReceivables, savedProcessableReceivable)

                        Then("all properties of Receivable are correct from first ProcessableReceivable") {
                            val firstProcessableReceivable = savedProcessableReceivable[0]
                            assertEquals("1539985", firstProcessableReceivable.transactionId)
                            assertEquals(FakeTransactionData().createDebitType(), firstProcessableReceivable.transactionData)
                            assertEquals(FakePricingDetails().createSimple(FakeTransactionData().createDebitType().id), firstProcessableReceivable.pricingDetails)
                            assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, firstProcessableReceivable.status)
                            val firstReceivable = firstProcessableReceivable.receivables[0]
                            assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                            assertEquals("merchant", firstReceivable.holder)
                            assertEquals(6666, firstReceivable.amount)
                            assertEquals(1, firstReceivable.installment)
                            assertEquals("VCD", firstReceivable.paymentNetwork)
                            assertEquals(LocalDate.parse("2021-10-15"), firstReceivable.settlementDate)
                            assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                            assertEquals("1539985", firstReceivable.transactionId)
                            assertEquals("clearing", firstReceivable.originFrom)

                            assertEquals(1, firstProcessableReceivable.receivables.size)
                        }

                        And("all properties of Receivable are correct from second ProcessableReceivable") {
                            val secondProcessableReceivable = savedProcessableReceivable[1]
                            assertEquals("1539985", secondProcessableReceivable.transactionId)
                            assertEquals(FakeTransactionData().createDebitType(), secondProcessableReceivable.transactionData)
                            assertEquals(FakePricingDetails().createSimple(FakeTransactionData().createDebitType().id), secondProcessableReceivable.pricingDetails)
                            assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, secondProcessableReceivable.status)
                            val firstReceivable = secondProcessableReceivable.receivables[0]
                            assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                            assertEquals("merchant", firstReceivable.holder)
                            assertEquals(6666, firstReceivable.amount)
                            assertEquals(1, firstReceivable.installment)
                            assertEquals("VCD", firstReceivable.paymentNetwork)
                            assertEquals(LocalDate.parse("2021-10-15"), firstReceivable.settlementDate)
                            assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                            assertEquals("1539985", firstReceivable.transactionId)
                            assertEquals("clearing", firstReceivable.originFrom)

                            assertEquals(1, secondProcessableReceivable.receivables.size)
                        }

                        Then("it should have called all the mocks") {
                            confirmVerified(processableReceivableRepository)
                        }
                    }
                }
            }

            When("transaction is a credit type") {
                val fakeCreditProcessableReceivables: List<ProcessableReceivables> = FakeProcessableReceivables().createCreditSimpleList()
                val fakeCreditProcessableReceivablesExpected: List<ProcessableReceivables> = FakeProcessableReceivables().createCreditTransactionExpectExampleList()
                val creditTransactionProcessResults: List<TransactionProcessResult> = FakeTransactionProcessResults().createCreditTransactionTypeList()

                And("it comes to repository") {
                    val processableReceivableRepository = spyk<ProcessableReceivablesRepository>()
                    val capturingSlot = slot<List<ProcessableReceivables>>()
                    every { processableReceivableRepository.save(capture(capturingSlot)) } returns fakeCreditProcessableReceivables

                    val logger = spyk<Logger>()
                    val log = spyk<Log>()
                    every { logger.withContext(any()) } returns log

                    And("listener receive the event") {
                        val transactionReadEvent = TransactionProcessedEvent(creditTransactionProcessResults)
                        val transactionProcessedEventListener =
                            TransactionProcessedEventListener(processableReceivableRepository, logger)
                        transactionProcessedEventListener.handleEvent(transactionReadEvent)

                        Then("it should save in the repository") {
                            val savedProcessableReceivable: List<ProcessableReceivables> = capturingSlot.captured

                            verify {
                                processableReceivableRepository invoke "save" withArguments listOf(
                                    savedProcessableReceivable
                                )
                            }
                            assertEquals(fakeCreditProcessableReceivablesExpected, savedProcessableReceivable)

                            Then("all properties of Receivable are correct from first ProcessableReceivable") {
                                val firstProcessableReceivable = savedProcessableReceivable[0]
                                assertEquals("1539985", firstProcessableReceivable.transactionId)
                                assertEquals(FakeTransactionData().createCreditType(), firstProcessableReceivable.transactionData)
                                assertEquals(FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditType().id), firstProcessableReceivable.pricingDetails)
                                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, firstProcessableReceivable.status)

                                val firstReceivable = firstProcessableReceivable.receivables[0]
                                assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                                assertEquals("merchant", firstReceivable.holder)
                                assertEquals(1, firstReceivable.installment)
                                assertEquals(6666, firstReceivable.amount)
                                assertEquals(LocalDate.parse("2021-11-14"), firstReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val secondReceivable = firstProcessableReceivable.receivables[1]
                                assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                                assertEquals("merchant", secondReceivable.holder)
                                assertEquals(6666, secondReceivable.amount)
                                assertEquals(2, secondReceivable.installment)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals(LocalDate.parse("2021-12-14"), secondReceivable.settlementDate)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val thirdReceivable = firstProcessableReceivable.receivables[2]
                                assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                                assertEquals("merchant", thirdReceivable.holder)
                                assertEquals(6668, thirdReceivable.amount)
                                assertEquals(3, thirdReceivable.installment)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals(LocalDate.parse("2022-01-14"), thirdReceivable.settlementDate)
                                assertEquals("clearing", firstReceivable.originFrom)

                                assertEquals(3, firstProcessableReceivable.receivables.size)
                            }

                            And("all properties of Receivable are correct from second ProcessableReceivable") {
                                val secondProcessableReceivable = savedProcessableReceivable[0]
                                assertEquals("1539985", secondProcessableReceivable.transactionId)
                                assertEquals(FakeTransactionData().createCreditType(), secondProcessableReceivable.transactionData)
                                assertEquals(FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditType().id), secondProcessableReceivable.pricingDetails)
                                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, secondProcessableReceivable.status)

                                val firstReceivable = secondProcessableReceivable.receivables[0]
                                assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                                assertEquals("merchant", firstReceivable.holder)
                                assertEquals(6666, firstReceivable.amount)
                                assertEquals(1, firstReceivable.installment)
                                assertEquals(LocalDate.parse("2021-11-14"), firstReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val secondReceivable = secondProcessableReceivable.receivables[1]
                                assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                                assertEquals("merchant", secondReceivable.holder)
                                assertEquals(6666, secondReceivable.amount)
                                assertEquals(2, secondReceivable.installment)
                                assertEquals(LocalDate.parse("2021-12-14"), secondReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val thirdReceivable = secondProcessableReceivable.receivables[2]
                                assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                                assertEquals("merchant", thirdReceivable.holder)
                                assertEquals(3, thirdReceivable.installment)
                                assertEquals(6668, thirdReceivable.amount)
                                assertEquals(LocalDate.parse("2022-01-14"), thirdReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2021-10-14"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)

                                assertEquals(3, secondProcessableReceivable.receivables.size)
                            }
                        }

                        Then("it should have called all the mocks") {
                            confirmVerified(processableReceivableRepository)
                        }
                    }
                }
            }

            When("transaction is a credit type with datetime in the border of month") {
                val fakeCreditProcessableReceivables: List<ProcessableReceivables> = FakeProcessableReceivables().createCreditInLastDayOfMonthList()
                val creditTransactionProcessResults: List<TransactionProcessResult> = FakeTransactionProcessResults().createCreditTransactionTypeInLastDayOfMonthList()

                And("it comes to repository") {
                    val processableReceivableRepository = spyk<ProcessableReceivablesRepository>()
                    val capturingSlot = slot<List<ProcessableReceivables>>()
                    every { processableReceivableRepository.save(capture(capturingSlot)) } returns fakeCreditProcessableReceivables

                    val logger = spyk<Logger>()
                    val log = spyk<Log>()
                    every { logger.withContext(any()) } returns log

                    And("listener receive the event") {
                        val transactionReadEvent = TransactionProcessedEvent(creditTransactionProcessResults)
                        val transactionProcessedEventListener =
                            TransactionProcessedEventListener(processableReceivableRepository, logger)
                        transactionProcessedEventListener.handleEvent(transactionReadEvent)

                        Then("it should save in the repository") {
                            val savedProcessableReceivable: List<ProcessableReceivables> = capturingSlot.captured

                            verify {
                                processableReceivableRepository invoke "save" withArguments listOf(
                                    savedProcessableReceivable
                                )
                            }

                            assertEquals(fakeCreditProcessableReceivables, savedProcessableReceivable)

                            Then("all properties of Receivable are correct from first ProcessableReceivable") {
                                val firstProcessableReceivable = savedProcessableReceivable[0]
                                assertEquals("1539985", firstProcessableReceivable.transactionId)
                                assertEquals(FakeTransactionData().createCreditTypeInLastDayOfMonth(), firstProcessableReceivable.transactionData)
                                assertEquals(FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditTypeInLastDayOfMonth().id), firstProcessableReceivable.pricingDetails)
                                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, firstProcessableReceivable.status)

                                val firstReceivable = firstProcessableReceivable.receivables[0]
                                assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                                assertEquals("merchant", firstReceivable.holder)
                                assertEquals(6666, firstReceivable.amount)
                                assertEquals(1, firstReceivable.installment)
                                assertEquals(LocalDate.parse("2022-02-28"), firstReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val secondReceivable = firstProcessableReceivable.receivables[1]
                                assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                                assertEquals("merchant", secondReceivable.holder)
                                assertEquals(6666, secondReceivable.amount)
                                assertEquals(2, secondReceivable.installment)
                                assertEquals(LocalDate.parse("2022-03-31"), secondReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val thirdReceivable = firstProcessableReceivable.receivables[2]
                                assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                                assertEquals("merchant", thirdReceivable.holder)
                                assertEquals(6668, thirdReceivable.amount)
                                assertEquals(3, thirdReceivable.installment)
                                assertEquals(LocalDate.parse("2022-04-30"), thirdReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)

                                assertEquals(3, firstProcessableReceivable.receivables.size)
                            }

                            And("all properties of Receivable are correct from second ProcessableReceivable") {
                                val secondProcessableReceivable = savedProcessableReceivable[0]
                                assertEquals("1539985", secondProcessableReceivable.transactionId)
                                assertEquals(FakeTransactionData().createCreditTypeInLastDayOfMonth(), secondProcessableReceivable.transactionData)
                                assertEquals(FakePricingDetails().createCreditSimple(FakeTransactionData().createCreditTypeInLastDayOfMonth().id), secondProcessableReceivable.pricingDetails)
                                assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, secondProcessableReceivable.status)

                                val firstReceivable = secondProcessableReceivable.receivables[0]
                                assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                                assertEquals("merchant", firstReceivable.holder)
                                assertEquals(6666, firstReceivable.amount)
                                assertEquals(1, firstReceivable.installment)
                                assertEquals(LocalDate.parse("2022-02-28"), firstReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val secondReceivable = secondProcessableReceivable.receivables[1]
                                assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                                assertEquals("merchant", secondReceivable.holder)
                                assertEquals(6666, secondReceivable.amount)
                                assertEquals(2, secondReceivable.installment)
                                assertEquals(LocalDate.parse("2022-03-31"), secondReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)
                                val thirdReceivable = secondProcessableReceivable.receivables[2]
                                assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                                assertEquals("merchant", thirdReceivable.holder)
                                assertEquals(3, thirdReceivable.installment)
                                assertEquals(6668, thirdReceivable.amount)
                                assertEquals(LocalDate.parse("2022-04-30"), thirdReceivable.settlementDate)
                                assertEquals("VCD", firstReceivable.paymentNetwork)
                                assertEquals(LocalDate.parse("2022-01-31"), firstReceivable.originAt)
                                assertEquals("1539985", firstReceivable.transactionId)
                                assertEquals("clearing", firstReceivable.originFrom)

                                assertEquals(3, secondProcessableReceivable.receivables.size)
                            }
                        }
                    }
                }
            }
        }

        When("transaction is a PURCHASE_REVERSAL and a credit type") {
            val creditPurchaseReversalTransactionProcessResults: List<TransactionProcessResult> = FakeTransactionProcessResults().createTransactionPurchaseReversalCreditTypeList()
            val fakeCreditPurchaseReversalProcessableReceivables: List<ProcessableReceivables> = FakeProcessableReceivables().createCreditReversalPurchaseTypeList()

            And("it comes to repository") {
                val processableReceivableRepository = spyk<ProcessableReceivablesRepository>()
                val capturingSlot = slot<List<ProcessableReceivables>>()
                every { processableReceivableRepository.save(capture(capturingSlot)) } returns fakeCreditPurchaseReversalProcessableReceivables

                val logger = spyk<Logger>()
                val log = spyk<Log>()
                every { logger.withContext(any()) } returns log

                And("listener receive the event") {
                    val transactionReadEvent = TransactionProcessedEvent(creditPurchaseReversalTransactionProcessResults)
                    val transactionProcessedEventListener =
                        TransactionProcessedEventListener(processableReceivableRepository, logger)
                    transactionProcessedEventListener.handleEvent(transactionReadEvent)

                    Then("it should save in the repository") {
                        val savedProcessableReceivable: List<ProcessableReceivables> = capturingSlot.captured

                        verify {
                            processableReceivableRepository invoke "save" withArguments listOf(
                                savedProcessableReceivable
                            )
                        }

                        assertEquals(fakeCreditPurchaseReversalProcessableReceivables, savedProcessableReceivable)

                        Then("all properties of Receivable are correct from first ProcessableReceivable") {
                            val firstProcessableReceivable = savedProcessableReceivable[0]
                            assertEquals(FakeTransactionData().createCreditReversalPurchaseType(), firstProcessableReceivable.transactionData)
                            assertEquals(FakePricingDetails().createCreditPurchaseReversal(FakeTransactionData().createCreditReversalPurchaseType().id), firstProcessableReceivable.pricingDetails)
                            assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, firstProcessableReceivable.status)

                            val firstReceivable = firstProcessableReceivable.receivables[0]
                            assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                            assertEquals("merchant", firstReceivable.holder)
                            assertEquals(-6666, firstReceivable.amount)
                            assertEquals(1, firstReceivable.installment)
                            assertEquals(LocalDate.parse("2022-02-28"), firstReceivable.settlementDate)
                            val secondReceivable = firstProcessableReceivable.receivables[1]
                            assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                            assertEquals("merchant", secondReceivable.holder)
                            assertEquals(-6666, secondReceivable.amount)
                            assertEquals(2, secondReceivable.installment)
                            val thirdReceivable = firstProcessableReceivable.receivables[2]
                            assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                            assertEquals("merchant", thirdReceivable.holder)
                            assertEquals(-6668, thirdReceivable.amount)
                            assertEquals(3, thirdReceivable.installment)
                            assertEquals(LocalDate.parse("2022-04-30"), thirdReceivable.settlementDate)

                            assertEquals(3, firstProcessableReceivable.receivables.size)
                        }

                        And("all properties of Receivable are correct from second ProcessableReceivable") {
                            val secondProcessableReceivable = savedProcessableReceivable[1]
                            assertEquals(FakeTransactionData().createCreditReversalPurchaseType(), secondProcessableReceivable.transactionData)
                            assertEquals(FakePricingDetails().createCreditPurchaseReversal(FakeTransactionData().createCreditReversalPurchaseType().id), secondProcessableReceivable.pricingDetails)
                            assertEquals(ProcessableReceivablesStatus.WAITING_TO_SEND, secondProcessableReceivable.status)

                            val firstReceivable = secondProcessableReceivable.receivables[0]
                            assertEquals("5ae0c5077e70c30004c907b2", firstReceivable.merchantId)
                            assertEquals("merchant", firstReceivable.holder)
                            assertEquals(-6666, firstReceivable.amount)
                            assertEquals(1, firstReceivable.installment)
                            assertEquals(LocalDate.parse("2022-02-28"), firstReceivable.settlementDate)
                            val secondReceivable = secondProcessableReceivable.receivables[1]
                            assertEquals("5ae0c5077e70c30004c907b2", secondReceivable.merchantId)
                            assertEquals("merchant", secondReceivable.holder)
                            assertEquals(-6666, secondReceivable.amount)
                            assertEquals(2, secondReceivable.installment)
                            assertEquals(LocalDate.parse("2022-03-31"), secondReceivable.settlementDate)
                            val thirdReceivable = secondProcessableReceivable.receivables[2]
                            assertEquals("5ae0c5077e70c30004c907b2", thirdReceivable.merchantId)
                            assertEquals("merchant", thirdReceivable.holder)
                            assertEquals(3, thirdReceivable.installment)
                            assertEquals(-6668, thirdReceivable.amount)
                            assertEquals(LocalDate.parse("2022-04-30"), thirdReceivable.settlementDate)
                        }
                    }
                }
            }
        }
    }
})
