package br.com.hash.clearing.domain.input

import br.com.hash.clearing.domain.model.TransactionData

interface TransactionQueueReader {
    fun read(transactionDataList: List<TransactionData>)
}
