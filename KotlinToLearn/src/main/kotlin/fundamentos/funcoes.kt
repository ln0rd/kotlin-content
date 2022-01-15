package fundamentos

fun imprimirSoma(a: Int, b: Int): Int {
    println(a+b)
    return a+b
}

fun imprimir(a: String) {
    println("Mensagem: [$a]")
}


fun imprimir2() {
    println("funcao sem entrada nem retorno")
}

fun valorDefault(a: Int, b: Int = 5): Int {
    println(a+b)
    return a+b
}

fun umaLinha(c: Int, d: Double): Double = c + d

fun main() {
    imprimirSoma(2,2)
    imprimir("Vish")
    imprimir2()
    valorDefault(1)
    println(umaLinha(1,3.0))
}