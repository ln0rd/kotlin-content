package br.com.hash.clearing.infrastructure.transaction

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class PricingEngineConfiguration(
    @Value("\${hash.pricing-engine.url}") val url: String,
)
