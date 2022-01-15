package fundamentos.Operadores

fun main(args: Array<String>) {

    val nota: Double = 7.0
    val resultado: String = if(nota >= 7) "Aprovado" else "Reprovado"
    println(resultado)

    println( obterResultado(8.0) )

}

fun obterResultado( nota: Double ): String = if (nota >= 7) "Passou" else "Não Passou"