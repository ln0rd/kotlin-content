package br.com.hash.clearing.infrastructure.input

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
        SchedulingConfiguration::class, InputJobs::class, MockedReaderComponents::class
    ],
    properties = ["clearing.jobs.transactionReader=10"]
)
class InputJobsTests(
    @Autowired private val pubSubTransactionQueueReader: PubSubTransactionQueueReader
) : BehaviorSpec({

    Given("the input jobs with a schedule delay set to 10ms") {

        When("a certain amount of time has passed") {
            val timeout = Duration.ofMillis(1000)
            val interval = Duration.ofMillis(10)

            Then("the pubSubTransactionQueueReader should have been called multiple times") {
                // Reset mocked bean state to count invocations from here
                clearMocks(pubSubTransactionQueueReader, answers = false)
                await atMost timeout withPollInterval interval untilAsserted {
                    verify(atLeast = 5) { pubSubTransactionQueueReader.pull() }
                }
            }
        }
    }
})
