package funcoes

fun ordenar(vararg numeros: Int): IntArray{
    return numeros.sortedArray()
}

fun main() {
    val result: IntArray =  ordenar(2,3,4,1,9,0,12,66,34,89,12,5,102,90)

    for (n in result) {
        println(n)
    }

}