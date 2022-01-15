package funcoes

fun main() {

    for ( n in ordenar(3,4,5,10,65,78,102,902,43,56,1,5, a = 1) ) {
        print("[$n] ")
    }

}

fun ordenar( vararg numeros: Int, a: Int): IntArray {
    return numeros.sortedArray()
}