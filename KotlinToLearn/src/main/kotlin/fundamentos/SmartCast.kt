package fundamentos

fun main() {

    val valorInt: Int = 2
    val valorString: String = "Charlie"

    souEsperto(valorInt)
    souEsperto(valorString)

    souEspero2(valorInt)
    souEspero2(valorString)
    souEspero2(true)

}
//  recebe qualquer coisa, e reconhece qual o tipo da variavel trazendo
//  o smartcast é o fato de fazer a verificação e ele já reconhecer os metodos que compoem
//  um int ou string
fun souEsperto(x: Any) {

    if (x is String) {
        println(x.toUpperCase())
    }

    if (x is Int) {
        println(x.plus(2))
    }

}


fun souEspero2(x: Any) {

    when(x) {
        is String -> println(x.toUpperCase())
        is Int -> println(x.plus(2.0))
        else -> println("Repense sua vida!")
    }

}
