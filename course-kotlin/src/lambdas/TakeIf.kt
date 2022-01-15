package lambdas

fun main() {

    println("Digite sua msg: ")
    val entrada = readLine()

    val mensagem = entrada.takeIf { it != "" } ?: "Sem mensagem"

    println(mensagem)

}

