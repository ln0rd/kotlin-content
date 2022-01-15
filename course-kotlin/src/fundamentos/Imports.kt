package fundamentos

import fundamentos.pacoteA.simplesfuncao as funcaoSimples
import fundamentos.pacoteA.Coisa
import fundamentos.pacoteA.FaceMoeda.CARA
import fundamentos.pacoteB.*

fun main(args: Array<String>) {

    /**
     * Por padrão tudo o que está dentro de kotlin.io
     * já está disponivel para acessar sem chamar kotlin.io
     **/

    kotlin.io.println(funcaoSimples("executando funcao com novo nome"))

    /**
     * a Classe Coisa foi foi instanciada
     * sem precisar da palavra New
     **/

    val coisa = Coisa("Lança")
    println(coisa.nome)

    println(CARA)

    /**
     * Podemos chamar as funcoes que estao dentro do
     * pacoteB que importamos tudo
     **/

    println("Soma: ${soma(2,2)}; Subtracao: ${subtracao(10,6)}  ")
}