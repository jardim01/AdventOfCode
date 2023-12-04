package y2023.day2

import utils.resourceReader
import y2023.day2.domain.Game
import y2023.day2.domain.fewestCubeCountPossible
import y2023.day2.domain.isPossible

private const val N_OF_RED_CUBES = 12
private const val N_OF_GREEN_CUBES = 13
private const val N_OF_BLUE_CUBES = 14

fun main() {
    val reader = "2023/2/input.txt".resourceReader()
    var possibleIdSum = 0
    var setPowerSum = 0
    reader.forEachLine { gameRepr ->
        val game = Game.fromString(gameRepr)
        if (game.isPossible(N_OF_RED_CUBES, N_OF_GREEN_CUBES, N_OF_BLUE_CUBES)) possibleIdSum += game.id
        val fewestCount = game.fewestCubeCountPossible()
        val setPower = fewestCount.nOfRed * fewestCount.nOfGreen * fewestCount.nOfBlue
        setPowerSum += setPower
    }
    println("Sum of possible game ids: $possibleIdSum")
    println("Set power sum: $setPowerSum")
}
