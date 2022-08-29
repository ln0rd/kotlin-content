package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.event.Event
import br.com.hash.clearing.domain.event.EventPublisher
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.AuthorizerData
import br.com.hash.clearing.domain.model.CaptureChannelData
import br.com.hash.clearing.domain.model.CardholderData
import br.com.hash.clearing.domain.model.InstallmentTransactionData
import br.com.hash.clearing.domain.model.PaymentLinkData
import br.com.hash.clearing.domain.model.PaymentNetworkData
import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.RevenueDetail
import br.com.hash.clearing.domain.model.SpecificData
import br.com.hash.clearing.domain.model.SplitDetail
import br.com.hash.clearing.domain.model.TransactionData
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Called
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TransactionProcessorTests : BehaviorSpec({
    Given("the following list of ProcessableTransaction") {
        val processableTransactions = listOf(
            ProcessableTransaction(
                transactionId = "01FN9E5C5W1PMZBJ95VKEZHY8M",
                status = ProcessableTransactionStatus.PREPROCESSED,
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
                        name = "Elo Cartão de Crédito",
                        numericCode = "008",
                        alphaCode = "ECC"
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
                status = ProcessableTransactionStatus.PREPROCESSED,
                transactionData = TransactionData(
                    id = "01FN9E5KV1EF7HWBCADV5NVGVN",
                    hashCorrelationKey = "f1cd85a8-6e1c-43b5-977b-000054415049",
                    isoID = "606382a65b80110006d7f709",
                    merchantID = "e4a6e22d400d761707d090ab",
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
                        name = "Visa Cartão de Débito",
                        numericCode = "026",
                        alphaCode = "VCD"
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
            )
        )

        And("an eventPublisher") {
            val eventPublisher = mockk<EventPublisher>()
            val eventPublisherArguments = slot<Event>()
            every { eventPublisher.publish(capture(eventPublisherArguments)) } just Runs

            And("a ProcessableTransactionRepository") {
                val processableTransactionRepository = mockk<ProcessableTransactionRepository>()
                every { processableTransactionRepository.findReadyToProcess() } returns processableTransactions

                val repositoryArguments = slot<List<ProcessableTransaction>>()
                every { processableTransactionRepository.save(capture(repositoryArguments)) } returnsArgument 0

                And("a Pricing Engine with the following results") {
                    val pricingDetails = listOf(
                        PricingDetails(
                            transactionId = "01FN9E5C5W1PMZBJ95VKEZHY8M",
                            listOf(RevenueDetail(1, "6124ef6b1a6e5e000635d96b", 1200)),
                            listOf(RevenueDetail(1, "6124ef6b1a6e5e000635d96b", 700)),
                            listOf(SplitDetail("6124ef6b1a6e5e000635d96b", 1, 12251000, 1200)),
                        )
                    )

                    val pricingEngineGateway = mockk<PricingEngineGateway>()
                    val pricingEngineArguments = slot<List<TransactionData>>()
                    every { pricingEngineGateway.calculate(capture(pricingEngineArguments)) } returns pricingDetails

                    When("the TransactionProcessor is invoked") {
                        val logger = mockk<Logger>(relaxed = true)

                        val transactionProcessor = TransactionProcessor(
                            eventPublisher, processableTransactionRepository,
                            pricingEngineGateway, logger
                        )
                        transactionProcessor.process()

                        Then("the Pricing Engine should have been invoked with amounts converted to 5 decimal places") {
                            val capturedPricingEngineArguments = pricingEngineArguments.captured
                            assertEquals(2, capturedPricingEngineArguments.size)
                            assertEquals(12251000, capturedPricingEngineArguments[0].amount)
                            assertEquals(2595000, capturedPricingEngineArguments[1].amount)
                        }

                        Then("only the processed ProcessableTransactions should be saved") {
                            val capturedRepositoryArguments = repositoryArguments.captured
                            verify(exactly = 1) { processableTransactionRepository.save(capturedRepositoryArguments) }

                            assertEquals(1, capturedRepositoryArguments.size)
                            assertEquals("01FN9E5C5W1PMZBJ95VKEZHY8M", capturedRepositoryArguments[0].transactionId)
                        }

                        Then("the ProcessableTransactions should contain the pricing calculation") {
                            val capturedRepositoryArguments = repositoryArguments.captured
                            val savedPricingDetails = capturedRepositoryArguments[0].pricingDetails
                            assertNotNull(savedPricingDetails)
                            assertEquals(pricingDetails[0], savedPricingDetails)
                        }

                        Then("the ProcessableTransaction status should be set as PROCESSSED only for the processed transactions") {
                            val capturedRepositoryArguments = repositoryArguments.captured
                            assertEquals(ProcessableTransactionStatus.PROCESSED, capturedRepositoryArguments[0].status)
                        }

                        Then("a TransactionProcessedEvent with the processed transactions should be emitted") {
                            val capturedEventPublisherArguments = eventPublisherArguments.captured
                            verify(exactly = 1) { eventPublisher.publish(capturedEventPublisherArguments) }

                            assertTrue(capturedEventPublisherArguments is TransactionProcessedEvent)

                            val transactionProcessResults = capturedEventPublisherArguments.transactionProcessResults
                            assertEquals(1, transactionProcessResults.size)
                            assertEquals("01FN9E5C5W1PMZBJ95VKEZHY8M", transactionProcessResults[0].transactionData.id)
                            assertEquals("01FN9E5C5W1PMZBJ95VKEZHY8M", transactionProcessResults[0].pricingDetails.transactionId)
                        }
                    }

                    // Reset the following mocked bean states, except for return configurations
                    clearMocks(processableTransactionRepository, eventPublisher, answers = false)
                }
                And("the Pricing Engine cannot be reached") {
                    val pricingEngineGateway = mockk<PricingEngineGateway>()
                    every { pricingEngineGateway.calculate(any()) } returns listOf()

                    When("the TransactionProcessor is invoked") {
                        val logger = mockk<Logger>(relaxed = true)

                        val transactionProcessor = TransactionProcessor(
                            eventPublisher, processableTransactionRepository,
                            pricingEngineGateway, logger
                        )
                        transactionProcessor.process()

                        Then("the ProcessableTransactionRepository should not be invoked") {
                            verify(exactly = 0) { processableTransactionRepository.save(any()) }
                        }

                        Then("the TransactionProcessedEvent should not be emitted") {
                            verify(exactly = 0) { eventPublisher.publish(any()) }
                        }
                    }
                }
            }
        }
    }
    Given("an empty list of ProcessableTransaction") {
        val processableTransactions = listOf<ProcessableTransaction>()

        And("an eventPublisher") {
            val eventPublisher = mockk<EventPublisher>()

            And("a ProcessableTransactionRepository") {
                val processableTransactionRepository = mockk<ProcessableTransactionRepository>()
                every { processableTransactionRepository.findReadyToProcess() } returns processableTransactions

                And("a pricing engine gateway") {
                    val pricingEngineGateway = mockk<PricingEngineGateway>()

                    When("the TransactionProcessor is invoked") {
                        val logger = mockk<Logger>(relaxed = true)

                        val transactionProcessor = TransactionProcessor(
                            eventPublisher, processableTransactionRepository,
                            pricingEngineGateway, logger
                        )
                        transactionProcessor.process()

                        Then("No event should be emitted") {
                            verify { eventPublisher wasNot Called }
                        }

                        Then("the ProcessableTransactionRepository should not be invoked") {
                            verify(exactly = 0) { processableTransactionRepository.save(any()) }
                        }

                        Then("the PricingEngineGateway should not be invoked") {
                            verify { pricingEngineGateway wasNot Called }
                        }
                    }
                }
            }
        }
    }
})
