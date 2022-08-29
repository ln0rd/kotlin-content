package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.model.TransactionData
import br.com.hash.clearing.domain.transaction.TransactionServiceGateway
import br.com.hash.clearing.infrastructure.http.DefaultWebClient
import java.nio.charset.Charset
import java.util.logging.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class HttpTransactionServiceGateway(transactionServiceConfiguration: TransactionServiceConfiguration) : TransactionServiceGateway {

    private val logger: Logger = Logger.getLogger(HttpTransactionServiceGateway::class.simpleName)

    private val webClient = DefaultWebClient.builder()
        .defaultHeaders { header ->
            header.setBasicAuth(
                transactionServiceConfiguration.username,
                transactionServiceConfiguration.password,
                Charset.forName("UTF-8")
            )
        }
        .build()

    private val url = transactionServiceConfiguration.url

    override fun findByIds(ids: List<String>): List<TransactionData> {
        val transactionDataList = runBlocking {
            ids.map { id ->
                async {
                    fetchTransactionData(id)
                }
            }.awaitAll()
        }

        return transactionDataList.filterNotNull()
    }

    private suspend fun fetchTransactionData(id: String): TransactionData? {
        return webClient
            .get()
            .uri("$url/transaction-events/$id")
            .retrieve()
            .bodyToMono(TransactionData::class.java)
            .toFuture()
            .exceptionally { e ->
                logger.warning(e.message)
                null
            }.await()
    }
}
