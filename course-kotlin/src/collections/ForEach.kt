package collections

fun main() {

    val childrens = arrayListOf<String>("Norman", "Ray", "Emma", "Phill", "Anna")

    /**
     * forEach Lambda
     **/

    childrens.forEach { println("Fugitivo: [$it]") }

    /**
     * forEach Function
     **/

    childrens.forEach() {
        println(it)
    }


}