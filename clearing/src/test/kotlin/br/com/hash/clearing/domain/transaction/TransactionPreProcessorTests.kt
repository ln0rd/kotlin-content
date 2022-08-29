package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.InstallmentTransactionData
import br.com.hash.clearing.domain.model.PaymentLinkData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.TransactionData
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.OffsetDateTime
import kotlin.test.assertEquals

class TransactionPreProcessorTests : BehaviorSpec({
    Given("the following list of ProcessableTransaction") {
        val processableTransactions = listOf(
            ProcessableTransaction(
                transactionId = "01FN9E5C5W1PMZBJ95VKEZHY8M",
                status = ProcessableTransactionStatus.RECEIVED,
                transactionData = TransactionData(
                    id = "01FN9E5C5W1PMZBJ95VKEZHY8M",
                    hashCorrelationKey = "3d041751-dd73-40db-a00e-000054415049",
                    isoID = "606382a65b80110006d7f709",
                    merchantID = "6124ef6b1a6e5e000635d96b",
                    merchantCategoryCode = "5912",
                    terminalID = "6126679044f3ae00069bfd5c",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "897830D6-E9E6-4722-BDE0-E95039272E40",
                        dateTime = OffsetDateTime.parse("2021-11-24T13:58:48-03:00"),
                        responseCode = "00",
                        authorizationCode = "584873",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "",
                        numericCode = "",
                        alphaCode = ""
                    ),
                    dateTime = OffsetDateTime.parse("2021-11-24T13:58:48-03:00"),
                    transactionType = "purchase",
                    accountType = "credit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 12251,
                    currencyCode = "986",
                    InstallmentTransactionData(
                        installmentCount = 3,
                        installmentQualifier = "merchant"
                    ),
                    CardholderData(
                        panFirstDigits = "516292",
                        panLastDigits = "9629",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "LANDO CALRISSIAN",
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                )
            ),
            ProcessableTransaction(
                transactionId = "01FN9E5KV1EF7HWBCADV5NVGVN",
                status = ProcessableTransactionStatus.RECEIVED,
                transactionData = TransactionData(
                    id = "01FN9E5KV1EF7HWBCADV5NVGVN",
                    hashCorrelationKey = "f1cd85a8-6e1c-43b5-977b-000054415049",
                    isoID = "606382a65b80110006d7f709",
                    merchantID = "6176e22e4ad7d40007d090ab",
                    merchantCategoryCode = "5912",
                    terminalID = "6178559dd8b89900075cb907",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "D450C90F-F10E-416D-A2F5-FA8AEAD8227C",
                        dateTime = OffsetDateTime.parse("2021-11-24T13:58:56-03:00"),
                        responseCode = "00",
                        authorizationCode = "150336",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "",
                        numericCode = "",
                        alphaCode = ""
                    ),
                    dateTime = OffsetDateTime.parse("2021-11-24T13:58:56-03:00"),
                    transactionType = "purchase",
                    accountType = "debit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 2595,
                    currencyCode = "986",
                    installmentTransactionData = null,
                    CardholderData(
                        panFirstDigits = "516292",
                        panLastDigits = "8627",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "GRAND MOFF TARKIN",
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
            ),
            ProcessableTransaction(
                transactionId = "non-existent-id",
                status = ProcessableTransactionStatus.RECEIVED,
                transactionData = TransactionData(
                    id = "non-existent-id",
                    hashCorrelationKey = "f1cd85a8-6e1c-43b5-977b-000054415049",
                    isoID = "2a660630110085b870906d7f",
                    merchantID = "26176e2e4ad7d4090ab0007d",
                    merchantCategoryCode = "5912",
                    terminalID = "75cb9076178559dd8b899000",
                    AuthorizerData(
                        name = "pagseguro",
                        uniqueID = "C90FD450-0F1E-16D4-2AF5-27CFA828AEAD",
                        dateTime = OffsetDateTime.parse("2021-11-25T15:57:51-03:00"),
                        responseCode = "00",
                        authorizationCode = "510336",
                        SpecificData(
                            affiliationID = "163562222"
                        )
                    ),
                    CaptureChannelData(
                        name = "hash-pos",
                        PaymentLinkData(
                            creationTimestamp = null
                        )
                    ),
                    PaymentNetworkData(
                        name = "",
                        numericCode = "",
                        alphaCode = ""
                    ),
                    dateTime = OffsetDateTime.parse("2021-11-25T15:57:51-03:00"),
                    transactionType = "purchase",
                    accountType = "debit",
                    approved = true,
                    crossBorder = false,
                    entryMode = "icc",
                    amount = 51295,
                    currencyCode = "986",
                    installmentTransactionData = null,
                    CardholderData(
                        panFirstDigits = "516216",
                        panLastDigits = "1627",
                        panSequenceNumber = null,
                        cardExpirationDate = null,
                        cardholderName = "BOBA FETT",
                        verificationMethod = "offline-pin",
                        issuerCountryCode = "BRA"
                    ),
                    referenceTransactionId = null,
                    antifraudData = null
                ),
            ),
        )

        And("a ProcessableTransactionRepository") {
            val processableTransactionRepository = mockk<ProcessableTransactionRepository>()
            every { processableTransactionRepository.findUnprocessed() } returns processableTransactions

            val capturingSlot = slot<List<ProcessableTransaction>>()
            every { processableTransactionRepository.save(capture(capturingSlot)) } returnsArgument 0

            And("a TransactionServiceGateway that finds 2 out of 3 transactions, 1 of them with payment network data") {
                val transactionServiceResponse = listOf(
                    processableTransactions[0].transactionData.copy(
                        paymentNetworkData = PaymentNetworkData("Visa Cartão de Débito", "026", "VCD")
                    ),
                    processableTransactions[1].transactionData.copy(
                        paymentNetworkData = PaymentNetworkData("", "", "")
                    )
                )

                val transactionServiceGateway = mockk<TransactionServiceGateway>()
                every { transactionServiceGateway.findByIds(any()) } returns transactionServiceResponse

                When("the TransactionPreProcessor is invoked") {
                    val logger = mockk<Logger>(relaxed = true)

                    val transactionPreProcessor = TransactionPreProcessor(
                        processableTransactionRepository,
                        transactionServiceGateway, logger
                    )
                    transactionPreProcessor.process()

                    val savedProcessableTransactions = capturingSlot.captured

                    Then("only the processed ProcessableTransactions should be saved") {
                        assertEquals(1, savedProcessableTransactions.size)
                        assertEquals("01FN9E5C5W1PMZBJ95VKEZHY8M", savedProcessableTransactions[0].transactionId)
                    }

                    Then("the TransactionData should be updated with the paymentNetworkData information") {
                        assertEquals("Visa Cartão de Débito", savedProcessableTransactions[0].transactionData.paymentNetworkData.name)
                        assertEquals("026", savedProcessableTransactions[0].transactionData.paymentNetworkData.numericCode)
                        assertEquals("VCD", savedProcessableTransactions[0].transactionData.paymentNetworkData.alphaCode)
                    }

                    Then("the ProcessableTransaction status should be set as PREPROCESSED if paymentNetworkData is provided") {
                        assertEquals(ProcessableTransactionStatus.PREPROCESSED, savedProcessableTransactions[0].status)
                    }
                }
            }
        }

        And("a ProcessableTransactionRepository") {
            val processableTransactionRepository = mockk<ProcessableTransactionRepository>()
            every { processableTransactionRepository.findUnprocessed() } returns processableTransactions

            And("the Transaction Service cannot be reached") {
                val transactionServiceGateway = mockk<TransactionServiceGateway>()
                every { transactionServiceGateway.findByIds(any()) } returns listOf()

                When("the TransactionPreProcessor is invoked") {
                    val logger = mockk<Logger>(relaxed = true)

                    val transactionPreProcessor = TransactionPreProcessor(
                        processableTransactionRepository,
                        transactionServiceGateway, logger
                    )
                    transactionPreProcessor.process()

                    Then("the ProcessableTransactionRepository should not be invoked") {
                        verify(exactly = 0) { processableTransactionRepository.save(any()) }
                    }
                }
            }
        }
    }

    Given("an empty ProcessableTransaction list") {
        val processableTransactions = listOf<ProcessableTransaction>()

        val processableTransactionRepository = mockk<ProcessableTransactionRepository>()
        every { processableTransactionRepository.findUnprocessed() } returns processableTransactions

        And("a TransactionServiceGateway") {
            val transactionServiceGateway = mockk<TransactionServiceGateway>()

            When("the TransactionPreProcessor is invoked") {
                val logger = mockk<Logger>(relaxed = true)

                val transactionPreProcessor = TransactionPreProcessor(
                    processableTransactionRepository,
                    transactionServiceGateway, logger
                )
                transactionPreProcessor.process()

                Then("the TransactionServiceGateway should not be invoked") {
                    verify { transactionServiceGateway wasNot Called }
                }

                Then("the ProcessableTransactionRepository should not be invoked") {
                    verify(exactly = 0) { processableTransactionRepository.save(any()) }
                }
            }
        }
    }
})
