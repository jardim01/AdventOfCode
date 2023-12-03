package y2022.day10.domain

class Clock {
    private val beforeCycleListeners = mutableListOf<(Int) -> Unit>()
    private val afterCycleListeners = mutableListOf<(Int) -> Unit>()
    private var cycle = 0

    fun beforeCycle(listener: (Int) -> Unit) {
        beforeCycleListeners.add(listener)
    }

    fun afterCycle(listener: (Int) -> Unit) {
        afterCycleListeners.add(listener)
    }

    fun tick() {
        beforeCycleListeners.forEach { listener ->
            listener(cycle + 1)
        }
        cycle++
        afterCycleListeners.forEach { listener ->
            listener(cycle)
        }
    }
}
