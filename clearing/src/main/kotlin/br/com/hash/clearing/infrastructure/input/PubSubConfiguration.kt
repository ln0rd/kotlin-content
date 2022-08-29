package br.com.hash.clearing.infrastructure.input

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class PubSubConfiguration(env: Environment) {
    val topicId: String = env.getRequiredProperty("gcp.pubsub.topicId")
    val subscriptionId: String = env.getRequiredProperty("gcp.pubsub.subscriptionId")
    val maxMessages: Int = env.getRequiredProperty("gcp.pubsub.max-messages").toInt()
}
