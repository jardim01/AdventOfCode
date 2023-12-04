package y2022.day10

import utils.resourceReader
import y2022.day10.domain.CPU
import y2022.day10.domain.CRT
import y2022.day10.domain.Clock
import y2022.day10.domain.Instruction

private val cyclesToCheck = listOf(20, 60, 100, 140, 180, 220)

fun main() {
    val reader = "2022/10/input.txt".resourceReader()

    val clock = Clock()
    val cpu = CPU(clock = clock)
    val crt = CRT(clock = clock, cpu = cpu)

    var signalStrengthSum = 0
    clock.beforeCycle { cycle ->
        if (cycle in cyclesToCheck) {
            val signalStrength = cycle * cpu.x
            signalStrengthSum += signalStrength
        }
    }

    reader.forEachLine {
        val instruction = Instruction.parse(it)
        cpu.queue(instruction)
    }

    repeat(240) {
        clock.tick()
    }

    println("Signal strength sum: $signalStrengthSum")
    println(crt)
}
