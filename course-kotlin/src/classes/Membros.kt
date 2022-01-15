package classes

fun main() {
    var nascimento: Data = Data(3, 5, 1996)
    println("${nascimento.formatar()}")
    println("${nascimento}")
    println("${nascimento.dia}/${nascimento.mes}/${nascimento.ano}")
}

class Data(var dia: Int, var mes: Int, var ano: Int) {

    fun formatar(): String {
        return "$dia/$mes/$ano"
    }

}