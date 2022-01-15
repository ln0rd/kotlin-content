package com.leonardobufalo.tour.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Cliente(
        @JsonProperty(value = "Matricula") val id: Long,
        val nome: String, val dataNascimento: String,
        val telefone: Telefone?)
