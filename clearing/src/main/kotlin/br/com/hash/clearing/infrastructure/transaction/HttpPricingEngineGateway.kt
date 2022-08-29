package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.transaction.PricingEngineGateway
import br.com.hash.clearing.infrastructure.http.DefaultWebClient
import java.util.logging.Logger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class HttpPricingEngineGateway(pricingEngineConfiguration: PricingEngineConfiguration) : PricingEngineGateway {

    private val logger: Logger = Logger.getLogger(HttpPricingEngineGateway::class.simpleName)

    private val webClient = DefaultWebClient.builder().build()

    private val url = pricingEngineConfiguration.url

    override fun calculate(transactionDataList: List<TransactionData>): List<PricingDetails> {
        return getCalculateFromPricingAPI(transactionDataList) ?: listOf()
    }

    private fun getCalculateFromPricingAPI(transactionDataList: List<TransactionData>): List<PricingDetails>? {
        return try {
            webClient
                .post()
                .uri("$url/calculate_pricing")
                .bodyValue(transactionDataList)
                .retrieve()
                .bodyToMono<List<PricingDetails>>()
                .block()
        } catch (e: Exception) {
            logger.warning(e.message)
            null
        }
    }
}
