package br.com.hash.clearing.domain.model

import java.time.OffsetDateTime

data class AuthorizerData(
    val name: String,
    val uniqueID: String?,
    val dateTime: OffsetDateTime,
    val responseCode: String,
    val authorizationCode: String,
    val specificData: SpecificData
)
