package y2023.day4.domain

import utils.pow

data class Card(val number: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
    companion object {
        private fun extractNumbers(text: String): List<Int> {
            return text
                .split(" ")
                .filter { it.isNotBlank() }
                .map(String::toInt)
        }

        fun fromString(text: String): Card {
            val number = Regex("Card +(\\d+):").find(text)!!.groupValues[1].toInt()
            val numbersStr = text.substringAfter(": ")
            val parts = numbersStr.split("|")
            val winningNumbers = extractNumbers(parts[0]).toSet()
            val numbers = extractNumbers(parts[1]).toSet()
            return Card(
                number = number,
                winningNumbers = winningNumbers,
                numbers = numbers,
            )
        }
    }

    fun countMatchingNumbers(): Int {
        val matching = winningNumbers.intersect(numbers)
        return matching.size
    }

    fun countPoints(): Int {
        val matchingCount = countMatchingNumbers()
        return 2.pow(matchingCount - 1)
    }
}
