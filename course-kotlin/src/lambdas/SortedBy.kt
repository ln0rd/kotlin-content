package lambdas

fun main() {

    val nomes = arrayListOf("Midorya", "Bakugou", "Lida", "Todoroki", "All Might")
    val ordenados = nomes.sortedBy { it.reversed() } // Ordena por ordem alfabetica mas das ultimas letras

    println( ordenados )

}

