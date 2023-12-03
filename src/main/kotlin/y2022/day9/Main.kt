package y2022.day9

import resourceReader
import y2022.day9.domain.Coordinate
import y2022.day9.domain.Direction
import y2022.day9.domain.Move
import y2022.day9.domain.Node
import kotlin.math.absoluteValue

private const val ROPE_SIZE = 10

fun main() {
    val reader = "2022/9/input.txt".resourceReader()

    val moves = reader.readLines().map(Move::fromString)

    val head = buildRope(ROPE_SIZE)
    val tail = head.tail()
    val tailCoords = mutableSetOf(tail.value)

    moves.forEach { move ->
        repeat(move.distance) {
            var curr = head
            while (true) {
                if (curr == head) {
                    val newCoord = when (move.direction) {
                        Direction.UP -> curr.value.copy(y = curr.value.y - 1)
                        Direction.DOWN -> curr.value.copy(y = curr.value.y + 1)
                        Direction.LEFT -> curr.value.copy(x = curr.value.x - 1)
                        Direction.RIGHT -> curr.value.copy(x = curr.value.x + 1)
                    }
                    curr.value = newCoord
                } else {
                    val prev = curr.previous!!
                    val hDist = prev.value.x - curr.value.x
                    val vDist = prev.value.y - curr.value.y

                    var tailNewX = curr.value.x
                    var tailNewY = curr.value.y
                    if (hDist.absoluteValue == 2) {
                        // plus or minus 1
                        tailNewX += (hDist / hDist.absoluteValue)
                        if (vDist != 0) tailNewY += (vDist / vDist.absoluteValue)
                    } else if (vDist.absoluteValue == 2) {
                        // plus or minus 1
                        tailNewY += (vDist / vDist.absoluteValue)
                        if (hDist != 0) tailNewX += (hDist / hDist.absoluteValue)
                    }
                    curr.value = Coordinate(x = tailNewX, y = tailNewY)
                    if (curr == tail) {
                        // save coord
                        tailCoords.add(curr.value)
                    }
                }

                curr = curr.next ?: break
            }
        }
    }

    println(tailCoords.size)
}

fun buildRope(size: Int): Node<Coordinate> {
    val head = Node(value = Coordinate(0, 0), previous = null, next = null)
    var curr = head
    repeat(size - 1) {
        val newNode = curr.copy()
        curr.next = newNode
        newNode.previous = curr
        curr = newNode
    }
    return head
}
