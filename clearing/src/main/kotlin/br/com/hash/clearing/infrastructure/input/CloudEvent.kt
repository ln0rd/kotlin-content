package br.com.hash.clearing.infrastructure.input

import br.com.hash.clearing.domain.model.TransactionData

data class CloudEvent(
    val specversion: String,
    val source: String,
    val type: String,
    val dataschema: String,
    val id: String,
    val data: TransactionData
)
