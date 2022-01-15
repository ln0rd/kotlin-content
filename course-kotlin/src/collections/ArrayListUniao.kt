package collections

fun main() {

    val numeros = arrayListOf(1, 2, 3, 4, 5)

    val strings = arrayListOf("Snape", "Sirius", "Harry", "Voldemort", "Dumbledore", "Olho Torto", "Roney")

    /**
     * Sobrecarga de Operadores
     **/

    val uniao = numeros + strings

    println(uniao)

    for (item in uniao) {
        println("Item -> [$item]")
    }

}