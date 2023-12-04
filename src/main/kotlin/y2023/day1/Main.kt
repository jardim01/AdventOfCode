package y2023.day1

import utils.resourceReader
import y2023.day1.domain.WordDigits

fun main() {
    part1()
    part2()
}

fun part1() {
    val reader = "2023/1/input.txt".resourceReader()
    var sum = 0
    reader.forEachLine { line ->
        val first = line.first { it.isDigit() }.digitToInt()
        val last = line.last { it.isDigit() }.digitToInt()
        sum += combineDigits(first, last)
    }
    println("Sum: $sum")
}

fun part2() {
    val reader = "2023/1/input.txt".resourceReader()
    var sum = 0
    reader.forEachLine { line ->
        var first: Int? = null
        var subStr = line
        while (first == null) {
            val c = subStr.first().digitToIntOrNull()
            if (c != null) {
                first = c
            } else {
                WordDigits.values().forEach {
                    if (subStr.startsWith(it.word)) first = it.digit
                }
            }
            subStr = subStr.drop(1)
        }

        var last: Int? = null
        subStr = line
        while (last == null) {
            val c = subStr.last().digitToIntOrNull()
            if (c != null) {
                last = c
            } else {
                WordDigits.values().forEach {
                    if (subStr.endsWith(it.word)) last = it.digit
                }
            }
            subStr = subStr.dropLast(1)
        }

        sum += combineDigits(first!!, last!!)
    }
    println("Sum: $sum")
}

private fun combineDigits(first: Int, last: Int): Int {
    return first * 10 + last
}
