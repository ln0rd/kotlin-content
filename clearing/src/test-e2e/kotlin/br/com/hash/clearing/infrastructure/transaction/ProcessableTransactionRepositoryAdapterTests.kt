package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus.PREPROCESSED
import br.com.hash.clearing.domain.transaction.ProcessableTransactionStatus.RECEIVED
import br.com.hash.clearing.infrastructure.EndToEndTestConfiguration
import br.com.hash.clearing.infrastructure.database.DatabaseState
import br.com.hash.clearing.infrastructure.filesystem.resourceAsText
import io.kotest.core.spec.style.DescribeSpec
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(classes = [EndToEndTestConfiguration::class])
class ProcessableTransactionRepositoryAdapterTests(
    private val processableTransactionRepositoryAdapter: ProcessableTransactionRepositoryAdapter,
    private val jdbcTemplate: JdbcTemplate
) : DescribeSpec() {
    init {
        describe("stale records retrieval tests") {
            DatabaseState.reset()

            saveProcessableTransaction("1", RECEIVED)
            saveProcessableTransaction("1", PREPROCESSED)
            saveProcessableTransaction("2", RECEIVED)
            saveProcessableTransaction("3", RECEIVED, "200")
            saveProcessableTransaction("3", PREPROCESSED, "200")
            saveProcessableTransaction("4", RECEIVED, "300", OffsetDateTime.now().minusHours(3))
            saveProcessableTransaction("4", PREPROCESSED, "300", OffsetDateTime.now().minusHours(3))
            saveProcessableTransaction("5", RECEIVED, "300", OffsetDateTime.now().minusHours(3))
            saveProcessableTransaction("6", RECEIVED, "300")

            it("should find by status RECEIVED with current owner or stale created_at") {
                val processableTransactions = processableTransactionRepositoryAdapter.findUnprocessed()
                assertEquals(2, processableTransactions.size)
                assertEquals("2", processableTransactions[0].transactionId)
                assertEquals(RECEIVED, processableTransactions[0].status)
                assertEquals("5", processableTransactions[1].transactionId)
                assertEquals(RECEIVED, processableTransactions[1].status)
            }

            it("should find by status PREPROCESSED with current owner or stale created_at") {
                val processableTransactions = processableTransactionRepositoryAdapter.findReadyToProcess()
                assertEquals(2, processableTransactions.size)
                assertEquals("1", processableTransactions[0].transactionId)
                assertEquals(PREPROCESSED, processableTransactions[0].status)
                assertEquals("4", processableTransactions[1].transactionId)
                assertEquals(PREPROCESSED, processableTransactions[1].status)
            }
        }
    }

    private fun saveProcessableTransaction(
        transactionId: String,
        status: ProcessableTransactionStatus,
        owner: String = Instance.INSTANCE_ID,
        createdAt: OffsetDateTime = OffsetDateTime.now()
    ) {
        val transactionData = resourceAsText("transaction-service/transaction-events-by-id-response.json")
        val sql = """
                insert into processable_transactions (
                   transaction_data,
                   transaction_id,
                   status,
                   owner,
                   created_at
                ) values (
                    '$transactionData',
                    '$transactionId',
                    '$status',
                    '$owner',
                    '$createdAt'
                )
                """
        jdbcTemplate.update(sql)
    }
}
