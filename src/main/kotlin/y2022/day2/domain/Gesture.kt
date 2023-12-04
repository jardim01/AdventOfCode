package y2022.day2.domain

enum class Gesture(val points: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun fromStr(str: String): Gesture {
            return when (str) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException(str)
            }
        }
    }

    fun vs(other: Gesture): RoundResult {
        return when (this) {
            ROCK -> when (other) {
                ROCK -> RoundResult.DRAW
                PAPER -> RoundResult.LOST
                SCISSORS -> RoundResult.WON
            }

            PAPER -> when (other) {
                ROCK -> RoundResult.WON
                PAPER -> RoundResult.DRAW
                SCISSORS -> RoundResult.LOST
            }

            SCISSORS -> when (other) {
                ROCK -> RoundResult.LOST
                PAPER -> RoundResult.WON
                SCISSORS -> RoundResult.DRAW
            }
        }
    }
}
