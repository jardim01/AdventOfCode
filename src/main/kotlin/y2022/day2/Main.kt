package y2022.day2

import resourceReader

enum class RoundResult(val points: Int) {
    WON(6), LOST(0), DRAW(3);

    companion object {
        fun fromStr(str: String): RoundResult {
            return when (str) {
                "X" -> LOST
                "Y" -> DRAW
                "Z" -> WON
                else -> throw IllegalArgumentException(str)
            }
        }
    }
}

enum class Gesture(val points: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun fromStr(str: String): Gesture {
            return when (str) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException(str)
            }
        }
    }

    fun vs(other: Gesture): RoundResult {
        return when (this) {
            ROCK -> when (other) {
                ROCK -> RoundResult.DRAW
                PAPER -> RoundResult.LOST
                SCISSORS -> RoundResult.WON
            }

            PAPER -> when (other) {
                ROCK -> RoundResult.WON
                PAPER -> RoundResult.DRAW
                SCISSORS -> RoundResult.LOST
            }

            SCISSORS -> when (other) {
                ROCK -> RoundResult.LOST
                PAPER -> RoundResult.WON
                SCISSORS -> RoundResult.DRAW
            }
        }
    }
}

data class Round(val g1: Gesture, val g2: Gesture) {
    val p1Result = g1.vs(g2)
    val p2Result = g2.vs(g1)
    val p1Points = p1Result.points + g1.points
    val p2Points = p2Result.points + g2.points
}

fun part1() {
    val reader = "2022/2/input.txt".resourceReader()

    val rounds = mutableListOf<Round>()
    reader.forEachLine { line ->
        val gestures = line.split(" ").map(Gesture::fromStr)
        rounds.add(Round(gestures[0], gestures[1]))
    }
    val totalScore = rounds.sumOf { it.p2Points }
    println("Total Score: $totalScore")
}

fun part2() {
    val reader = "2022/2/input.txt".resourceReader()

    val rounds = mutableListOf<Round>()
    reader.forEachLine { line ->
        val lineParts = line.split(" ")
        val g1 = Gesture.fromStr(lineParts[0])
        val wantedResult = RoundResult.fromStr(lineParts[1])
        val round = Gesture.values().map { g2 -> Round(g1, g2) }.first { it.p2Result == wantedResult }
        rounds.add(round)
    }
    val totalScore = rounds.sumOf { it.p2Points }
    println("Total Score: $totalScore")
}

fun main() {
    part1()
    part2()
}