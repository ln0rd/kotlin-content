package collections

fun main() {

    val conjunto = hashSetOf(3, 'a', "texto", true, 3.14)

    conjunto.add(3).print()
    conjunto.add(10).print()

    conjunto.size.print()
    conjunto.remove('a').print()
    
    conjunto.contains('a').print()
    conjunto.contains("texto").print()

    val nums = setOf(1, 2, 3) // somente leitura

    (conjunto + nums).print()
    (conjunto - nums).print()

    conjunto.intersect(nums).print() // não muda o conjunto original, retorna um novo conjunto
    


}

fun Any.print() = println(this)