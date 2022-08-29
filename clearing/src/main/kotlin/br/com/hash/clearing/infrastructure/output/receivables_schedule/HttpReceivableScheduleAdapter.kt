package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.Receivable
import br.com.hash.clearing.infrastructure.output.receivables_schedule.Receivable as ReceivableInfrastructure

object HttpReceivableScheduleAdapter {
    fun createInfrastructureReceivables(receivablesList: List<Receivable>): Receivables {
        val receivablesInfrastructureList = receivablesList.map { receivable ->
            ReceivableInfrastructure(
                amount = receivable.amount,
                holder = receivable.holder,
                installment = receivable.installment,
                isoId = receivable.isoId,
                merchantId = receivable.merchantId,
                originAt = receivable.originAt,
                originFrom = receivable.originFrom,
                paymentNetwork = receivable.paymentNetwork,
                settlementDate = receivable.settlementDate,
                transactionId = receivable.transactionId
            )
        }

        return Receivables(receivablesInfrastructureList)
    }
}
