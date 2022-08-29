package br.com.hash.clearing.infrastructure.input

import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.input.TransactionQueueReader
import br.com.hash.clearing.domain.input.TransactionReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InputComponents {

    @Bean
    fun transactionQueueReader(eventPublisher: EventPublisher): TransactionQueueReader {
        return TransactionReader(eventPublisher)
    }
}
