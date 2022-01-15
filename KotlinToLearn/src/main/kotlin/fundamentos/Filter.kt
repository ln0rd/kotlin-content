package fundamentos

fun main() {

    val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
    // Retorna maiores ou igual a 5
    val biggerThanFive = list.filter { it -> it >= 5 }
    val pair = list.filter { IsPair(it) }

    println(biggerThanFive)
    println(pair)
}

fun IsPair(value: Int) : Boolean {
    return (value % 2 == 0)
}