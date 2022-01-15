package fundamentos

fun main(args: Array<String>) {

    val a: Int = 1

    /**
     * Converter numbero para string
     **/

    // em vez de imprimir 2 irá imprimir 11
    println(a.toString() + 1)

    /**
     * Converter string para double
     **/

    // resultado sera 4.9 pois convertei 1.9 em double
    println("1.9".toDouble() + 3)

    /**
     * Converter string para int e se não der
     * usa o elvis operator para passar outro
     * valor padrao
     **/

    println("Teste".toIntOrNull() ?: 0)


}