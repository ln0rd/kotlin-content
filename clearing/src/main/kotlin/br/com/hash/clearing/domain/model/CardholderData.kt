package br.com.hash.clearing.domain.model

data class CardholderData(
    val panFirstDigits: String,
    val panLastDigits: String,
    val panSequenceNumber: String?,
    val cardExpirationDate: String?,
    val cardholderName: String?,
    val verificationMethod: String?,
    val issuerCountryCode: String?
)
