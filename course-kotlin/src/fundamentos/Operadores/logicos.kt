package fundamentos.Operadores

fun main(args: Array<String>) {

    val executouTraabalho1: Boolean = true
    val executouTraabalho2: Boolean = true

    val comprouSorvete: Boolean = executouTraabalho1 || executouTraabalho2
    val comprouTv50: Boolean = executouTraabalho1 && executouTraabalho2
    val comprouTv32: Boolean = executouTraabalho1 xor executouTraabalho2


    println(comprouSorvete)
    println(comprouTv50)
    println(comprouTv32)
}