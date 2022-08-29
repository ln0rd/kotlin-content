package br.com.hash.clearing.infrastructure.logger

import br.com.hash.clearing.domain.logger.Log
import br.com.hash.clearing.domain.logger.Logger
import kotlin.reflect.KClass
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SLF4JLogger : Logger {
    override fun withContext(context: KClass<*>): Log {
        val logger = LoggerFactory.getLogger(context.simpleName)
        return SLF4JLog(logger)
    }
}
