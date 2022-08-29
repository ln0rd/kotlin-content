package br.com.hash.clearing.domain.input

import br.com.hash.clearing.domain.event.Event
import br.com.hash.clearing.domain.model.TransactionData

class TransactionReadEvent(val transactionDataList: List<TransactionData>) : Event
