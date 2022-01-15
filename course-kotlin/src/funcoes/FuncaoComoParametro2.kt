package funcoes

fun main() {

    val nomes = listOf("Bakugou", "Midorya", "Lida", "Todorokis", "Leo", "Sao")
    println(filtrar(nomes, ::comTresLetras))

}

fun <E>filtrar(lista: List<E>, filtro: (E) -> Boolean): List<E> {

    val listaFiltrada = ArrayList<E>()

    for (e in lista) {
        if (filtro(e)){
            listaFiltrada.add(e)
        }
    }
    return listaFiltrada
}

fun comTresLetras(nome: String): Boolean {
    return nome.length === 3
}