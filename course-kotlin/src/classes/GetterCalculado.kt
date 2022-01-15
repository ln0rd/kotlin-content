package classes

fun main() {

    var p1 = Produto("Ipad", 2349.90, 0.20, true)
    println(p1.precoComDesconto)

    val p2 = Produto("Mac Pro", 8000.00, .32, true)
    println("${p2.nome}\n\tDe: R$ ${p2.preco} por ${p2.precoComDesconto}")

}

class Produto(var nome: String, var preco: Double, var desconto: Double, var ativo: Boolean) {
    val inativo: Boolean get() = !ativo
    val precoComDesconto: Double get() = preco * (1 - desconto)
}