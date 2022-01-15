package funcoes

fun main() {

    println( potencia(3, 2) )
    println( potencia(10) )
    println( potencia( base = 10) )
    println( potencia( expoente = 2 ) )

}

fun potencia( base: Int = 2, expoente: Int = 1): Int {
    return Math.pow( base.toDouble(), expoente.toDouble() ).toInt()
}