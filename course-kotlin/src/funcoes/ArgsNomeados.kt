package funcoes

fun main() {

    println( relacaoDeTrabalho( "All Might", "Midorya") )
    println( relacaoDeTrabalho( funcionario = "Big Three", chefe = "U.A" ) )

}

fun relacaoDeTrabalho(chefe: String, funcionario: String): String {
    return "$funcionario é subordinado(a) à $chefe"
}