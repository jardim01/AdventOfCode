package y2022.day3.domain

data class Rucksack(val items: List<ItemType>) {
    private val compartment1 = items.take(items.size / 2)
    private val compartment2 = items.takeLast(items.size / 2)

    val itemTypeInBothCompartments = compartment1.intersect(compartment2.toSet()).first()
}
