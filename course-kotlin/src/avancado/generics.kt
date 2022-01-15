package avancado

fun main() {

    val materialEscolar = Caixa("Caneta")

    materialEscolar.adicionar("Borracha")
    materialEscolar.adicionar("Lapis")
    materialEscolar.adicionar("Caderno")

    println(materialEscolar)

}

class Caixa<T>( val objeto: T ) {

    private val objetos = ArrayList<T>()

    init {
        adicionar(objeto)
    }

    fun adicionar(novoObjeto: T){
        objetos.add(novoObjeto)
    }

}