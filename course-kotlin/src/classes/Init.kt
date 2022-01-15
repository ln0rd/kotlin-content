package classes

fun main() {

    val filme = Filme3("Os Incriveis", 2004, "Animação/Ação/Familia").printThis()

}

class Filme3(nome: String, anoLancamento: Int, genero: String) {

    val nome: String
    val anoLancamento: Int
    val genero: String

    init {

        this.nome = nome
        this.anoLancamento = anoLancamento
        this.genero= genero

    }

    fun printThis(): Filme3 {
        println("O filme $nome lançado em $anoLancamento, generos: [$genero]")
        return this
    }

}