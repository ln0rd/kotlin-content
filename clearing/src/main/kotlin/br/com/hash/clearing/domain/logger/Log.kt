package br.com.hash.clearing.domain.logger

interface Log {
    fun debug(message: String?)
    fun info(message: String?)
    fun warn(message: String?)
    fun error(message: String?)
}
