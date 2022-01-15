package funcoes

fun main() {
    println(potencia(10))
    println(potencia(20, 2))
    println(potencia(exponte = 2))
    println(potencia(exponte = 2, base = 10))
}


fun potencia(base: Int = 2, exponte: Int = 1): Int {
    return Math.pow(base.toDouble(),exponte.toDouble()).toInt();
}