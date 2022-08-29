package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.mock.FakePricingDetails
import br.com.hash.clearing.domain.mock.FakeTransactionData
import br.com.hash.clearing.domain.model.TransactionProcessResult
import br.com.hash.clearing.domain.transaction.TransactionProcessedEvent
import br.com.hash.clearing.infrastructure.output.meta_accounting.MetaAccountingComponents
import br.com.hash.clearing.infrastructure.output.receivables_schedule.ReceivablesScheduleComponents
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.confirmVerified
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlin.test.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        SpringEventPublisher::class, TransactionProcessedEventListeners::class,
        MetaAccountingComponents::class, ReceivablesScheduleComponents::class,
        MockedComponents::class
    ]
)
class TransactionProcessedEventListenerTests(
    @Autowired private val eventPublisher: EventPublisher,
    @Autowired private val metaAccountingEventListener: EventListener<TransactionProcessedEvent>,
    @Autowired private val receivablesScheduleEventListener: EventListener<TransactionProcessedEvent>
) : BehaviorSpec({
    Given("the current spring configuration") {

        Then("the eventPublisher component should exist") {
            assertNotNull(eventPublisher)
        }

        Then("the receivablesScheduleEventListener component should exist") {
            assertNotNull(receivablesScheduleEventListener)
        }

        Then("the metaAccountingEventListener component should exist") {
            assertNotNull(metaAccountingEventListener)
        }
    }

    Given("the following configured TransactionProcessedEventListeners") {

        mockkObject(receivablesScheduleEventListener)
        mockkObject(metaAccountingEventListener)

        When("a TransactionProcessedEvent is emitted") {
            val transactionProcessResults = listOf(
                TransactionProcessResult(FakeTransactionData().createDebitType(), FakePricingDetails().createEmpty("1539985")),
                TransactionProcessResult(FakeTransactionData().createDebitType(), FakePricingDetails().createEmpty("1539985")),
                TransactionProcessResult(FakeTransactionData().createDebitType(), FakePricingDetails().createEmpty("1539985")),
            )

            val transactionProcessedEvent = TransactionProcessedEvent(transactionProcessResults)
            eventPublisher.publish(transactionProcessedEvent)

            Then("the Receivables Schedule TransactionProcessedEventListener should handle the event") {
                verify { receivablesScheduleEventListener invoke "handleEvent" withArguments listOf(transactionProcessedEvent) }
                confirmVerified(receivablesScheduleEventListener)
            }

            Then("the Meta Accounting TransactionProcessedEventListener should handle the event") {
                verify { metaAccountingEventListener invoke "handleEvent" withArguments listOf(transactionProcessedEvent) }
                confirmVerified(metaAccountingEventListener)
            }
        }

        unmockkAll()
    }
})
