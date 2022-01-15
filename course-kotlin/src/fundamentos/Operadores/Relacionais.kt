package fundamentos.Operadores

import java.util.*
import javax.xml.crypto.Data

fun main(args: Array<String>) {

    println("Banana" === "Banana")
    println("Banana" == "Banana")
    println(1 == 2)
    println(1 === 2)
    println(1 === 2)

    val d1 = Date(0)
    val d2 = Date(0)
    val d3 = d2

    /**
     * Tres iguais pergunta se é igual na referencia
     * Dois iguais compara o conteudo
     **/

    // igualdade referencial
    println("Resultado com \"===\" ${d1 === d2}")
    println("Resultado com \"==\" ${d1 == d2}")
    println("Resultado com \"===\" ${d3 === d2}")

}