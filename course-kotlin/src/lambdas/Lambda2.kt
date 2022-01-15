package lambdas

fun main() {

    val calc = Calculadora()
    val result = calc.calcular(3,4)
    val result2 = calc.calcular(3,4, Multiplicacao())

    val subtracao = { a: Int, b: Int -> a - b }
    val result3 = calc.calcular(8,5, subtracao)

    println("${result} ${result2} ${result3}")

}

interface Operacao {
    fun executar(a: Int, b: Int): Int
}

class Multiplicacao: Operacao {
    override fun executar(a: Int, b: Int): Int {
        return a * b
    }
}

class Calculadora {

    fun calcular(a: Int, b: Int): Int {
        return a + b
    }

    fun calcular(a: Int, b: Int, operacao: Operacao): Int {
        return operacao.executar(a, b)
    }

    fun calcular(a: Int, b: Int, operacao: (Int, Int) -> Int): Int {
        return operacao(a,b)
    }

}