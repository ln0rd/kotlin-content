package br.com.hash.clearing.infrastructure.transaction

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class TransactionServiceConfiguration(
    @Value("\${hash.transaction-service.url}") val url: String,
    @Value("\${hash.transaction-service.username}") val username: String,
    @Value("\${hash.transaction-service.password}") val password: String
)
