package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.TransactionProcessor
import io.kotest.core.spec.style.BehaviorSpec
import kotlin.test.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [MockedComponents::class, TransactionComponents::class, ProcessableTransactionRepositoryAdapter::class],
)
class TransactionProcessorTests(
    @Autowired private val transactionProcessor: TransactionProcessor
) : BehaviorSpec({
    Given("the current spring configuration") {
        Then("the transactionProcessor component should exist") {
            assertNotNull(transactionProcessor)
        }
    }
})
