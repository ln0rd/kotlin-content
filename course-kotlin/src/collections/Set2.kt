package collections

fun main() {

    val aprovados = hashSetOf("Deku", "Bakugou", "Todoroki", "Eraser Head")

    println("Sem ordem ... ")
    for (aprovado in aprovados) {
        aprovado.print()
    }

    val aprovadosMissao1 = linkedSetOf("Deku", "Bakugou", "Todoroki", "Eraser Head")
    println("Em ordem ... ")
    for (aprovado in aprovadosMissao1) {
        aprovado.print()
    }

    val AprovadosNatural = sortedSetOf("Deku", "Bakugou", "Todoroki", "Eraser Head")
    println("Em Ordem Alfabetica")
    for (aprovado in AprovadosNatural) {
        aprovado.print()
    }
}