package fundamentos

fun main(args: Array<String>) {

    /**
     * Nessa situação quando o opcional for nulo ele irá
     * pegar a string "Valor Padrão" devido o uso do
     * operador elvis, que caso não tenha o valor definido ou seja
     * é nulo, ele pegara a outra opção.
     **/

    val opcional: String? = null
//    val opcional: String? = "Ola"
    val obrigatorio: String = opcional ?: "Valor Padrão"


    println(obrigatorio)

}