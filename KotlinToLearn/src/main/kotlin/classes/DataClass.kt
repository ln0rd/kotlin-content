package classes

class Geladeira(val marca: String, val litros: Int) {

}
data class Televisao(val marca: String, val polegadas: Int)


fun main() {

    val geladeira = Geladeira(marca ="LG", litros = 320)
    val geladeira2 = Geladeira(marca ="LG", litros = 320)

    val televisao = Televisao(marca = "Samsung", polegadas = 60)
    val televisao2 = Televisao(marca = "Samsung", polegadas = 60)


    println(geladeira == geladeira2)

    println(televisao == televisao2)

    // Destructuring
    val (marca, polegada) = televisao
    print(" $marca & $polegada ")
}