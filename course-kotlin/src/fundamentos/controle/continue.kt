package fundamentos.controle

fun main(args: Array<String>) {

    for (i in 1..10) {
        if (i == 5){
            println("Entrou na condicao")
            continue
        }
        println("Atual: $i")
    }

}