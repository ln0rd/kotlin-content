package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.transaction.PricingEngineGateway
import br.com.hash.clearing.domain.transaction.TransactionServiceGateway
import io.mockk.mockk
import io.mockk.spyk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockedComponents {
    @Bean
    fun eventPublisher(): EventPublisher {
        return spyk()
    }

    @Bean
    fun processableTransactionCrudRepository(): ProcessableTransactionCrudRepository {
        return mockk()
    }

    @Bean
    fun transactionServiceGateway(): TransactionServiceGateway {
        return mockk()
    }

    @Bean
    fun pricingEngineGateway(): PricingEngineGateway {
        return mockk()
    }

    @Bean
    fun logger(): Logger {
        return mockk(relaxed = true)
    }
}
