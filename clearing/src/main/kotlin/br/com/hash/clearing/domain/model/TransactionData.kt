package br.com.hash.clearing.domain.model

import java.time.OffsetDateTime

data class TransactionData(
    val id: String,
    val hashCorrelationKey: String,
    val isoID: String,
    val merchantID: String,
    val merchantCategoryCode: String,
    val terminalID: String?,
    val authorizerData: AuthorizerData,
    val captureChannelData: CaptureChannelData?,
    val paymentNetworkData: PaymentNetworkData,
    val dateTime: OffsetDateTime,
    val transactionType: String,
    val accountType: String,
    val approved: Boolean,
    val crossBorder: Boolean,
    val entryMode: String,
    val amount: Int,
    val currencyCode: String,
    val installmentTransactionData: InstallmentTransactionData?,
    val cardholderData: CardholderData,
    val referenceTransactionId: String?,
    val antifraudData: AntifraudData?
) {
    fun hasPaymentNetworkData(): Boolean {
        return paymentNetworkData.name.isNotEmpty()
    }

    fun withFiveDecimalPlaces(): TransactionData {
        return copy(amount = amount * 1000)
    }
}
