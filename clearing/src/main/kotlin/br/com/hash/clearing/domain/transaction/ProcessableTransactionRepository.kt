package br.com.hash.clearing.domain.transaction

interface ProcessableTransactionRepository {
    fun findUnprocessed(): List<ProcessableTransaction>
    fun findReadyToProcess(): List<ProcessableTransaction>
    fun save(transactionDataList: List<ProcessableTransaction>): List<ProcessableTransaction>
}
