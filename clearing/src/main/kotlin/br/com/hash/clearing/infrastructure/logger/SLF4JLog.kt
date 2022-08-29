package br.com.hash.clearing.infrastructure.logger

import br.com.hash.clearing.domain.logger.Log
import org.slf4j.Logger

class SLF4JLog(private val logger: Logger) : Log {
    override fun debug(message: String?) {
        if (logger.isDebugEnabled) {
            logger.debug(message)
        }
    }

    override fun info(message: String?) {
        if (logger.isInfoEnabled) {
            logger.info(message)
        }
    }

    override fun warn(message: String?) {
        if (logger.isWarnEnabled) {
            logger.warn(message)
        }
    }

    override fun error(message: String?) {
        if (logger.isErrorEnabled) {
            logger.error(message)
        }
    }
}
