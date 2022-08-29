package br.com.hash.clearing.infrastructure.input

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MockedReaderComponents {
    @Bean
    fun pubSubTransactionQueueReader(): PubSubTransactionQueueReader {
        val mock = mockk<PubSubTransactionQueueReader>()
        every { mock.pull() } just Runs

        return mock
    }
}
