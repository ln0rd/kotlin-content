package funcoes

fun main() {

    val resultado = executarComLog("somar") {
        somarSomar(4,5)
    }

    println("Resultado da soma é $resultado")

}

inline fun <T> executarComLog(nomefuncao: String, funcao: () -> T): T {
    println("Entrando no método $nomefuncao...")

    try {
        return funcao()
    } finally {
        println("metodo $nomefuncao finalizando")
    }
}

fun somarSomar(a: Int, b: Int): Int {
    return a+b
}