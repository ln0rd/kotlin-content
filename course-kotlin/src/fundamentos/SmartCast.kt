package fundamentos

fun main(args: Array<String>) {

    souEsperto("leo")
    souEsperto2(2)

}

fun souEsperto(x: Any) {
    if (x is String){
        println(x.toUpperCase())
    } else if (x is Int) {
        println(x.plus(3))
    } else if( x is Boolean) {
        println(x)
    }
}

fun souEsperto2(x: Any){
    when(x) {
        is String -> println(x.toUpperCase())
        is Int -> println(x.plus(3))
        is Boolean -> print(x)
    }
}