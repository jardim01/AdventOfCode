package y2022.day2.domain

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
