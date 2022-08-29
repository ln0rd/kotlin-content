package br.com.hash.clearing.domain.output.meta_accounting

import br.com.hash.clearing.domain.model.TransactionData

interface OutboxRepository {
    fun findUnprocessed(): List<TransactionData>
    fun save(transactionDataList: List<TransactionData>)
}
