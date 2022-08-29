package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.transaction.TransactionPreProcessor
import br.com.hash.clearing.domain.transaction.TransactionProcessor
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockedProcessorComponents {
    @Bean
    fun eventPublisher(): EventPublisher {
        return spyk()
    }

    @Bean
    fun transactionPreProcessor(): TransactionPreProcessor {
        val mock = mockk<TransactionPreProcessor>()
        every { mock.process() } just Runs

        return mock
    }

    @Bean
    fun transactionProcessor(): TransactionProcessor {
        val mock = mockk<TransactionProcessor>()
        every { mock.process() } just Runs

        return mock
    }
}
