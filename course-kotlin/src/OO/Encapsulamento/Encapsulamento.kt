package OO.Encapsulamento

fun main() {

    Capsulafilha().verificarAcesso()
    VerificarAcesso()

}

/**
 * Somente dentro do metodo
 * ou um metodo so é visivel dentro da classe
 **/

private val dentroDoArquivo = 1

/**
 * Sera visivel somente dentro da classe e de
 * outras classes que extendem dela
 **/

// protected val protegidoNaoSuportadoAqui = 1

/**
 * Quando gerar um jar do projeto ele n estara disponivel
 * fora dele, seja propriedade ou metodo
 **/

internal val dentroDoProjeto = 1

/**
 * Visivel publicamente, para todos
 **/

val publico = 1

/**
 * Para permitir que herdam/Extendam da classe
 * precisa por o open
 */

open class Capsula () {
    private val dentroDoObjeto = 2
    protected val vaiPorHeranca = 3
    internal val dentroDoProjeto = 1
    val puclico = 4

    private fun dentroDoOjeto() = 1
    protected fun vaiPorHeranca() = 0
    internal fun dentroDoProjeto() = 0
    fun publico() = "Ola"

}

class Capsulafilha: Capsula() {
    fun verificarAcesso() {
//        println( Capsula().dentroDoObjeto ) // tipo private n consigo acessar
        println( vaiPorHeranca ) // tipo protect a transmitiu por heranca
        println( dentroDoProjeto ) // tipo internal transmitiu por heranca
        println( puclico ) // tbm veio por heranca

    }
}

fun VerificarAcesso() {
    println( Capsula().dentroDoProjeto ) // n aparece nada pq é privado
    println( Capsula().puclico ) // aparece pq é publico
//    println( Capsula().vaiPorHeranca ) // n consigo acessar pq n herdei a classe
}