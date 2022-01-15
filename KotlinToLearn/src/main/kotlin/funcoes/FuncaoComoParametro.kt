package funcoes

fun main() {

    println(calc(2,3, ::somar))
    println(calc(2,3, Operacoes()::soma))

}

class Operacoes {

    fun soma(a: Int, b: Int): Int {
        return a+b
    }

}

fun somar(a: Int, b: Int): Int {
    return a+b
}


fun calc(a: Int, b: Int, funcao: (Int, Int) -> Int): Int {
    return funcao(a, b)
}