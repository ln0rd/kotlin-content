package fundamentos

fun main(args: Array<String>) {

    var a: Int? = null // a ? se chama safe call operator

    /**
     * se fosse no java ao lidar com uma variavel
     * nula ele daria o problema de null exception
     * mas no kotlin o safe call operator impede
     * que isso ocorra
     **/

    println( a?.dec() )

}