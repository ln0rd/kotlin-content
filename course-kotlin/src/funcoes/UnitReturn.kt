package funcoes

fun main() {

    imprimirMaior(1,2)
    imprimirMaior2(2,3)
    imprimirMaior3(3,4)
    imprimirMaior4(4,5)
    imprimirMaior5(5,6).run { 2 > 1 }.run { print("Resutaldo = $this") }

}

fun imprimirMaior( a: Int, b: Int ) {
    println( Math.max(a, b) )
}

fun imprimirMaior2( a: Int, b: Int ): Unit {
    println( Math.max(a, b) )
}

fun imprimirMaior3( a: Int, b: Int ): Unit {
    println(Math.max(a,b))
    return
}

fun imprimirMaior4( a: Int, b: Int ): Unit {
    println( Math.max(a, b) )
    return Unit
}

fun imprimirMaior5( a: Int, b: Int ) {
    println( Math.max(a, b) )
    return Unit
}