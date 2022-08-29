package br.com.hash.clearing.infrastructure.input

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class InputJobs(private val pubSubTransactionQueueReader: PubSubTransactionQueueReader) {

    @Scheduled(fixedDelayString = "#{\${clearing.jobs.transactionReader}}")
    fun transactionReaderJob() {
        pubSubTransactionQueueReader.pull()
    }
}
