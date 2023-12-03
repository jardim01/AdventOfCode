package y2022.day9

import resourceReader
import kotlin.math.absoluteValue

enum class Direction(val c: Char) {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');

    companion object {
        fun fromChar(c: Char) = Direction.values().firstOrNull { it.c == c } ?: throw IllegalArgumentException()
    }
}

data class Move(val direction: Direction, val distance: Int) {
    init {
        require(distance > 0)
    }

    companion object {
        fun fromString(string: String): Move {
            println(string)
            val direction = Direction.fromChar(string[0])
            val distance = string.substring(2).toInt()
            return Move(direction, distance)
        }
    }
}

data class Coordinate(val x: Int, val y: Int) {
    override fun toString() = "($x, $y)"
}

fun main() {
    val reader = "2022/9/input.txt".resourceReader()

    val moves = reader.readLines().map { Move.fromString(it) }

    var headCoord = Coordinate(0, 0)
    var tailCoord = headCoord.copy()
    val tailCoords = mutableSetOf(tailCoord.copy())

    moves.forEach { move ->
        repeat(move.distance) {
            val headNewX = when (move.direction) {
                Direction.UP -> headCoord.x
                Direction.DOWN -> headCoord.x
                Direction.LEFT -> headCoord.x - 1
                Direction.RIGHT -> headCoord.x + 1
            }
            val headNewY = when (move.direction) {
                Direction.UP -> headCoord.y - 1
                Direction.DOWN -> headCoord.y + 1
                Direction.LEFT -> headCoord.y
                Direction.RIGHT -> headCoord.y
            }
            headCoord = Coordinate(x = headNewX, y = headNewY)

            val horizontalDistance = headCoord.x - tailCoord.x
            val verticalDistance = headCoord.y - tailCoord.y

            var tailNewX = tailCoord.x
            var tailNewY = tailCoord.y
            if (horizontalDistance.absoluteValue == 2) {
                // plus or minus 1
                tailNewX += (horizontalDistance / horizontalDistance.absoluteValue)
                if (verticalDistance != 0) tailNewY += (verticalDistance / verticalDistance.absoluteValue)
            } else if (verticalDistance.absoluteValue == 2) {
                // plus or minus 1
                tailNewY += (verticalDistance / verticalDistance.absoluteValue)
                if (horizontalDistance != 0) tailNewX += (horizontalDistance / horizontalDistance.absoluteValue)
            }
            tailCoord = Coordinate(x = tailNewX, y = tailNewY)
            tailCoords.add(tailCoord)
        }
    }

    println(tailCoords.size)
}