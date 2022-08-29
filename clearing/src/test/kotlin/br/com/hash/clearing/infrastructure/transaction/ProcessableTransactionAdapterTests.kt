
package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.mock.FakeProcessableTransaction as FakeProcessableTransactionDomain
import br.com.hash.clearing.domain.transaction.ProcessableTransaction as ProcessableTransactionDomain
import br.com.hash.clearing.infrastructure.mock.FakeProcessableTransactionEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlin.test.assertEquals

class ProcessableTransactionAdapterTests() : BehaviorSpec({
    Given("Saving a transactionData through TransactionAdapter") {
        val listOfProcessableTransactionEntity = listOf(FakeProcessableTransactionEntity().createSimple(), FakeProcessableTransactionEntity().createSimple())
        val listOfProcessableTransactionDomain = listOf(
            FakeProcessableTransactionDomain().createSimple(),
            FakeProcessableTransactionDomain().createSimple()
        )
        val listOfProcessableTransactionDomainExpected = listOf(
            FakeProcessableTransactionDomain().expectedInProcessableTransactionAdapter(),
            FakeProcessableTransactionDomain().expectedInProcessableTransactionAdapter(),
        )

        val listOfExpectedTransactionRepositoryParameters = listOf<ProcessableTransactionEntity>(
            FakeProcessableTransactionEntity().createSimpleExpectedInTransactionRepositoryParameters(),
            FakeProcessableTransactionEntity().createSimpleExpectedInTransactionRepositoryParameters()
        )

        val processableTransactionCrudRepository = mockk<ProcessableTransactionCrudRepository>()
        val mockedProcessableTransactionRepositoryAdapter = ProcessableTransactionRepositoryAdapter(processableTransactionCrudRepository)

        When("We have a list of transactionsData to saveAll") {
            val capturingSlot = slot<List<ProcessableTransactionEntity>>()
            every { processableTransactionCrudRepository.saveAll(capture(capturingSlot)) } returns listOfProcessableTransactionEntity

            Then("It need return a ProcessableTransactions list from this list of transactionsData") {
                val savedProcessableTransactionsList: List<ProcessableTransactionDomain> =
                    mockedProcessableTransactionRepositoryAdapter.save(listOfProcessableTransactionDomain)
                assertEquals(listOfProcessableTransactionDomainExpected, savedProcessableTransactionsList)
            }

            Then("It comes at repository with right arguments") {
                val slotCaptured = capturingSlot.captured
                assertEquals(listOfExpectedTransactionRepositoryParameters, slotCaptured)
            }
        }
    }
})
