package classes

class Data(var dia: Int, var mes: Int, var ano:Int) {

    fun format(): String {
            return "$dia/$mes/$ano"
    }

}


fun main() {

    val result = Data(dia = 2, mes = 12, ano = 2020)

    println(result.format())


    with(result) { println("$dia/$mes/$ano") }
}