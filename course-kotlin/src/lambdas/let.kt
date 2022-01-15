package lambdas

fun main() {

    val listWhitNulls: List<String?> = listOf("A",null,"B",null,"C")

    for (item in listWhitNulls) {
        item?.let {
            println(it)
        }
    }

}