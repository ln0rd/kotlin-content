package collections

fun main() {

    val map = HashMap<Long, String>()

    map.put(10020030040, "Severo Snape")
    map.put(30040050060, "Sirius Black")

    /**
     * Substitui
     **/

    map.put(20, "R.J Lupin")
    map.put(20, "Harry Potter")

    map.print()

    for (par in map) {
        println(par)
    }

    for (nome in map.values) {
        println(nome)
    }
    for (keys in map.keys) {
        println(keys)
    }

}