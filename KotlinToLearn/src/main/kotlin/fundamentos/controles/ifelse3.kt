package fundamentos.controles

fun main() {
    val nota: Double = 8.3

    // usando operador range
    if (nota in 9..10) {
        println("Fantastico")
    } else if (nota in 7..8) {
        println("Parabens")
    } else if (nota in 4..6) {
        println("Tem como recuperar")
    } else if (nota in 0..3) {
        println("Não ligue para esse sistema idiota")
    }

}