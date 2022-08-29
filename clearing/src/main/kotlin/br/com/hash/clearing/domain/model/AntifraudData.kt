package br.com.hash.clearing.domain.model

data class AntifraudData(
    val name: String?,
    val requestID: String?,
    val flaggedAsSuspicious: Boolean?
)
