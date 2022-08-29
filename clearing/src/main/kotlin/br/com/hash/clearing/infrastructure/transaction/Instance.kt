package br.com.hash.clearing.infrastructure.transaction

import java.util.UUID

object Instance {
    val INSTANCE_ID: String = UUID.randomUUID().toString()
}
