package y2022.day4

import resourceReader

private fun CharSequence.toRange(): IntRange {
    val parts = this.split("-")
    val start = parts[0].toInt()
    val end = parts[1].toInt()
    return start..end
}

fun IntRange.contains(other: IntRange) = other.first >= this.first && other.last <= this.last

fun IntRange.overlaps(other: IntRange) =
    this.first in other || this.last in other || this.contains(other) || other.contains(this)

fun main() {
    val reader = "2022/4/input.txt".resourceReader()

    val ranges = mutableListOf<Pair<IntRange, IntRange>>()
    reader.forEachLine { line ->
        val parts = line.split(",")

        val elf1Range = parts[0].toRange()
        val elf2Range = parts[1].toRange()

        ranges.add(Pair(elf1Range, elf2Range))
    }
    val count1 = ranges.count { pair -> pair.first.contains(pair.second) }
    println("Count: $count1")

    // part2

    val count2 = ranges.count { pair -> pair.first.overlaps(pair.second) }
    println("Count: $count2")
}