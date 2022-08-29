package br.com.hash.clearing.infrastructure.output.meta_accounting

import br.com.hash.clearing.domain.output.meta_accounting.MetaAccountingSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MetaAccountingJobs(private val metaAccountingSender: MetaAccountingSender) {

    @Scheduled(fixedDelayString = "#{\${clearing.jobs.metaAccountingSender}}")
    fun metaAccountingSenderJob() {
        metaAccountingSender.send()
    }
}
