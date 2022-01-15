package classes

class Pessoa1(var nome: String)
class Pessoa2(val nome: String)

// nesse caso nomeInicial só é acessivel no construtor.
// assim não podemos acessar o nomeInicial dps de construir a classe.
class Pessoa3(nomeInicial: String) {
    val nome: String = nomeInicial
}


fun main() {
    val p1 = Pessoa1(nome = "leo")
    p1.nome = "leo 2"

    val p2 = Pessoa2(nome = "Charlie")
    //não consigo eatr
//    p2.nome = "Charlie 2"

    val p3 = Pessoa3(nomeInicial = "Leo")
    p3.nome
}