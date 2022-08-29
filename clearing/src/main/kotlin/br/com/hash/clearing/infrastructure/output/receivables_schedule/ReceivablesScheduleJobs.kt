package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ReceivablesScheduleJobs(private val receivablesScheduleSender: ReceivablesScheduleSender) {

    @Scheduled(fixedDelayString = "#{\${clearing.jobs.receivablesScheduleSender}}")
    fun receivablesScheduleSenderJob() {
        receivablesScheduleSender.send()
    }
}
