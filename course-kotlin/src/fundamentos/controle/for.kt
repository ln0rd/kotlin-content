package fundamentos.controle

fun main(args: Array<String>) {

    /**
     * Crescente
     **/

    for (i in 1..10) {
        print("[$i] ")
    }

    println()

    /**
     * Decrescente
     **/

    for (i in 10 downTo 1) {
        print("[$i] ")
    }

    println()

    /**
     * De 10 em 10 Crescente
     **/

    for (i in 0..100 step 10) {
        print("[$i] ")
    }

    println()

    /**
     * De 10 em 10 Decrescente
     **/

    for (i in 100 downTo 0 step 10) {
        print("[$i] ")
    }

    println()
    /**
     * WithIndex passa pelo array trazendo o
     * indice e o valor
     **/

    val alunos = arrayListOf("Leo", "Shikamaru", "Natsu")

    for ( (indice, aluno) in alunos.withIndex() ) {
        println( "Info: [$indice] - [$aluno]" )
    }




}