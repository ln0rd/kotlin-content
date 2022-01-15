package lambdas

fun main() {

    val alunos = arrayListOf(
        Aluno("Leo", 8.4),
        Aluno("Han", 10.0),
        Aluno("Laura", 7.6),
        Aluno("Chester", 6.0),
        Aluno("Ally", 9.0))

    val aprovados = alunos.filter { it.nota >= 7.0 }.sortedBy { it.nome }

    println(aprovados)

}

data class Aluno(val nome: String, val nota: Double)