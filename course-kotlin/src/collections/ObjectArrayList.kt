package collections

fun main() {

    var frutas = arrayListOf(
        Fruta("Banana", 3.45),
        Fruta("Melão", 5.45),
        Fruta("Maçã", 2.30),
        Fruta("Limão", 6.45)
    )

    /**
     * Percorrendo o array de objetos imprimindo as propriedades
     **/

    for (fruta in frutas) {
        println(" Fruta [${fruta.nome}] Preço [${fruta.preco}]")
    }

    /**
     * Verifica se existe esse objeto dentro do Array.
     * Tem que passar o Objeto inteiro
     **/

    println(frutas.contains(Fruta("Banana", 3.45)))

    /**
     * Preços diferentes.
     * Retorna os objetos com preços distintos
     **/

    println(frutas.distinctBy { it.preco })

    /**
     * Adicionar um novo Objeto no Array*/

    frutas.add(Fruta("Abacaxi", 8.0))
    println(frutas.last())

}

data class Fruta(var nome: String, var preco: Double)