package y2022.day3

import resourceReader

data class ItemType(val type: Char) {
    val priority = if (type.isLowerCase()) {
        type.code - 'a'.code + 1
    } else {
        type.code - 'A'.code + 27
    }
}

data class Rucksack(val items: List<ItemType>) {
    private val compartment1 = items.take(items.size / 2)
    private val compartment2 = items.takeLast(items.size / 2)

    val itemTypeInBothCompartments = compartment1.intersect(compartment2.toSet()).first()
}

fun <T> intersect(vararg sets: Set<T>): Set<T> {
    return sets.reduce { acc, set -> acc.intersect(set) }
}

fun main() {
    val reader = "2022/3/input.txt".resourceReader()

    val rucksacks = mutableListOf<Rucksack>()
    reader.forEachLine { line ->
        rucksacks.add(Rucksack(line.map { ItemType(it) }))
    }
    println("Priority sum: ${rucksacks.sumOf { it.itemTypeInBothCompartments.priority }}")

    // part2

    val groups = rucksacks.windowed(size = 3, step = 3)
    val badges = mutableListOf<ItemType>()
    groups.forEach {
        val badge = intersect(*it.map { rucksack -> rucksack.items.toSet() }.toTypedArray()).first()
        badges.add(badge)
    }
    println("Badge priority sum: ${badges.sumOf { it.priority }}")
}
