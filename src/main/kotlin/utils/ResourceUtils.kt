package utils

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URL

fun getResource(name: String): URL? = object {}.javaClass.getResource(name)

fun resourceBufferedReader(name: String): BufferedReader {
    val file = getResource(name)?.file ?: throw FileNotFoundException()
    val fis = FileInputStream(file)
    return BufferedReader(fis.reader())
}

fun String.resourceReader() = resourceBufferedReader(this)
