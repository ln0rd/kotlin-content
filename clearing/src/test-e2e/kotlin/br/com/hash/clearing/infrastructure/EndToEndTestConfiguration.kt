package br.com.hash.clearing.infrastructure

import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.NoCredentialsProvider
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.gcp.core.GcpProjectIdProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootConfiguration
@ComponentScan(
    excludeFilters = [
        ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [EnableScheduling::class]),
    ],
    basePackages = ["br.com.hash.clearing.infrastructure"]
)
@EnableAutoConfiguration
class EndToEndTestConfiguration {

    @Bean
    fun noCredentialsProvider(): CredentialsProvider {
        return NoCredentialsProvider.create()
    }

    @Bean
    fun gcpProjectIdProvider(): GcpProjectIdProvider {
        return GcpProjectIdProvider { "hash-project-id" }
    }
}
