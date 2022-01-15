package collections

fun main() {

    /**
     * intArrayOf impoem ao array um tipo, permitindo
     * somente itens do tipo int no array
     **/

    val pares = arrayListOf(2, 4, 6)
    val impares = intArrayOf(1, 3, 5)

    /**
     * Funcao union permite unir arrays do mesmo tipo
     * em seguida usamos sorted() para ordenar a sequencia
     **/

    for (n in impares.union(pares).sorted()) {
        println( n )
    }

}