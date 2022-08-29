package br.com.hash.clearing.infrastructure.monitoring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MonitoringRoutes {

    @GetMapping("/healthz")
    fun healthz() = MonitoringResponse("OK")

    @GetMapping("/readiness")
    fun readiness() = MonitoringResponse("OK")
}
