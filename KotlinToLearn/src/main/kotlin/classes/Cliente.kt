package classes

class Cliente {

    var nome: String = ""

}


fun main() {
    val cliente = Cliente()

    cliente.nome = "Leo"

    println(cliente.nome)
}