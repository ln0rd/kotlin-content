package classes

fun main() {

    ItemDePedido.desconto = .05
    ItemDePedido.create("Playstation 4", 2300.00).printThis()
    ItemDePedido("Mac Pro", 8000.00).printThis()

}

class ItemDePedido(val nome: String, var preco: Double) {

    companion object {
        fun create(nome: String, preco: Double) = ItemDePedido(nome, preco)
        @JvmStatic var desconto: Double = 0.0
    }

    fun precoComDesconto(): Double = preco - preco * desconto

    fun printThis(): ItemDePedido {
        println("Objeto criado: Item: [$nome] valor: [$preco] com desconto ${preco - desconto}" )
        return this
    }

}