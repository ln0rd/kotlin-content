package br.com.hash.clearing.domain.transaction

import br.com.hash.clearing.domain.event.Event
import br.com.hash.clearing.domain.model.TransactionProcessResult

class TransactionProcessedEvent(val transactionProcessResults: List<TransactionProcessResult>) : Event
