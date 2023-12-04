package y2022.day2

import utils.resourceReader
import y2022.day2.domain.Gesture
import y2022.day2.domain.Round
import y2022.day2.domain.RoundResult

fun main() {
    part1()
    part2()
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
