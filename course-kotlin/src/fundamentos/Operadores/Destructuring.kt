package fundamentos.Operadores

fun main(args: Array<String>) {
    var carro1 = Carro("ford", "Caminhonete")
    var person1 = Personagem("Midorya")

    println(carro1.marca)
    println(person1.nome)

    val (marca, modelo) = Carro( "Tesla", "S" )

    println("Marca ${marca} Modelo ${modelo}")

    val (pessoa1, pessoa2) = listOf("Midorya", "Bakugou")

    println("Pessoa1: $pessoa1, Pessoa2: $pessoa2")
}

data class Carro( val marca: String, val modelo: String )

class Personagem( val nome: String )

