package br.com.hash.clearing.infrastructure.input

import br.com.hash.clearing.domain.input.TransactionQueueReader
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage
import org.springframework.stereotype.Component

@Component
class PubSubTransactionQueueReader(
    private val transactionQueueReader: TransactionQueueReader,
    private val pubSubConfiguration: PubSubConfiguration,
    private val pubSubTemplate: PubSubTemplate,
    logger: Logger
) {
    private val log = logger.withContext(TransactionQueueReader::class)
    private val objectReader = DefaultObjectMapper().reader()

    fun pull() {
        val messages =
            pubSubTemplate.pull(
                pubSubConfiguration.subscriptionId,
                pubSubConfiguration.maxMessages,
                true
            )

        if (messages.isEmpty()) {
            return
        }

        try {
            val transactionDataList = translateMessagesToTransactionDataList(messages)

            transactionQueueReader.read(transactionDataList)

            pubSubTemplate.ack(messages).get()
        } catch (e: Exception) {
            log.warn(e.message)
            pubSubTemplate.nack(messages).get()
        }
    }

    private fun translateMessagesToTransactionDataList(
        messages: List<AcknowledgeablePubsubMessage>
    ): List<TransactionData> {
        val transactionDataList = mutableListOf<TransactionData>()

        for (acknowledgeablePubsubMessage in messages) {
            val data = acknowledgeablePubsubMessage.pubsubMessage.data.toByteArray()
            val cloudEvent = objectReader.readValue(data, CloudEvent::class.java)
            val transactionData = cloudEvent.data
            transactionDataList.add(transactionData)
        }

        return transactionDataList
    }
}
