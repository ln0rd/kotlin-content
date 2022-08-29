package br.com.hash.clearing.domain.output.receivables_schedule

interface ReceivablesScheduleGateway {
    fun send(receivablesList: List<Receivable>, idempotency: String): List<Receivable>?
}
