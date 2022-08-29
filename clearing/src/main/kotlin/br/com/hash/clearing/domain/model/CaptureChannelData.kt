package br.com.hash.clearing.domain.model

data class CaptureChannelData(
    val name: String,
    val paymentLinkData: PaymentLinkData?,
)
