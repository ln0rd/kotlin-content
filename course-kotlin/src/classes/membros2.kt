package classes

fun main() {

    val calc = Calculadora()
    calc.somar(1,2,3).multi(3).printThis()
    calc.limpar()
    calc.somar(7,3).printThis()

}

class Calculadora{

    private var resultado: Int = 0

    fun somar(vararg valores: Int): Calculadora {

        valores.forEach {
            resultado += it
        }
        return this

    }

    fun multi(vararg valores: Int): Calculadora {

        valores.forEach {
            resultado *= it
        }
        return this

    }

    fun limpar(): Calculadora {
        resultado = 0
        return this
    }

    fun printThis(): Calculadora {
        println(resultado)
        return this
    }

    fun obterResultado(): Int {
        return resultado
    }

}