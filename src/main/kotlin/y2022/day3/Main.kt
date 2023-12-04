package y2022.day3

import utils.intersect
import utils.resourceReader
import y2022.day3.domain.ItemType
import y2022.day3.domain.Rucksack

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
