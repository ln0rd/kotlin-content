package classes

fun main() {

    val c1 = Cliente2("")
    println(c1.nome)

    val c2 = Cliente2("Leo")
    println(c2.nome)

}

class Cliente2 {

    constructor(nome:String){
        this.nome = nome
    }

    var nome: String
        get() = "Meu nome é ${field}"
        set(value) {
            field = value.takeIf { value.isNotEmpty() } ?: "Anônimo"
        }

}