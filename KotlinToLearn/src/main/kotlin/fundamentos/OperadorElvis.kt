package fundamentos

fun main() {
    // elvis, caso for nulo ele pega o valor padrão
    val opcional: String? = null
    val obrigatoria: String = opcional ?: "Valor Padrão"

    println(obrigatoria)
}
