package br.com.hash.clearing.infrastructure.transaction

import br.com.hash.clearing.domain.transaction.TransactionPreProcessor
import br.com.hash.clearing.domain.transaction.TransactionProcessor
import javax.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@Transactional(rollbackOn = [Exception::class])
class TransactionJobs(
    private val transactionPreProcessor: TransactionPreProcessor,
    private val transactionProcessor: TransactionProcessor
) {

    @Scheduled(fixedDelayString = "#{\${clearing.jobs.transactionPreProcessor}}")
    fun transactionPreProcessorJob() {
        transactionPreProcessor.process()
    }

    @Scheduled(fixedDelayString = "#{\${clearing.jobs.transactionProcessor}}")
    fun transactionProcessorJob() {
        transactionProcessor.process()
    }
}
