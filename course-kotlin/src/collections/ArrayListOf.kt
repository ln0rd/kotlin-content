package collections

fun main() {

    /**
     * Função TopLevel retorna um array com os parametros passados
     * de acordo com os tipos passados
     **/

    val stringsAndNumbers = arrayListOf("Harry", 2 ,"Sirius", 99 ,"Regulus", 2 ,"Snape", "Dumbledore")
    val strignsNumbersBooleans = arrayListOf("Newt", true, false, "Voldemort")
    val stringsIntDoubleBooleans = arrayListOf("Hermione", true, 4.50, "Porpentina", 4)
    val strings = arrayListOf("leo", "Norman", "Emma", "Ray")

    /**
     * Caso percorra um array alem de Strings, não será possivel
     * usar funções como toUpperCase
     **/

    for( item in strings ) {
        println( item.toUpperCase() )
    }

    /**
     * Caso percorra um array com strings e tente multiplicar
     * irá dar erro
     **/

    for( item in stringsAndNumbers ) {
//        println( item * 2 )
    }

}