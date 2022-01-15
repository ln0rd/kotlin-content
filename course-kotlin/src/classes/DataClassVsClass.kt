package classes

fun main() {

    val g1 = Geladeira("Brastemp", 320)
    val g2 = Geladeira("Brastemp", 320)

    println(g1 == g2)

    val tv1 = Televisao("Samsung", 32)
    val tv2 = Televisao("Samsung", 32)
    val tv3 = Televisao("Samsung", 44)

    println(tv1 == tv2)
    println(tv1 == tv3)

    println(tv1.copy(polegadas = 56))

    val (marca, poelgada) = tv1
    println("$marca $poelgada'")

}

class Geladeira(val marca: String, val Litros: Int) {

}

data class Televisao(val marca: String, val polegadas: Int ) {

}