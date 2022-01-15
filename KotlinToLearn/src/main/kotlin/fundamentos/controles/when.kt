package fundamentos.controles

fun main() {
    val nota: Int = 10

    when (nota) {
        10, 9 -> println("entre 10 e 9")
        8, 7 -> println("entre 8 e 7")
    }
}