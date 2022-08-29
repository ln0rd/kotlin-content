package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.model.PricingDetails
import br.com.hash.clearing.domain.model.TransactionData

interface PricingEngineGateway {
    fun calculate(transactionDataList: List<TransactionData>): List<PricingDetails>
}
