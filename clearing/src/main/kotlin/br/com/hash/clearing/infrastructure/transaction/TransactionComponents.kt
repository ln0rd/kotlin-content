package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.transaction.PricingEngineGateway
import br.com.hash.clearing.domain.transaction.ProcessableTransactionRepository
import br.com.hash.clearing.domain.transaction.TransactionPreProcessor
import br.com.hash.clearing.domain.transaction.TransactionProcessor
import br.com.hash.clearing.domain.transaction.TransactionReadEventListener
import br.com.hash.clearing.domain.transaction.TransactionServiceGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransactionComponents {

    @Bean
    fun transactionReadEventListener(processableTransactionRepository: ProcessableTransactionRepository): TransactionReadEventListener {
        return TransactionReadEventListener(processableTransactionRepository)
    }

    @Bean
    fun transactionPreProcessor(
        processableTransactionRepository: ProcessableTransactionRepository,
        transactionServiceGateway: TransactionServiceGateway,
        logger: Logger
    ): TransactionPreProcessor {
        return TransactionPreProcessor(processableTransactionRepository, transactionServiceGateway, logger)
    }

    @Bean
    fun transactionProcessor(
        eventPublisher: EventPublisher,
        processableTransactionRepository: ProcessableTransactionRepository,
        pricingEngineGateway: PricingEngineGateway,
        logger: Logger
    ): TransactionProcessor {
        return TransactionProcessor(eventPublisher, processableTransactionRepository, pricingEngineGateway, logger)
    }
}
