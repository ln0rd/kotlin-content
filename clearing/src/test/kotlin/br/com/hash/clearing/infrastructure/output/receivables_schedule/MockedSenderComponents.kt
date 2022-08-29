package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleSender
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MockedSenderComponents {
    @Bean
    fun receivablesScheduleSender(): ReceivablesScheduleSender {
        val mock = mockk<ReceivablesScheduleSender>()
        every { mock.send() } just Runs

        return mock
    }
}
