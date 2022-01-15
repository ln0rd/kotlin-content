package funcoes

import java.util.*

data class Horario(val horario: Int, val minuto: Int, val segundo: Int) {

    fun mostrarHorario() {
        println("$horario:$minuto:$segundo")
    }

}


fun agora(): Horario {
    val agora = Calendar.getInstance()

    // se não utilizar o with teria que referenciar o agora.get(Calendar.HOUR)
    with(agora) {
        return Horario(get(Calendar.HOUR), get(Calendar.MINUTE), get(Calendar.SECOND))
    }
}

fun main() {
    agora().mostrarHorario()
    val (hora, minuto, segundo) = agora()
    println("$hora:$minuto:$segundo")
}