package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.input.TransactionReadEvent
import br.com.hash.clearing.domain.mock.FakeProcessableTransaction
import br.com.hash.clearing.domain.mock.FakeTransactionData
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlin.test.assertEquals

class TransactionReadEventListenerTests : BehaviorSpec({
    Given("a TransactionReadEvent with a list of transaction data") {
        val transactionDataList = listOf(FakeTransactionData().createDebitType(), FakeTransactionData().createDebitType())
        val transactionReadEvent = TransactionReadEvent(transactionDataList)
        val processableTransaction = listOf(
            FakeProcessableTransaction().expectedInTransactionReadEventListener(),
            FakeProcessableTransaction().expectedInTransactionReadEventListener()
        )

        And("a ProcessableTransactionRepository") {
            val processableTransactionRepository = spyk<ProcessableTransactionRepository>()
            val capturingSlot = slot<List<ProcessableTransaction>>()
            every { processableTransactionRepository.save(capture(capturingSlot)) } returns processableTransaction

            When("the TransactionReadEventListener receives the event") {
                val transactionReadEventListener = TransactionReadEventListener(processableTransactionRepository)
                transactionReadEventListener.handleEvent(transactionReadEvent)

                Then("it should save the transaction in the repository") {
                    val savedProcessableTransactions = capturingSlot.captured

                    verify { processableTransactionRepository invoke "save" withArguments listOf(savedProcessableTransactions) }
                    assertEquals(processableTransaction, savedProcessableTransactions)
                }

                Then("it should have called all the mocks") {
                    confirmVerified(processableTransactionRepository)
                }
            }
        }
    }
})
