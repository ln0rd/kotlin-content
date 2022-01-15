package fundamentos

fun main(args: Array<String>) {

    /**
     * Tipos Basicos Inteiros.
     * o "_" usando nos numeros são como os "." e
     * não causa interferencias.
     **/

    val num1: Byte = 127 //valor só vai até 127
    val num2: Short = 32_767
    val num3: Int = 2_147_483_647 // Int.MAX_VALUE
    val num4: Long = 9_223_372_036_854_775_807 // Long.MAX_VALUE

    /**
     * Tipos Basicos Flutuantes
     **/

    val num5: Float = 3.14F // Mesmo tamanho do Int
    val num6: Double = 3.14

    /**
     * Tipo Caracter
     **/

    val char: Char = '?' // Outros exemplos: '1', 'leo', ' '

    /**
     * Tipo Boolean
     **/

    val boolean: Boolean = true // ou false


    //listOf retorna dentro de um array
    println(listOf(num1, num2, num3, num4, num5, num6, char, boolean))

    /**
     * Testar os Tipos
     **/

    println(2 is Int)
    println(2423545343 is Long)
    println(2.4 is Double)
    println(false is Boolean)

    /**
     * Tudo é um objeto
     **/

    println(10.dec())

}