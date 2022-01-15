package fundamentos.operadores

import java.util.*

fun main() {

    println("leo" == "leo")
    println("leo" !== "leo")
    println("leo" == "leo123")
    println(3 > 2)
    println(3 < 2)
    println(3 <= 2)
    println(3 >= 2)


    val d1 = Date()
    val d2  = Date()

    println(d1 === d2)
    println(d1 == d2)

}