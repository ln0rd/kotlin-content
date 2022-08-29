package br.com.hash.clearing.infrastructure.output.meta_accounting

import br.com.hash.clearing.domain.output.meta_accounting.MetaAccountingSender
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MockedSenderComponents {
    @Bean
    fun metaAccountingSender(): MetaAccountingSender {
        val mock = mockk<MetaAccountingSender>()
        every { mock.send() } just Runs

        return mock
    }
}
