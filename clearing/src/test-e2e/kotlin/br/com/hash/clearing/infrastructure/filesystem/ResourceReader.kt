package br.com.hash.clearing.infrastructure.filesystem

fun resourceAsText(resource: String): String {
    return ClassLoader.getSystemResource(resource).readText()
}

fun resourceAsBytes(resource: String): ByteArray {
    return ClassLoader.getSystemResource(resource).readBytes()
}
