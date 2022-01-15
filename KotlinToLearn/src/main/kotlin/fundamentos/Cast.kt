package fundamentos

fun main() {
    imprimirConceito(2)
    imprimirConceito(2.0)
    imprimirConceito(-2)
}

fun imprimirConceito(nota: Any) {
    //interrogação diz que é opcional o cast e se der erro o valor sera null
    //o elvis operator garante que se for null o valor padrão será zero
    when(nota as? Int ?: 0) {
        10,9 -> println("A")
        8,7 -> println("B")
        6,5 -> println("C")
        4,3 -> println("D")
        2,1,0 -> println("Reprovado")
        else -> println("Invalido")
    }
}