package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesRepository
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleGateway
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleSender
import br.com.hash.clearing.domain.output.receivables_schedule.TransactionProcessedEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReceivablesScheduleComponents {

    @Bean
    fun receivablesScheduleEventListener(processableReceivablesRepository: ProcessableReceivablesRepository, logger: Logger): TransactionProcessedEventListener {
        return TransactionProcessedEventListener(processableReceivablesRepository, logger)
    }

    @Bean
    fun receivablesScheduleSender(receivablesScheduleGateway: ReceivablesScheduleGateway, processableReceivablesRepository: ProcessableReceivablesRepository, logger: Logger): ReceivablesScheduleSender {
        return ReceivablesScheduleSender(receivablesScheduleGateway, processableReceivablesRepository, logger)
    }
}
