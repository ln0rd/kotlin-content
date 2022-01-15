package funcoes

fun main() {

    println("o menor valor é ${min(2,4)}")

}

fun min(a: Int, b: Int): Int {
    return if (a < b) a else b
}