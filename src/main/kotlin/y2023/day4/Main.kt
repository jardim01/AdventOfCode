package y2023.day4

import utils.resourceReader
import y2023.day4.domain.Card

private const val PART_2 = true

fun main() {
    val reader = "2023/4/input.txt".resourceReader()
    val cards = reader.lineSequence().map(Card::fromString).associateBy { it.number }
    if (!PART_2) {
        val totalPoints = cards.values.sumOf { it.countPoints() }
        println("Total points: $totalPoints")
    } else {
        // holds the number of each card and copies
        val cardNumbers = cards.keys.toMutableList()
        var idx = 0
        while (true) {
            val cardNumber = cardNumbers.getOrNull(idx++) ?: break
            val card = cards[cardNumber]!!
            repeat(card.countMatchingNumbers()) {
                val copyCardNumber = card.number + it + 1
                cardNumbers.add(copyCardNumber)
            }
        }
        val totalCards = cardNumbers.size
        println("Total cards: $totalCards")
    }
}
