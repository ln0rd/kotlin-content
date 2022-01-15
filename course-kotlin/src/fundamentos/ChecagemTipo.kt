package fundamentos

fun main(args: Array<String>) {

    val valor: Any = "abc"
//    val valor: Any = 123

    println(valor is String)
    println(valor is Int)
    println(valor is Boolean)

    if (valor is String) {
        println("É String")
    } else if (valor !is String) {
        println("Não é uma String")
    }

}