package funcoes

fun min( a: Int, b: Int ): Int {
    return if( a < b ) a else b
}

fun max( a: Int, b: Int ): Int = if( a > b ) a else b

fun main() {

    println("Menor valor: ${min(2,5)}")
    println("Maior valor: ${max(2,5)}")

}