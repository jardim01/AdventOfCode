package y2023.day2

import resourceReader
import y2023.day2.domain.CubeColor
import y2023.day2.domain.CubeSet
import y2023.day2.domain.Game

private const val N_OF_RED_CUBES = 12
private const val N_OF_GREEN_CUBES = 13
private const val N_OF_BLUE_CUBES = 14

fun main() {
    val reader = "2023/2/input.txt".resourceReader()
    var possibleIdSum = 0
    var setPowerSum = 0
    reader.forEachLine { gameRepr ->
        val game = parseGame(gameRepr)
        if (game.isPossible(N_OF_RED_CUBES, N_OF_GREEN_CUBES, N_OF_BLUE_CUBES)) possibleIdSum += game.id
        val fewestCount = game.fewestCubeCountPossible()
        val setPower = fewestCount.nOfRed * fewestCount.nOfGreen * fewestCount.nOfBlue
        setPowerSum += setPower
    }
    println("Sum of possible game ids: $possibleIdSum")
    println("Set power sum: $setPowerSum")
}

fun Game.isPossible(nOfRed: Int, nOfGreen: Int, nOfBlue: Int): Boolean {
    return sets.all {
        it.nOfRed <= nOfRed && it.nOfGreen <= nOfGreen && it.nOfBlue <= nOfBlue
    }
}

fun Game.fewestCubeCountPossible(): CubeSet {
    val fewestRed = this.sets.maxOf { it.nOfRed }
    val fewestGreen = this.sets.maxOf { it.nOfGreen }
    val fewestBlue = this.sets.maxOf { it.nOfBlue }
    return CubeSet(nOfRed = fewestRed, nOfGreen = fewestGreen, nOfBlue = fewestBlue)
}

/**
 * @param gameRepr the representation of the game. Example:
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 */
fun parseGame(gameRepr: String): Game {
    val id = parseGameId(gameRepr)
    val sets = parseCubeSets(gameRepr)
    return Game(id = id, sets = sets)
}

/**
 * @param gameRepr the representation of the game. Example:
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 */
fun parseGameId(gameRepr: String): Int {
    return Regex("Game (\\d+):").find(gameRepr)!!.groupValues[1].toInt()
}

/**
 * @param gameRepr the representation of the game. Example:
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 */
fun parseCubeSets(gameRepr: String): List<CubeSet> {
    val sets = gameRepr.substringAfter(": ").split("; ")
    return sets.map(::parseCubeSet)
}

/**
 * @param repr the representation of the set. Example:
 * 3 blue, 4 red
 */
fun parseCubeSet(repr: String): CubeSet {
    val parts = repr.split(", ")
    var nOfRed = 0
    var nOfGreen = 0
    var nOfBlue = 0
    parts.forEach {
        val (color, count) = parseCube(it)
        when (color) {
            CubeColor.RED -> nOfRed += count
            CubeColor.GREEN -> nOfGreen += count
            CubeColor.BLUE -> nOfBlue += count
        }
    }
    return CubeSet(nOfRed = nOfRed, nOfGreen = nOfGreen, nOfBlue = nOfBlue)
}

/**
 * @param repr the representation of the cube count and color. Example:
 * 3 blue
 */
fun parseCube(repr: String): Pair<CubeColor, Int> {
    val parts = repr.split(" ")
    val count = parts.first().trim().toInt()
    val color = CubeColor.parse(parts.last().trim())
    return color to count
}
