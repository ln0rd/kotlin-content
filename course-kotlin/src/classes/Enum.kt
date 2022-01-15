package classes

fun main() {

    println("O melhor dia da semana é ${DiaSemana.SEXTA} de tardezinha")

    println("O dia topster é o ${DiaSemana2.SABADO.nome} é dia util? [${DiaSemana2.SABADO.util}]")

}

enum class DiaSemana {
    DOMINGO, SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO
}

enum class DiaSemana2(val id: Int, val nome: String, val util: Boolean) {
    DOMINGO(1, "Domingo", false),
    SEGUNDA(2, "Segunda", true),
    TERCA(3, "Terça", true),
    QUARTA(4, "Quarta", true),
    QUINTA(5, "Quinta", true),
    SEXTA(6, "Sexta", true),
    SABADO(7, "Sabado", false),

}