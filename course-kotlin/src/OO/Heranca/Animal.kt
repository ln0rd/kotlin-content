package OO.Heranca

open class Animal(val nome: String)

class Cachorro: Animal {
    private val altura: Double

    constructor(nome: String, altura: Double): super(nome) {
        this.altura = altura
    }

    constructor(nome: String) : this(nome, 0.0)

    override fun toString(): String {
        return "$nome tem $altura cm de altura"
    }
}

fun main() {
    val dogAlemao = Cachorro("Spyke", 80.5)
    val yorkshire = Cachorro("Lady Di")

    println(dogAlemao)
    println(yorkshire)
}