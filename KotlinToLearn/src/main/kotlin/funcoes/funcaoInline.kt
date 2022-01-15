package funcoes

fun main() {

    transacao {
        println("Executando a primeira funcao")
        println("Executando a segunda funcao")
        println("Executando a terceira funcao")
    }

}

inline fun transacao( funcao: () -> Unit ) {
    println("abrindo transacao...")

    try {
        funcao()
    } finally {
        println("com ou sem erro fechando a transacao")
    }
}