package classes


fun main() {

    /**
     * Acessa a propriedade do Objeto que é definido pela
     * classe, apos instanciar passa pra variavel as mesmas propriedades,
     * definidas dentro da classe.
     **/

    val cliente = Cliente()
    cliente.nome = "leo"

    val OutroCliente = Cliente()
    OutroCliente.nome = "Bakugou"

    println("Nome do cliente é [${cliente.nome}] e nome do OutroCliente é [${OutroCliente.nome}]")

}

class Cliente {

    var nome: String = ""

}
