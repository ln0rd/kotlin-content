package br.com.hash.clearing.infrastructure.output.receivables_schedule

import br.com.hash.clearing.domain.output.receivables_schedule.ProcessableReceivablesStatus
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "processable_receivables")
data class ProcessableReceivablesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null,

    @Column(name = "transaction_data")
    val transactionData: String,

    @Column(name = "pricing_details")
    val pricingDetails: String,

    @Column(name = "transaction_id")
    val transactionId: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: ProcessableReceivablesStatus,

    @Column(name = "receivables")
    val receivables: String,

    @Column(name = "created_at", updatable = false, insertable = false)
    val createdAt: OffsetDateTime? = null,

    @Column(name = "owner")
    val owner: String
)
