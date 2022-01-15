package collections

fun main() {

    val alunos = arrayListOf<String>("Harry", "Snape", "Hagrid", "Dumbledore", "Minerva", "Roney", "Hermione")

    for (aluno in alunos) {
        println("Aluno [$aluno]")
    }

    for ((indice, aluno) in alunos.withIndex()) {

        println("Indice: $indice Aluno: $aluno")

    }

}