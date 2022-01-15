package funcoes

fun main() {

    val list = listOf("Leo", "Bakugou", "Lida")
    println(list.secondOrNull())

}

fun <E>List<E>.secondOrNull(): E? = if (this.size >= 2) this[1] else null