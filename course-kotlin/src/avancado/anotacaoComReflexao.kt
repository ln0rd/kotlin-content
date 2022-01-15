package avancado

import collections.Objeto

fun main() {

    val obj1 = Pessoa(1, "Leo")
    val obj2 = Pessoa( -2, "")

    println(validar(obj1))
    println(validar(obj2))
}

annotation class Positivo
annotation class NaoVazio

class Pessoa(id: Int, nome: String) {

    @Positivo
    var id: Int = id

    @NaoVazio
    var nome: String = nome

}

// reflection...
fun getValor(objeto: Any, nomeDoAtributo: String): Any {

    val atributo = objeto.javaClass.getDeclaredField(nomeDoAtributo)
    val estaAcessivel = atributo.isAccessible

    atributo.isAccessible  = true
    val valor = atributo.get(objeto)
    atributo.isAccessible = estaAcessivel

    return valor
}

fun validar(objeto: Any): List<String> {
    val msgs = ArrayList<String>()
    objeto::class.members.forEach { member ->
        member.annotations.forEach{ annotation ->
            val valor = getValor(objeto, member.name)
            when (annotation) {
                is Positivo ->
                    if (valor !is Int || valor <= 0) {
                        msgs.add("O valor $valor não é um numero positivo")
                    }
                is NaoVazio ->
                    if (valor !is String || valor.trim().isEmpty()) {
                        msgs.add("O valor $valor não é uma String valida")
                    }
            }
        }
    }
    return msgs
}