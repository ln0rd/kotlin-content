package classes

fun main() {

    val filme = Filme2("Resident Evil", 2002, "Terror/Ação/Suspense").printThis()

}

class Filme2( val nome: String, val anoLancamento: Int, val genero: String) {

    fun printThis(): Filme2 {
        println("$nome foi lançado em $anoLancamento, generos [$genero]")
        return this
    }

}
