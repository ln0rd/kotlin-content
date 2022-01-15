package OO.Polimorfismo

/**
 * Polimorfismo Dinamico
 **/

open class Comida( val peso: Double )
class Feijao( peso: Double ) : Comida(peso)
class Arroz( peso: Double ) : Comida(peso)
class Ovo( peso: Double ) : Comida(peso)

class Pessoa( var peso: Double ) {

    fun comer(comida: Comida) {
        peso += comida.peso
    }

    override fun toString(): String {
        return "O peso da pessoa atualmente é $peso Kg"
    }

}

fun main() {

    val feijao = Feijao(0.3)
    val arroz = Arroz(0.7)
    val ovo = Ovo(200.00)

    val pessoa1 = Pessoa(78.0)

    println(pessoa1)
    pessoa1.comer(arroz)
    println(pessoa1)
    pessoa1.comer(feijao)
    println(pessoa1)

}