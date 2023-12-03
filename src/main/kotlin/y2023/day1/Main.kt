package y2023.day1

import resourceReader

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

enum class WordDigits(val word: String, val digit: Int) {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
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

fun combineDigits(first: Int, last: Int): Int {
    return first * 10 + last
}
