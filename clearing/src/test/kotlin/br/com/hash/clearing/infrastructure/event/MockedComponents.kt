package br.com.hash.clearing.infrastructure.event

import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.output.meta_accounting.MetaAccountingGateway
import br.com.hash.clearing.domain.output.meta_accounting.OutboxRepository as MetaAccountingOutboxRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleGateway
import br.com.hash.clearing.domain.transaction.PricingEngineGateway
import br.com.hash.clearing.domain.transaction.ProcessableTransactionRepository
import br.com.hash.clearing.domain.transaction.TransactionServiceGateway
import io.mockk.mockk
import io.mockk.spyk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MockedComponents {
    @Bean
    fun processableTransactionRepository(): ProcessableTransactionRepository {
        return spyk()
    }

    @Bean
    fun metaAccountingRepository(): MetaAccountingOutboxRepository {
        return spyk()
    }

    @Bean
    fun processableReceivablesRepository(): ProcessableReceivablesRepository {
        return spyk()
    }

    @Bean
    fun metaAccountingGateway(): MetaAccountingGateway {
        return spyk()
    }

    @Bean
    fun receivablesScheduleGateway(): ReceivablesScheduleGateway {
        return spyk()
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
