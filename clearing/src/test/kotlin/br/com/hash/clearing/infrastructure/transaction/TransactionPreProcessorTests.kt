package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.TransactionPreProcessor
import io.kotest.core.spec.style.BehaviorSpec
import kotlin.test.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MockedComponents::class, TransactionComponents::class, ProcessableTransactionRepositoryAdapter::class],
)
class TransactionPreProcessorTests(
    @Autowired private val transactionPreProcessor: TransactionPreProcessor,
) : BehaviorSpec({
    Given("the current spring configuration") {
        Then("the transactionPreProcessor component should exist") {
            assertNotNull(transactionPreProcessor)
        }
    }
})
