package fundamentos

fun main() {

    val values: List<Int> = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    val sumValues: Int = values.reduce {acc, value -> acc + value}
    println(sumValues / values.size)

}