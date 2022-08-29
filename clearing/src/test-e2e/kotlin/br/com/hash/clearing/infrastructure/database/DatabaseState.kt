package br.com.hash.clearing.infrastructure.database

import org.flywaydb.core.Flyway

object DatabaseState {

    private val flyway = Flyway
        .configure()
        .dataSource("jdbc:postgresql://localhost/clearing", "username", "password")
        .load()

    fun reset() {
        flyway.clean()
        flyway.migrate()
    }
}
