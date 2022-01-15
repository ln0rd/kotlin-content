package fundamentos.operadores

fun obterResultado(nota: Double) : String = if(nota >= 7.0) "Aprovado" else "Reprovado"

fun main() {

    val nota: Double = 7.0
    val resultado = obterResultado(nota)

    println(resultado)

}