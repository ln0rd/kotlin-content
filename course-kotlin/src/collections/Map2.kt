package collections

fun main() {

    val map = hashMapOf(1 to "Harry Potter", 2 to "Sirius Black", 3 to "R. J. Lupin", 4 to "Severus Snape")

    for ((id, nome) in map) {
        println("ID [$id] NOME [$nome]")
    }

}