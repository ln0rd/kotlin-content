package br.com.hash.clearing.infrastructure.monitoring

import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import kotlin.test.assertEquals
import kotlinx.coroutines.future.await
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(
    classes = [EndToEndTestConfiguration::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class MonitoringRoutesTests(@LocalServerPort port: Int) : DescribeSpec({
    describe("Monitoring tests") {

        suspend fun fetch(path: String): Pair<HttpStatus, String> {
            val response = WebClient
                .create()
                .get()
                .uri("http://localhost:$port$path")
                .retrieve()
                .toEntity(String::class.java)
                .toFuture()
                .await()

            val status = ObjectMapper()
                .readTree(response.body)
                .get("status")
                .asText()

            return Pair(response.statusCode, status)
        }

        it("/healthz should return HTTP status 200 and a OK message") {
            val (httpStatus, messageStatus) = fetch("/healthz")

            assertEquals(HttpStatus.OK, httpStatus)
            assertEquals("OK", messageStatus)
        }

        it("/readiness should return HTTP status 200 and a OK message") {
            val (httpStatus, messageStatus) = fetch("/readiness")

            assertEquals(HttpStatus.OK, httpStatus)
            assertEquals("OK", messageStatus)
        }
    }
})
