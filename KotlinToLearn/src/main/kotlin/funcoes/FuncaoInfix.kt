package funcoes

class Produto(val nome: String, val preco: Double)

infix fun Produto.maisCaroque(produto: Produto): Boolean = this.preco > produto.preco


fun main() {
    val p1 = Produto(nome = "ipad", preco = 5000.20)
    val p2 = Produto(nome = "macbook", preco = 6000.76)

    println(p1.maisCaroque(p2))
    println(p2 maisCaroque p1)
}