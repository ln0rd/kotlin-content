package funcoes

fun main() {

    println(calc(2,3, Operacoes()::somar))
    println(calc(5,3, ::sub))

}

class Operacoes{

    fun somar(a: Int, b: Int): Int {
        return a + b
    }

}

fun sub(a: Int, b: Int): Int = a - b

fun calc(a: Int, b: Int, funcao: (Int, Int) -> Int ): Int {
    return funcao(a,b)
}