package br.com.hash.clearing.infrastructure.output.meta_accounting

import br.com.hash.clearing.domain.output.meta_accounting.MetaAccountingGateway
import br.com.hash.clearing.domain.output.meta_accounting.MetaAccountingSender
import br.com.hash.clearing.domain.output.meta_accounting.OutboxRepository
import br.com.hash.clearing.domain.output.meta_accounting.TransactionProcessedEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetaAccountingComponents {

    @Bean
    fun metaAccountingEventListener(outboxRepository: OutboxRepository): TransactionProcessedEventListener {
        return TransactionProcessedEventListener(outboxRepository)
    }

    @Bean
    fun metaAccountingSender(metaAccountingGateway: MetaAccountingGateway, outboxRepository: OutboxRepository): MetaAccountingSender {
        return MetaAccountingSender(metaAccountingGateway, outboxRepository)
    }
}
