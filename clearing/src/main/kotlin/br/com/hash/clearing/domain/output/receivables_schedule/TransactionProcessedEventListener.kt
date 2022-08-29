package br.com.hash.clearing.domain.output.receivables_schedule

import br.com.hash.clearing.domain.event.EventListener
import br.com.hash.clearing.domain.logger.Logger
import br.com.hash.clearing.domain.model.TransactionProcessResult
import br.com.hash.clearing.domain.transaction.TransactionProcessedEvent
import java.time.ZoneId

class TransactionProcessedEventListener(
    private val processableReceivablesRepository: ProcessableReceivablesRepository,
    logger: Logger
) : EventListener<TransactionProcessedEvent> {

    private val log = logger.withContext(TransactionProcessedEventListener::class)

    override fun handleEvent(event: TransactionProcessedEvent) {
        val transactionProcessResults: List<TransactionProcessResult> = event.transactionProcessResults
        processableReceivablesRepository.save(createProcessableReceivables(transactionProcessResults))
    }

    private fun createProcessableReceivables(transactionProcessResults: List<TransactionProcessResult>): List<ProcessableReceivables> {
        return transactionProcessResults.map { transactionProcessResult ->
            ProcessableReceivables(
                pricingDetails = transactionProcessResult.pricingDetails,
                transactionData = transactionProcessResult.transactionData,
                transactionId = transactionProcessResult.transactionData.id,
                status = ProcessableReceivablesStatus.WAITING_TO_SEND,
                receivables = createReceivables(transactionProcessResult)
            )
        }
    }

    private fun createReceivables(transactionProcessResult: TransactionProcessResult): List<Receivable> {
        val isoReceivables = generateReceivables(transactionProcessResult, "iso")
        val merchantReceivables = generateReceivables(transactionProcessResult, "merchant")

        return mergeReceivablesList(isoReceivables, merchantReceivables)
    }

    private fun generateReceivables(transactionProcessResult: TransactionProcessResult, holder: String): List<Receivable> {
        return transactionProcessResult.pricingDetails.splitDetail.map { splitDetail ->
            Receivable(
                amount = if (holder == "iso") splitDetail.isoRevenueAmount else splitDetail.splitAmount,
                isoId = if (holder == "iso") transactionProcessResult.transactionData.isoID else null,
                merchantId = if (holder == "iso") null else splitDetail.merchantId,
                holder = holder,
                installment = splitDetail.installmentNumber,
                originAt = transactionProcessResult.transactionData.dateTime.atZoneSameInstant(ZoneId.of("America/Sao_Paulo")).toLocalDate(),
                paymentNetwork = transactionProcessResult.transactionData.paymentNetworkData.alphaCode,
                settlementDate = BaseSettlementDate.defineBaseSettlementDate(
                    transactionDateTime = transactionProcessResult.transactionData.dateTime,
                    installmentNumber = splitDetail.installmentNumber,
                    accountType = transactionProcessResult.transactionData.accountType
                ),
                transactionId = transactionProcessResult.transactionData.id,
            ).withTwoDecimalPlaces()
        }
    }

    private fun hasAmountZero(receivable: Receivable): Boolean {
        if (receivable.amount == 0) {
            log.warn("receivable-has-zero-amount $receivable")
            return true
        }
        return false
    }

    private fun mergeReceivablesList(isoReceivables: List<Receivable>, merchantReceivables: List<Receivable>): List<Receivable> {
        val isoReceivablesNonZeroAmount: List<Receivable> = isoReceivables.filter { receivable: Receivable -> !hasAmountZero(receivable) }
        val merchantReceivablesNonZeroAmount: List<Receivable> = merchantReceivables.filter { receivable: Receivable -> !hasAmountZero(receivable) }

        return isoReceivablesNonZeroAmount + merchantReceivablesNonZeroAmount
    }
}
