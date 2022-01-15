package lambdas

fun main() {

    val alunos = arrayListOf("Darth Vader", "Sega", "All for one", "Voldemort")

    /**
     * Percorre cada item e o map retorna um novo array alterado
     * sortedBy ordena ordem alfabetica, e o apply e oq esta sera feito apois terminar
     * o sortedBy.
     **/

    alunos.map { it.toUpperCase() }.sortedBy { it }.apply { println(this) }

    alunos.map { println(it) }
    
}