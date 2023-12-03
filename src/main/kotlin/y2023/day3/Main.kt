package y2023.day3

import resourceReader
import y2023.day3.domain.Dot
import y2023.day3.domain.SchematicNumber
import y2023.day3.domain.Slot
import y2023.day3.domain.SlotItem
import y2023.day3.domain.Symbol
import java.io.BufferedReader

typealias Schematic = List<List<Slot<SlotItem>>>

fun main() {
    val reader = "2023/3/input.txt".resourceReader()
    val schematic = buildSchematic(reader)

    val partNumberSum = schematic
        .flatten()
        .filter { slot ->
            slot.item is SchematicNumber &&
                schematic.getAdjacentSlots(slot.x, slot.y).any { it.item is Symbol }
        }
        .map { it.item as SchematicNumber }
        .distinct()
        .sumOf { it.number }
    println("Part number sum: $partNumberSum")

    val gearRatioSum = schematic
        .flatten()
        .filter { it.item is Symbol && it.item.symbol == '*' }
        .mapNotNull {
            val adjacentPartNumbers = schematic
                .getAdjacentSlots(it.x, it.y)
                .filter { slot -> slot.item is SchematicNumber }
                .map { slot -> slot.item as SchematicNumber }
                .distinct()
            // return gear ratio or null
            if (adjacentPartNumbers.size != 2) null
            else adjacentPartNumbers.fold(1) { acc, item -> acc * item.number }
        }
        .sum()
    println("Gear ratio sum: $gearRatioSum")
}

fun buildSchematic(reader: BufferedReader): Schematic {
    val schematic: Schematic = reader.lines().toList().mapIndexed { y, line ->
        var partNumber = SchematicNumber(0)
        line.mapIndexed { x, c ->
            val item = if (c.isDigit()) {
                partNumber.number = partNumber.number * 10 + c.digitToInt()
                partNumber
            } else {
                partNumber = SchematicNumber(0)
                if (c == '.') Dot
                else Symbol(c)
            }
            Slot(x, y, item)
        }
    }.toList()

    return schematic
}

fun Schematic.getAdjacentSlots(x: Int, y: Int): List<Slot<SlotItem>> {
    val adjacentSlots = mutableListOf<Slot<SlotItem>>()
    for (deltaY in -1..1) {
        for (deltaX in -1..1) {
            if (deltaY == 0 && deltaX == 0) continue
            val slot = getOrNull(y + deltaY)?.getOrNull(x + deltaX)
            if (slot != null) adjacentSlots.add(slot)
        }
    }
    return adjacentSlots
}
