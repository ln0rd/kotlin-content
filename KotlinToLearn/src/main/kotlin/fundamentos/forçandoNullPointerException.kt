package fundamentos

fun main() {

    var a: Int? = null
    // Null safety
    println(a?.inc())

    println("Momento do erro...")
    // assume o risco de estar recebendo um nullPointerException
    println(a!!.inc())
}