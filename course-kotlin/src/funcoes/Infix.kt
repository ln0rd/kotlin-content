package funcoes

fun main() {

    val p1 = Produto("Ipad", 2390.00)
    val p2 = Produto("Mac pro", 8200.00)

    println( p1.maisCaroQue(p2) )
    println( p2 maisCaroQue p1 )
}

class Produto(val nome: String, val preco: Double)

infix fun Produto.maisCaroQue(produto: Produto): Boolean = this.preco > produto.preco