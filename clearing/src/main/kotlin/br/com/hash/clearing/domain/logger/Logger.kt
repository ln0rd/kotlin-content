package br.com.hash.clearing.domain.logger

import kotlin.reflect.KClass

interface Logger {
    fun withContext(context: KClass<*>): Log
}
