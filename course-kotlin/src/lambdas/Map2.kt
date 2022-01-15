package lambdas

fun main() {
    val totalizar = { total: Double, atual: Double -> total + atual }

    var precoTotal = materialEscolar.map { it.preco }.reduce(totalizar)

    println("O preço medio é R$ ${precoTotal / materialEscolar.size}")

}

class Produto(val nome: String, val preco: Double)

val materialEscolar = listOf(
    Produto("Fichario Escolar", 21.90),
    Produto("Caderno", 12.50),
    Produto("Caneta", 2.75),
    Produto("Borracha", 4.00),
    Produto("Lapis", 7.40),
    Produto("Mochila", 130.60)
)
