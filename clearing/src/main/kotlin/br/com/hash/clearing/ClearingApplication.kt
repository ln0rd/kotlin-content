package br.com.hash.clearing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClearingApplication

fun main(args: Array<String>) {
    runApplication<ClearingApplication>(*args)
}
