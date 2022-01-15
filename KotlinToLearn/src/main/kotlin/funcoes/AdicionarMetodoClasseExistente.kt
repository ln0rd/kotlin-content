package funcoes

fun main() {

    val myList: List<String> = listOf("Leo", "Charlie", "Bia")
    val myList2: List<Int> = listOf(1, 2, 5)

    println(myList.secondOrNull())
    println(myList.secondOrNullOnlyStrings())
    println(myList2.secondOrNull())

}

fun <E> List<E>.secondOrNull(): E? = if(this.size >= 2) this.get(1) else null
fun <String> List<String>.secondOrNullOnlyStrings(): String? = if(this.size >= 2) this.get(1) else null