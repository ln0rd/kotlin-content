package fundamentos

fun main() {
    val list: List<Int> = listOf<Int>(1,2,3,4,5,6,7,8,9,10)

    val for2 = list.map { it * 2 }
    println( for2 )
}