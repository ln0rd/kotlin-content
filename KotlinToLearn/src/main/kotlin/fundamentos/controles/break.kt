package fundamentos.controles

fun main() {

    for (i in 1..10) {
        if (i == 5) {
            break
        }
        println("Atual: ${i}")
    }
    println("Final")

    // quando a condição abaixo terminar ele termina o laço externo
    loop@for (i in 1..5) {
        for (j in 1..15) {
            if (i == 2 && j == 2) break@loop
            println("i [${i} j [${j}]]")
        }
    }
    println("Fim")

}