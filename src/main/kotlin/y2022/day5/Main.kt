package y2022.day5

import resourceReader

data class Box(val letter: Char)

data class Move(val amount: Int, val from: Int, val to: Int)

private const val NUMBER_OF_STACKS = 9

private const val PART_1 = false

fun <T> MutableList<T>.removeFirst(n: Int): List<T> {
    val elements = this.take(n)
    repeat(n) { this.removeFirst() }
    return elements
}

fun main() {
    val reader = "2022/5/input.txt".resourceReader()

    val columns: List<MutableList<Box>> = listOf(
        *(0..NUMBER_OF_STACKS).map { mutableListOf<Box>() }.toTypedArray()
    )

    while (true) {
        val line = reader.readLine()
        if (!line.contains("[")) break

        line.windowed(size = 4, step = 4, partialWindows = true)
            .forEachIndexed { i, s ->
                val boxLetter = s.trim().getOrNull(1) ?: return@forEachIndexed
                val box = Box(boxLetter)
                columns[i].add(box)
            }
    }

    println(
        columns.joinToString("\n") { boxes ->
            boxes.joinToString("") { it.letter.toString() }
        }
    )

    val moves = mutableListOf<Move>()

    // starts at last position
    reader.forEachLine { line ->
        val match = Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(line) ?: return@forEachLine
        val amount = match.groups[1]?.value?.toInt() ?: throw IndexOutOfBoundsException()
        val from = match.groups[2]?.value?.toInt() ?: throw IndexOutOfBoundsException()
        val to = match.groups[3]?.value?.toInt() ?: throw IndexOutOfBoundsException()

        moves.add(Move(amount, from - 1, to - 1))
    }

    if (PART_1) {
        // part1
        moves.forEach { move ->
            repeat(move.amount) {
                val box = columns[move.from].removeFirst()
                columns[move.to].add(index = 0, element = box)
            }
        }
    } else {
        // part2
        moves.forEach { move ->
            val boxes = columns[move.from].removeFirst(move.amount)
            columns[move.to].addAll(index = 0, elements = boxes)
        }
    }
    println(columns.joinToString("") { it.firstOrNull()?.letter?.toString() ?: "" })
}