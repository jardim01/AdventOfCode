package y2022.day9.domain

data class Move(val direction: Direction, val distance: Int) {
    init {
        require(distance > 0)
    }

    companion object {
        fun fromString(string: String): Move {
            val direction = Direction.fromChar(string[0])
            val distance = string.substring(2).toInt()
            return Move(direction, distance)
        }
    }
}
