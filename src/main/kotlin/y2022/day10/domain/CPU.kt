package y2022.day10.domain

class CPU(clock: Clock) {
    private val operations = mutableListOf<() -> Unit>()

    var x = 1
        private set

    init {
        clock.afterCycle {
            operations.removeFirstOrNull()?.invoke()
        }
    }

    fun queue(instruction: Instruction) {
        repeat(instruction.durationCycles - 1) {
            operations.add { /* wait */ }
        }

        when (instruction) {
            Instruction.Noop -> {
                operations.add { /* no operation */ }
            }

            is Instruction.Addx -> {
                operations.add {
                    x += instruction.value
                }
            }
        }
    }
}
