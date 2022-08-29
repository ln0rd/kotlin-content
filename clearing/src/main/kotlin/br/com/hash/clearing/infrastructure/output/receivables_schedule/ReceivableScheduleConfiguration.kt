package br.com.hash.clearing.infrastructure.output.receivables_schedule

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class ReceivableScheduleConfiguration(
    @Value("\${hash.receivable-schedule.url}") val url: String,
)
