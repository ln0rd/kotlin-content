package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.TransactionPreProcessor
import br.com.hash.clearing.domain.transaction.TransactionProcessor
import br.com.hash.clearing.infrastructure.scheduling.SchedulingConfiguration
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearMocks
import io.mockk.verify
import java.time.Duration
import org.awaitility.kotlin.atMost
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.awaitility.kotlin.withPollInterval
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        SchedulingConfiguration::class, TransactionJobs::class, MockedProcessorComponents::class,
    ],
    properties = ["clearing.jobs.transactionPreProcessor=10", "clearing.jobs.transactionProcessor=10"]
)
class TransactionJobsTests(
    @Autowired private val transactionPreProcessor: TransactionPreProcessor,
    @Autowired private val transactionProcessor: TransactionProcessor,
) : BehaviorSpec({

    Given("the transaction jobs with a schedule delay set to 10ms") {

        When("a certain amount of time has passed") {
            val timeout = Duration.ofMillis(1000)
            val interval = Duration.ofMillis(10)

            Then("the transactionPreProcessor should have been called multiple times") {
                // Reset mocked bean state to count invocations from here
                clearMocks(transactionPreProcessor, answers = false)
                await atMost timeout withPollInterval interval untilAsserted {
                    verify(atLeast = 5) { transactionPreProcessor.process() }
                }
            }

            Then("the transactionProcessor should have been called multiple times") {
                // Reset mocked bean state to count invocations from here
                clearMocks(transactionProcessor, answers = false)
                await atMost timeout withPollInterval interval untilAsserted {
                    verify(atLeast = 5) { transactionProcessor.process() }
                }
            }
        }
    }
})
