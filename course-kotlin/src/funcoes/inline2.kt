package funcoes

fun main() {

    val resultado = executarComLog("somar2") {
        somar7(4,5)
    }

    println(resultado)

}

inline fun <Type> executarComLog(nomeFuncao: String, funcao: () -> Type): Type {

    println("Entrando no método $nomeFuncao.")
    try {
        return funcao()
    } finally {
        println("Método $nomeFuncao finalizado.")
    }
}

fun somar7(a: Int, b: Int): Int {
    return  a + b
}
