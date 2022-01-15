package classes

fun main() {

    val carro1 = Carro("Ford", "Fusion")
    var carro2 = carro1
    println(" Velocidade inicial ${carro1.velocidade}")

    /**
     * Aumentei a velocidade de carro2 e subiu a de
     * carro1 devido o fato de apontar para mesma
     * referencia de memoria
     **/

    AumentarVelocidade(carro2)

    println(" Velocidade Atual ${carro1.velocidade}")

}

/**
 * Erro kotlin val cannot be reassigned
 * não pode reatribuir pois parametro sem var
 * é uma constante
 **/

fun porReferencia(velocidade: Int) {
/**    velocidade++      */
}

data class Carro(var marca: String, var modelo: String, var velocidade: Int = 0)

fun AumentarVelocidade(carro: Carro) {

    carro.velocidade++

}