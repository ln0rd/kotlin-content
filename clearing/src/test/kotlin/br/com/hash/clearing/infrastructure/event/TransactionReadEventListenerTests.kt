package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.input.TransactionReadEvent
import br.com.hash.clearing.domain.mock.FakeTransactionData
import br.com.hash.clearing.domain.transaction.TransactionReadEventListener
import br.com.hash.clearing.infrastructure.transaction.TransactionComponents
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
        SpringEventPublisher::class, TransactionReadEventListeners::class,
        TransactionComponents::class, MockedComponents::class
    ]
)
class TransactionReadEventListenerTests(
    @Autowired private val eventPublisher: EventPublisher,
    @Autowired private val transactionReadEventListener: TransactionReadEventListener
) : BehaviorSpec({

    Given("the current spring configuration") {

        Then("the eventPublisher component should exist") {
            assertNotNull(eventPublisher)
        }

        Then("the transactionReadEventListener component should exist") {
            assertNotNull(transactionReadEventListener)
        }
    }

    Given("a configured TransactionReadEventListener") {

        mockkObject(transactionReadEventListener)

        When("a TransactionReadEvent is emitted") {
            val transactionDataList = listOf(
                FakeTransactionData().createDebitType(),
                FakeTransactionData().createDebitType(),
                FakeTransactionData().createDebitType()
            )

            val transactionReadEvent = TransactionReadEvent(transactionDataList)
            eventPublisher.publish(transactionReadEvent)

            Then("the TransactionReadEventListener should handle the event") {
                verify { transactionReadEventListener invoke "handleEvent" withArguments listOf(transactionReadEvent) }
                confirmVerified(transactionReadEventListener)
            }
        }

        unmockkAll()
    }
})
