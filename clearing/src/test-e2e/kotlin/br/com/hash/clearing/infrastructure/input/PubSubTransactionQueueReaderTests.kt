package br.com.hash.clearing.infrastructure.input

import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import br.com.hash.clearing.infrastructure.filesystem.resourceAsBytes
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Called
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.gcp.pubsub.PubSubAdmin
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class PubSubTransactionQueueReaderTests(
    pubSubTransactionQueueReader: PubSubTransactionQueueReader,
    pubSubTestTransactionEventListener: PubSubTestTransactionEventListener,
    pubSubConfiguration: PubSubConfiguration,
    pubSubAdmin: PubSubAdmin,
    pubSubTemplate: PubSubTemplate
) : BehaviorSpec() {
    init {
        Given("the Transaction Events topic and the Clearing subscription") {
            DatabaseState.reset()

            pubSubAdmin.createTopic(pubSubConfiguration.topicId)
            pubSubAdmin.createSubscription(
                pubSubConfiguration.subscriptionId,
                pubSubConfiguration.topicId
            )

            When("two CloudEvents with transaction events are published in the PubSub topic") {
                publishAndWait(
                    pubSubTemplate,
                    pubSubConfiguration.topicId,
                    resourceAsBytes("pubsub/IM1-example.json")
                )
                publishAndWait(
                    pubSubTemplate,
                    pubSubConfiguration.topicId,
                    resourceAsBytes("pubsub/a-real-transaction.json")
                )

                And("the PubSubTransactionQueueReader pulls from the pubsub") {
                    pubSubTransactionQueueReader.pull()

                    Then("a TransactionReadEvent should be emitted with the transaction events") {
                        val event = pubSubTestTransactionEventListener.event
                        assertNotNull(event)

                        assertEquals(2, event.transactionDataList.size)

                        val sortedTransactionDataList =
                            event.transactionDataList.sortedWith { t1, t2 ->
                                t1.dateTime.compareTo(t2.dateTime)
                            }

                        val transactionData = sortedTransactionDataList[0]
                        assertEquals(transactionData.id, "1539985")
                        assertEquals(
                            transactionData.hashCorrelationKey,
                            "2501ed2d-c41a-427a-b7f3-40c97d69c7e2"
                        )
                        assertEquals(transactionData.isoID, "5cf141b986642840656717f1")
                        assertEquals(transactionData.merchantID, "5ae0c5077e70c30004c907b2")
                        assertEquals(transactionData.merchantCategoryCode, "1234")
                        assertEquals(transactionData.terminalID, "1539985")
                        assertEquals(transactionData.authorizerData.name, "Pagseguro")
                        assertEquals(transactionData.authorizerData.uniqueID, "22781384615723")
                        assertEquals(
                            transactionData.authorizerData.dateTime,
                            OffsetDateTime.parse("2020-09-03T08:59:05-03:00")
                        )
                        assertEquals(transactionData.authorizerData.responseCode, "00")
                        assertEquals(transactionData.authorizerData.authorizationCode, "A12285")
                        assertEquals(
                            transactionData.authorizerData.specificData.affiliationID,
                            "139235585"
                        )
                        assertEquals(transactionData.captureChannelData?.name, "hash-pos")
                        assertNull(transactionData.captureChannelData?.paymentLinkData)
                        assertEquals(
                            transactionData.paymentNetworkData.name,
                            "Visa Cartão de Débito"
                        )
                        assertEquals(transactionData.paymentNetworkData.numericCode, "026")
                        assertEquals(transactionData.paymentNetworkData.alphaCode, "VCD")
                        assertEquals(transactionData.dateTime, OffsetDateTime.parse("2020-09-03T08:59:05-03:00"))
                        assertEquals(transactionData.transactionType, "purchase")
                        assertEquals(transactionData.accountType, "debit")
                        assertEquals(transactionData.approved, true)
                        assertEquals(transactionData.crossBorder, false)
                        assertEquals(transactionData.entryMode, "icc")
                        assertEquals(transactionData.amount, 12000)
                        assertEquals(transactionData.currencyCode, "986")
                        assertNull(transactionData.installmentTransactionData)
                        assertEquals(transactionData.cardholderData.panFirstDigits, "516292")
                        assertEquals(transactionData.cardholderData.panLastDigits, "0253")
                        assertNull(transactionData.cardholderData.panSequenceNumber)
                        assertEquals(transactionData.cardholderData.cardExpirationDate, "0428")
                        assertNull(transactionData.cardholderData.cardholderName)
                        assertEquals(
                            transactionData.cardholderData.verificationMethod,
                            "offline-pin"
                        )
                        assertEquals(transactionData.cardholderData.issuerCountryCode, "076")
                        assertNull(transactionData.referenceTransactionId)
                        assertNull(transactionData.antifraudData)

                        val anotherTransactionData = sortedTransactionDataList[1]
                        assertEquals(anotherTransactionData.id, "0EW1FMPDAAWHHWKJ1QPXREQYN8")
                        assertEquals(
                            anotherTransactionData.hashCorrelationKey,
                            "c936387b-abd0-54ed-bfe9-054410005049"
                        )
                        assertEquals(anotherTransactionData.isoID, "608286301a65b10006d7f709")
                        assertEquals(anotherTransactionData.merchantID, "5b61b02cf3ce4f0007076519")
                        assertEquals(anotherTransactionData.merchantCategoryCode, "5912")
                        assertEquals(anotherTransactionData.terminalID, "83b61278f1ea8d0007b3652a")
                        assertEquals(anotherTransactionData.authorizerData.name, "pagseguro")
                        assertEquals(
                            anotherTransactionData.authorizerData.dateTime,
                            OffsetDateTime.parse("2021-11-12T12:05:06-03:00")
                        )
                        assertEquals(anotherTransactionData.authorizerData.responseCode, "00")
                        assertEquals(
                            anotherTransactionData.authorizerData.authorizationCode,
                            "827603"
                        )
                        assertEquals(
                            anotherTransactionData.authorizerData.specificData.affiliationID,
                            "163562222"
                        )
                        assertEquals(anotherTransactionData.captureChannelData?.name, "hash-pos")
                        assertNull(
                            anotherTransactionData
                                .captureChannelData
                                ?.paymentLinkData
                                ?.creationTimestamp
                        )
                        assertEquals(anotherTransactionData.paymentNetworkData.name, "")
                        assertEquals(anotherTransactionData.paymentNetworkData.alphaCode, "")
                        assertEquals(anotherTransactionData.paymentNetworkData.numericCode, "")
                        assertEquals(
                            anotherTransactionData.dateTime,
                            OffsetDateTime.parse("2021-11-12T12:05:06-03:00")
                        )
                        assertEquals(
                            anotherTransactionData.transactionType,
                            "purchase"
                        )
                        assertEquals(anotherTransactionData.accountType, "credit")
                        assertEquals(anotherTransactionData.approved, true)
                        assertEquals(anotherTransactionData.crossBorder, false)
                        assertEquals(anotherTransactionData.entryMode, "icc")
                        assertEquals(anotherTransactionData.amount, 6440)
                        assertEquals(anotherTransactionData.currencyCode, "986")
                        assertEquals(
                            anotherTransactionData.installmentTransactionData?.installmentCount,
                            3
                        )
                        assertEquals(
                            anotherTransactionData
                                .installmentTransactionData
                                ?.installmentQualifier,
                            "merchant"
                        )
                        assertEquals(anotherTransactionData.cardholderData.panFirstDigits, "548591")
                        assertEquals(anotherTransactionData.cardholderData.panLastDigits, "1215")
                        assertNull(anotherTransactionData.cardholderData.panSequenceNumber)
                        assertNull(anotherTransactionData.cardholderData.cardExpirationDate)
                        assertEquals(
                            anotherTransactionData.cardholderData.cardholderName,
                            "ANAKIN SKYWALKER"
                        )
                        assertEquals(
                            anotherTransactionData.cardholderData.verificationMethod,
                            "offline-pin"
                        )
                        assertEquals(anotherTransactionData.cardholderData.issuerCountryCode, "BRA")
                        assertNull(anotherTransactionData.referenceTransactionId)
                        assertNull(anotherTransactionData.antifraudData)
                    }

                    Then("the messages should be consumed from the PubSub subscription") {
                        val message = pubSubTemplate.pullNext(pubSubConfiguration.subscriptionId)
                        assertNull(message)
                    }
                }
            }

            When("a message is published in the topic") {
                publishAndWait(
                    pubSubTemplate,
                    pubSubConfiguration.topicId,
                    resourceAsBytes("pubsub/IM1-example.json")
                )

                And("an error occurs while processing the message") {
                    mockkObject(pubSubTestTransactionEventListener)
                    every { pubSubTestTransactionEventListener.handleEvent(any()) } throws
                        RuntimeException("that's why you fail")

                    pubSubTransactionQueueReader.pull()

                    Then("the message should not be acknowledged") {
                        val message = pubSubTemplate.pullNext(pubSubConfiguration.subscriptionId)
                        assertNotNull(message)
                    }

                    unmockkObject(pubSubTestTransactionEventListener)
                }
            }

            When("there is no messages in the topic") {
                And("the TransactionQueueReader tries to pull") {
                    mockkObject(pubSubTestTransactionEventListener)

                    pubSubTransactionQueueReader.pull()

                    Then("it should send no events") {
                        verify { pubSubTestTransactionEventListener wasNot Called }
                        confirmVerified(pubSubTestTransactionEventListener)
                    }

                    unmockkObject(pubSubTestTransactionEventListener)
                }
            }
        }
    }

    fun <T> publishAndWait(pubSubTemplate: PubSubTemplate, topic: String, payload: T) {
        pubSubTemplate.publish(topic, payload).completable().join()
    }
}
