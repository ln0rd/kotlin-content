package br.com.hash.clearing.domain.mock

import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.InstallmentTransactionData
import br.com.hash.clearing.domain.model.PaymentLinkData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.TransactionData
import java.time.OffsetDateTime

class FakeTransactionData {

    fun createDebitType(): TransactionData {
        return TransactionData(
            id = "1539985",
            hashCorrelationKey = "2501ed2d-c41a-427a-b7f3-40c97d69c7e2",
            isoID = "5cf141b986642840656717f1",
            merchantID = "5ae0c5077e70c30004c907b2",
            merchantCategoryCode = "1234",
            terminalID = "1539985",
            AuthorizerData(
                name = "Pagseguro",
                uniqueID = "22781384615723",
                dateTime = OffsetDateTime.parse("2020-09-03T08:59:07-03:00"),
                responseCode = "00",
                authorizationCode = "A12285",
                SpecificData(affiliationID = "139235585")
            ),
            CaptureChannelData(name = "hash-pos", PaymentLinkData(creationTimestamp = null)),
            PaymentNetworkData(
                name = "Visa Cartão de Débito",
                numericCode = "026",
                alphaCode = "VCD"
            ),
            dateTime = OffsetDateTime.parse("2021-10-14T16:03:21-03:00"),
            transactionType = "purchase",
            accountType = "debit",
            approved = true,
            crossBorder = false,
            entryMode = "icc",
            amount = 20000000,
            currencyCode = "986",
            InstallmentTransactionData(installmentCount = 1, installmentQualifier = null),
            CardholderData(
                panFirstDigits = "516292",
                panLastDigits = "0253",
                panSequenceNumber = "0428",
                cardExpirationDate = "02-2027",
                cardholderName = "Leo",
                verificationMethod = "offline-pin",
                issuerCountryCode = "77"
            ),
            referenceTransactionId = null,
            antifraudData = null
        )
    }

    fun createCreditType(): TransactionData {
        return TransactionData(
            id = "1539985",
            hashCorrelationKey = "2501ed2d-c41a-427a-b7f3-40c97d69c7e2",
            isoID = "5cf141b986642840656717f1",
            merchantID = "5ae0c5077e70c30004c907b2",
            merchantCategoryCode = "1234",
            terminalID = "1539985",
            AuthorizerData(
                name = "Pagseguro",
                uniqueID = "22781384615723",
                dateTime = OffsetDateTime.parse("2020-09-03T08:59:07-03:00"),
                responseCode = "00",
                authorizationCode = "A12285",
                SpecificData(affiliationID = "139235585")
            ),
            CaptureChannelData(name = "hash-pos", PaymentLinkData(creationTimestamp = null)),
            PaymentNetworkData(
                name = "Visa Cartão de Débito",
                numericCode = "026",
                alphaCode = "VCD"
            ),
            dateTime = OffsetDateTime.parse("2021-10-14T16:03:21-03:00"),
            transactionType = "purchase",
            accountType = "credit",
            approved = true,
            crossBorder = false,
            entryMode = "icc",
            amount = 20000000,
            currencyCode = "986",
            InstallmentTransactionData(installmentCount = 3, installmentQualifier = null),
            CardholderData(
                panFirstDigits = "516292",
                panLastDigits = "0253",
                panSequenceNumber = "0428",
                cardExpirationDate = "02-2027",
                cardholderName = "Leo",
                verificationMethod = "offline-pin",
                issuerCountryCode = "77"
            ),
            referenceTransactionId = null,
            antifraudData = null
        )
    }

    fun createCreditTypeInLastDayOfMonth(): TransactionData {
        return TransactionData(
            id = "1539985",
            hashCorrelationKey = "2501ed2d-c41a-427a-b7f3-40c97d69c7e2",
            isoID = "5cf141b986642840656717f1",
            merchantID = "5ae0c5077e70c30004c907b2",
            merchantCategoryCode = "1234",
            terminalID = "1539985",
            AuthorizerData(
                name = "Pagseguro",
                uniqueID = "22781384615723",
                dateTime = OffsetDateTime.parse("2022-01-31T16:03:21-03:00"),
                responseCode = "00",
                authorizationCode = "A12285",
                SpecificData(affiliationID = "139235585")
            ),
            CaptureChannelData(name = "hash-pos", PaymentLinkData(creationTimestamp = null)),
            PaymentNetworkData(
                name = "Visa Cartão de Débito",
                numericCode = "026",
                alphaCode = "VCD"
            ),
            dateTime = OffsetDateTime.parse("2022-01-31T16:03:21-03:00"),
            transactionType = "purchase",
            accountType = "credit",
            approved = true,
            crossBorder = false,
            entryMode = "icc",
            amount = 20000000,
            currencyCode = "986",
            InstallmentTransactionData(installmentCount = 3, installmentQualifier = null),
            CardholderData(
                panFirstDigits = "516292",
                panLastDigits = "0253",
                panSequenceNumber = "0428",
                cardExpirationDate = "02-2027",
                cardholderName = "Leo",
                verificationMethod = "offline-pin",
                issuerCountryCode = "77"
            ),
            referenceTransactionId = null,
            antifraudData = null
        )
    }

    fun createCreditReversalPurchaseType(): TransactionData {
        return TransactionData(
            id = "1539985",
            hashCorrelationKey = "2501ed2d-c41a-427a-b7f3-40c97d69c7e2",
            isoID = "5cf141b986642840656717f1",
            merchantID = "5ae0c5077e70c30004c907b2",
            merchantCategoryCode = "1234",
            terminalID = "1539985",
            AuthorizerData(
                name = "Pagseguro",
                uniqueID = "22781384615723",
                dateTime = OffsetDateTime.parse("2022-01-31T16:03:21-03:00"),
                responseCode = "00",
                authorizationCode = "A12285",
                SpecificData(affiliationID = "139235585")
            ),
            CaptureChannelData(name = "hash-pos", PaymentLinkData(creationTimestamp = null)),
            PaymentNetworkData(
                name = "Visa Cartão de Débito",
                numericCode = "026",
                alphaCode = "VCD"
            ),
            dateTime = OffsetDateTime.parse("2022-01-31T16:03:21-03:00"),
            transactionType = "purchase-reversal",
            accountType = "credit",
            approved = true,
            crossBorder = false,
            entryMode = "icc",
            amount = 20000000,
            currencyCode = "986",
            InstallmentTransactionData(installmentCount = 3, installmentQualifier = null),
            CardholderData(
                panFirstDigits = "516292",
                panLastDigits = "0253",
                panSequenceNumber = "0428",
                cardExpirationDate = "02-2027",
                cardholderName = "Leo",
                verificationMethod = "offline-pin",
                issuerCountryCode = "77"
            ),
            referenceTransactionId = null,
            antifraudData = null
        )
    }
}
