package classes

fun main() {

    val p1 = Pessoa1("Leo")
    val p2 = Pessoa2("Todoroki")
    val p3 = Pessoa3("Hawnk")

    println(p1.nome)
    println(p2.nome)
    println(p3.nome)

    p1.nome = "Ereaser Head"

    println(p1.nome)

    /**
     * Não conseguimos acessar nomeInicial
     * pois ele é uma variavel visivel só dentro
     * do metodo
     *
     * println(p3.nomeInicial)
     *
     **/
    
}

class Pessoa1(var nome: String) {

}

class Pessoa2(val nome: String) {

}

class Pessoa3(nomeInicial: String) {
    val nome: String = nomeInicial
}