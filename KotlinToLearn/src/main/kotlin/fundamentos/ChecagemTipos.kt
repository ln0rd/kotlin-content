package fundamentos

fun main() {

    val valor = "abc"
    val valor2: String = "leo"
    val inteiro1: Int = 1

    // Is
    println(valor is String)
    println(inteiro1 is Int)
    // Is not
    println(valor2 !is String)

}