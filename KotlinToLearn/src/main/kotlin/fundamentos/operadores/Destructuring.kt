package fundamentos.operadores

fun main() {
    // marca e modelo recebe o valores da instancia
    val (marca, modelo) = Carro("Tesla", "Tesla Model 3")
    println(marca + " | " + modelo)

    // pega direto do array
    val (nome1, nome2) = listOf<String>("Leo", "Alice", "Alucard")
    println(nome1 + " | " + nome2)

    // pega somente o terceiro item
    val (_, _, terceiro) = listOf<String>("Leo", "Alice", "Alucard")
    println(terceiro)
}

data class Carro(val marca: String, val modelo: String)
