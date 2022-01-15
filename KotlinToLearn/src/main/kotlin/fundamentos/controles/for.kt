package fundamentos.controles

fun main() {

    exemplo_um_For()
    exemplo_dois_for()
    exemplo_tres_for()
    exemplo_quatro_for()

}

fun exemplo_um_For() {

    for (i in 1..10) {
        println("exemplo 1 [${i}]")
    }

}

fun exemplo_dois_for() {

    for (i in 10 downTo 1) {
        println("exemplo 2 [${i}]")
    }

}

fun exemplo_tres_for() {

    for (i in 0..100 step 5) {
        println("exemplo 3 [${i}]")
    }

}

fun exemplo_quatro_for() {

    val nomes = listOf<String>("Leo", "Alice", "Bia")

    for ( (indice, item) in nomes.withIndex()) {
        println("Indice [${indice}] Nome [${item}]")
    }

}