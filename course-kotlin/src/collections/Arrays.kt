package collections

fun main() {

    /**
     * Gero um array com 10 posições
     **/
    val numeros = Array<Int>(10) { i -> + 10}

    for (numero in numeros) {
        println(numero)
    }

    println(numeros[2])
    println(numeros.get(9))
    println(numeros.size)
    println(numeros.set(1, 1234))
    numeros[1] = 1234


}

