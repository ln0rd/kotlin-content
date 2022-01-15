package fundamentos.controle

fun main(args: Array<String>) {

    for (i in 1..10) {
        if (i == 5){
            break
        }
        println("Atual: $i")
    }
    println("Fim do primeiro for")

    /**
     * Nessa situação o loop@ e o @loop indicam qual bloco
     * o break fara efeito, nesse exemplo irá parar diretamente
     * o primeiro for.
     **/

    loop@for (i in 1..10){
        for(j in 1..10){
            if(i == 2 && j == 9 ) break@loop
            println("valores i [$i] j [$j]")
        }
    }
    println("Fim do segundo for")

}