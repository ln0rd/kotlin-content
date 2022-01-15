package funcoes

fun main() {

    relacaoDeTrabalho("Charlie", "Leo")
    relacaoDeTrabalho(funcionario = "Leo", chefe = "Charlie")

}

fun relacaoDeTrabalho(chefe: String, funcionario: String): String {
    println("$funcionario é subordinado(a) à $chefe")
    return "$funcionario é subordinado(a) à $chefe"
}