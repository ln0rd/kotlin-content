package com.leonardobufalo.tour.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["tipo", "ddd"])
data class Telefone (
        val ddd: String = "",
        val numero: String = "",
        val tipo: String = "")