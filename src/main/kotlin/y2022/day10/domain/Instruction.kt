package y2022.day10.domain

sealed class Instruction(val durationCycles: Int) {
    object Noop : Instruction(1)
    class Addx(val value: Int) : Instruction(2)

    init {
        require(durationCycles > 0)
    }

    companion object {
        fun parse(text: String): Instruction {
            val parts = text.split(" ")
            return when (parts[0]) {
                "noop" -> Noop

                "addx" -> {
                    val value = parts[1].toInt()
                    Addx(value)
                }

                else -> throw IllegalArgumentException("Invalid instruction")
            }
        }
    }
}
