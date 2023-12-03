package y2022.day6

import resourceReader

const val PACKET_MARKER_SIZE = 4
const val MESSAGE_MARKER_SIZE = 14

fun main() {
    val reader = "2022/6/input.txt".resourceReader()

    reader.use {
        val line = reader.readLine()
        val markerIdx = line
            .windowed(size = PACKET_MARKER_SIZE, step = 1)
            .indexOfFirst { it.toSet().size == it.length } + PACKET_MARKER_SIZE
        println("First marker after character $markerIdx")
        val msgIdx = line
            .windowed(size = MESSAGE_MARKER_SIZE, step = 1)
            .indexOfFirst { it.toSet().size == it.length } + MESSAGE_MARKER_SIZE
        println("First marker after character $msgIdx")
    }
}