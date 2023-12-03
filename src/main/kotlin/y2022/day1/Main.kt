package y2022.day1

import resourceReader

data class Elf(val calories: Int)

fun main() {
    val reader = "2022/1/input.txt".resourceReader()

    var elf = Elf(calories = 0)
    val elfs = mutableListOf<Elf>()
    reader.forEachLine {
        if (it.isBlank()) {
            elfs.add(elf)
            elf = Elf(calories = 0)
            return@forEachLine
        }
        elf = Elf(calories = elf.calories + it.toInt())
    }
    elfs.sortBy { it.calories }
    elfs.reverse()
    println(elfs.take(3).sumOf { it.calories })
}