package br.com.hash.clearing.infrastructure.output.receivables_schedule

import com.fasterxml.jackson.annotation.JsonProperty

data class ReceivableScheduleResponse(
    @JsonProperty("message")
    val message: String
)
