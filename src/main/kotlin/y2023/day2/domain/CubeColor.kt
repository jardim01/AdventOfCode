package y2023.day2.domain

enum class CubeColor(val repr: String) {
    RED("red"), GREEN("green"), BLUE("blue");

    companion object {
        fun parse(color: String): CubeColor =
            values().firstOrNull { it.repr == color }
                ?: throw IllegalArgumentException("Invalid color representation")
    }
}
