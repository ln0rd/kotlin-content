package classes

fun main() {
    val filme = Filme("Anjos da Noite", 2003, "Ação/Drama/Suspense/Fantasia").PrintThis()
}

class Filme {

    constructor(nome: String, anoLancamento: Int, genero: String){
        this.nome = nome
        this.anoLancamento = anoLancamento
        this.genero = genero
    }

    val nome: String
    val anoLancamento: Int
    val genero: String

    fun PrintThis(): Filme {
        println("$nome foi lançado em $anoLancamento, generos: [$genero]")
        return this
    }

}