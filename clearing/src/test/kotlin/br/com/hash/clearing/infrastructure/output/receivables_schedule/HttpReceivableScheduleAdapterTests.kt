package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.mock.FakeReceivables
import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.infrastructure.mock.FakeReceivables as FakeReceivablesInfrastructure
import br.com.hash.clearing.infrastructure.output.receivables_schedule.Receivables as ReceivablesInfrastructure
import io.kotest.core.spec.style.BehaviorSpec
import kotlin.test.assertEquals

class HttpReceivableScheduleAdapterTests() : BehaviorSpec({
    Given("Http receivable schedule adapter") {
        val listOfReceivable: List<Receivable> = FakeReceivables().createReceivableScheduleTest()
        val expectedReceivables: ReceivablesInfrastructure = FakeReceivablesInfrastructure().createExpectedInAdapterTest()

        When("we adapter an receivable") {
            val adaptedReceivables = HttpReceivableScheduleAdapter.createInfrastructureReceivables(listOfReceivable)

            Then("it need return a receivable adapted") {
                assertEquals(expectedReceivables, adaptedReceivables)
            }
        }
    }
})
