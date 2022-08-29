package br.com.hash.clearing.infrastructure.http

import br.com.hash.clearing.infrastructure.json.DefaultObjectMapper
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.WebClient

object DefaultWebClient {
    fun builder(): WebClient.Builder = WebClient
        .builder()
        .codecs { configurer ->
            val jacksonDecoder = Jackson2JsonDecoder(DefaultObjectMapper())
            configurer.defaultCodecs().jackson2JsonDecoder(jacksonDecoder)

            val jacksonEncoder = Jackson2JsonEncoder(DefaultObjectMapper())
            configurer.defaultCodecs().jackson2JsonEncoder(jacksonEncoder)
        }
}
