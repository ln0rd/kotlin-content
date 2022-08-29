package br.com.hash.clearing.domain.output.receivables_schedule

interface ProcessableReceivablesRepository {
    fun findNotSent(): List<ProcessableReceivables>
    fun save(processableReceivables: List<ProcessableReceivables>): List<ProcessableReceivables>
}
