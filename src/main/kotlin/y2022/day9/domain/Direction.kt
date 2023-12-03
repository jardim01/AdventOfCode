package y2022.day9.domain

enum class Direction(val c: Char) {
    UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');

    companion object {
        fun fromChar(c: Char) = Direction.values().firstOrNull { it.c == c } ?: throw IllegalArgumentException()
    }
}
