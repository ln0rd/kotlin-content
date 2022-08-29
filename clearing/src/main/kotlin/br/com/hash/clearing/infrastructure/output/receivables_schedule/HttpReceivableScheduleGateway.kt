package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.domain.output.receivables_schedule.ReceivablesScheduleGateway
import br.com.hash.clearing.infrastructure.http.DefaultWebClient
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class HttpReceivableScheduleGateway(receivableScheduleConfiguration: ReceivableScheduleConfiguration, logger: Logger) :
    ReceivablesScheduleGateway {

    private val log = logger.withContext(HttpReceivableScheduleGateway::class)

    private val url: String = receivableScheduleConfiguration.url

    private val webClient = DefaultWebClient.builder().build()

    override fun send(receivablesList: List<Receivable>, idempotency: String): List<Receivable>? {
        val receivablesCreated: ReceivableScheduleResponse? =
            sendToReceivableSchedule(HttpReceivableScheduleAdapter.createInfrastructureReceivables(receivablesList), idempotency)
        return if (receivablesCreated !== null) receivablesList else null
    }

    private fun sendToReceivableSchedule(receivables: Receivables, idempotency: String): ReceivableScheduleResponse? {
        return try {
            webClient
                .post()
                .uri("$url/receivables")
                .header("idempotency", idempotency)
                .bodyValue(receivables)
                .retrieve()
                .bodyToMono<ReceivableScheduleResponse>()
                .block()
        } catch (e: Exception) {
            log.warn(e.message)
            null
        }
    }
}
