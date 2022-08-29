package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.model.TransactionData

interface TransactionServiceGateway {
    fun findByIds(ids: List<String>): List<TransactionData>
}
