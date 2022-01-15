package fundamentos

fun main() {

    //tipos numericos inteiros
    val num1: Byte = 127
    val num_1: Byte = -127
    val num2: Short = 32767
    val num_2: Short = -32767
    val num3: Int = 2147483647
    val num_3: Int = -2147483647 // Int.MAX_VALUE
    val num4: Long = 9223372036854775807
    val num_4: Long = -9223372036854775807 // Long.MAX_VALUE

    //tipos numericos reais
    val num5: Float = 3.14F
    val num6: Double = 3.14

    // Tipo Carctere
    val char: Char = '?' // Somente um caracter

    //Tipo Boolean
    val boolean: Boolean = true // false

    println(listOf(num1, num2, num3, num4, num5, num6))
    println(2 is Int)

    println(10.dec())

}