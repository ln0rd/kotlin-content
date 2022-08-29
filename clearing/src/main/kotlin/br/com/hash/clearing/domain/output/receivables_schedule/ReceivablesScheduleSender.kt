package br.com.hash.clearing.domain.output.receivables_schedule

import br.com.hash.clearing.domain.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ReceivablesScheduleSender(
    private val receivablesSchedulerGateway: ReceivablesScheduleGateway,
    private val processableReceivablesRepository: ProcessableReceivablesRepository,
    logger: Logger
) {
    private val log = logger.withContext(ReceivablesScheduleSender::class)

    fun send() {
        val processableReceivables: List<ProcessableReceivables> = processableReceivablesRepository.findNotSent()

        if (processableReceivables.isEmpty()) {
            log.warn("No processable receivables found to send")
            return
        }

        val processableReceivablesSentList = sendReceivables(processableReceivables)

        if (processableReceivablesSentList.isEmpty()) {
            log.warn("No Processable Receivables were accepted by Receivables Schedule")
            return
        }

        processableReceivablesRepository.save(createProcessableReceivablesWithSentStatus(processableReceivablesSentList))
    }

    private fun sendReceivables(processableReceivables: List<ProcessableReceivables>): List<ProcessableReceivables> {
        val processableReceivablesSentList: MutableList<ProcessableReceivables> = mutableListOf()
        runBlocking {
            processableReceivables.map {
                processableReceivable ->
                async {
                    val receivablesSent = receivablesSchedulerGateway.send(
                        processableReceivable.receivables,
                        processableReceivable.id!!,
                    )
                    if (receivablesSent !== null) processableReceivablesSentList.add(processableReceivable)
                }
            }
        }

        return processableReceivablesSentList
    }

    private fun createProcessableReceivablesWithSentStatus(processableReceivables: List<ProcessableReceivables>): List<ProcessableReceivables> {
        return processableReceivables.map { processableReceivable ->
            ProcessableReceivables(
                transactionId = processableReceivable.transactionId,
                pricingDetails = processableReceivable.pricingDetails,
                transactionData = processableReceivable.transactionData,
                status = ProcessableReceivablesStatus.SENT,
                receivables = processableReceivable.receivables
            )
        }
    }
}
