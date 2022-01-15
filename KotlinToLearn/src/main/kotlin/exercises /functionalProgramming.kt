package exercises

import kotlin.math.pow
import kotlin.math.sign

fun main() {

    val nameList: List<String> = listOf("Leo", "Bia", "Bianca", "Bartolomeu", "Romeu", "Charlie", "Alice")
    val namesInitialB: List<String> = nameList.filter { verifyInitialWithB(it) }
    println(namesInitialB)

    val numberList: List<Int> = listOf(1,2,3,4,5,6,7,8,9,10)
    val numberOdd: List<Int> = numberList.filter { justOdds(it) }
    println(numberOdd)

    val numberDoubleList: List<Double> = listOf(1.2,2.0,3.2,4.5,50.2,65.2,70.0,89.2,91.1,100.0)
    val numbersCubicSquare: List<Double> = numberDoubleList.map { SquareRoot(it) }
    println(numbersCubicSquare)

    val valuesToAvarage: List<Int> = listOf(8,3,5,7,8,9,6,7,6,7,5,8,7,10,4)
    val avarageClassRoom: Int = valuesToAvarage.reduce { acc, it -> acc + it }
    println(avarageClassRoom / valuesToAvarage.size)
}


fun verifyInitialWithB(value: String): Boolean {
    return (value.substring(0,1) == "b" || value.substring(0,1) == "B")
}

fun justOdds(value: Int): Boolean {
    return (value % 2 != 0)
}

fun SquareRoot(value: Double): Double {
    return value.toDouble().pow(1/3.toDouble())
}