package collections

fun main() {

    val conjunto = hashSetOf(
        Objeto("Espada", "Osso de Dragão"), // Hashcode 6
        Objeto("Cajado", "Madeira da Arvore Mãe"), // Hashcode 6
        Objeto("Casaco", "Tecido de Asas de Fadas Mortas"), // Hashcode 6
        Objeto("Anel", "Feito com Ferro de Anão Mágico") // Hashcode 4
    )

    conjunto.contains(Objeto("Cajado", "Madeira da Arvore Mãe")).print()

}

class Objeto(val nome: String, val descicao: String){

    override fun hashCode(): Int {
        return nome.length
    }

    override fun equals(other: Any?): Boolean {
        if (other is Objeto) {
            return nome.equals(other.nome, ignoreCase = true)
        } else {
            return false
        }
    }
}