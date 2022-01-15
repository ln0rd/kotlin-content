package OO.Polimorfismo

/**
 * Polimorfismo dinamico
 **/

class Feijao2( val peso: Double )
class Arroz2( val peso: Double )

class Pessoa2( var peso: Double ) {

    fun comer(feijão: Feijao2) {
        peso += feijão.peso
    }

    fun comer(arroz: Arroz2) {
        peso += arroz.peso
    }

    override fun toString(): String {
        return "O peso da pessoa atualmente é $peso Kg"
    }

}

fun main() {

    val feijao = Feijao2(0.3)
    val arroz = Arroz2(0.7)

    val pessoa1 = Pessoa2(78.0)

    println(pessoa1)
    pessoa1.comer(arroz)
    println(pessoa1)
    pessoa1.comer(feijao)
    println(pessoa1)

}