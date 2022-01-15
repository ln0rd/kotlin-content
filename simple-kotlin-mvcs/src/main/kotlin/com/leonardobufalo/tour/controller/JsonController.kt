package com.leonardobufalo.tour.controller

import com.leonardobufalo.tour.model.Cliente
import com.leonardobufalo.tour.model.SimpleObject
import com.leonardobufalo.tour.model.Telefone
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

@RestController
class JsonController {

    @GetMapping(value = ["/json"])
    fun getJson() = SimpleObject()

    @GetMapping(value = ["/cliente"])
    fun getCliente(): Cliente {
        val telefone = Telefone(ddd = "11", numero = "928090910", tipo = "Celular")
        val cliente = Cliente(
                id = 1,
                nome = "Hiro",
                dataNascimento = SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date()),
                telefone = telefone)

        return cliente
    }
}