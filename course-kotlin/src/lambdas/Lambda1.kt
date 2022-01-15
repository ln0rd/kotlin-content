package lambdas

/**
 * Dentro do Lambda a ultima linha
 * é o return implicito
 **/

fun main() {
    val soma = { x: Int, y: Int -> x + y }
    println(soma(4,6))
}