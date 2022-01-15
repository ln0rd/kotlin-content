package classes

fun main() {
    topLevel()
    Coisa().fazer()
    println(Coisa.constanteDeClasse)
}

val diretamenteNoArquivo = "Bom dia"

fun topLevel() {
    val local = "Fulano!"
    println("$diretamenteNoArquivo $local")
}

class Coisa {

    var variaveldeInstancia = "Teste"

    companion object {
        @JvmStatic val constanteDeClasse = "Ciclano"
    }

    fun fazer() {
        val local: Int = 7

        if (local > 3) {
            val variavelDeBloco = "Beltrano"
            println("$variaveldeInstancia, $constanteDeClasse, $local, e $variavelDeBloco")
        }

    }

}

