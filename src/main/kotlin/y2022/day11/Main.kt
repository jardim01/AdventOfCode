package y2022.day11

import y2022.day11.domain.monkeys

private const val PART_2 = true
private val N_OF_ROUNDS = if (PART_2) 10_000 else 20
private const val N_OF_MONKEYS_TO_FOCUS = 2

fun main() {
    val divisorProduct = monkeys.fold(1) { acc, monkey -> acc * monkey.divisor }
    repeat(N_OF_ROUNDS) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                var worryLevel = monkey.items.removeFirstOrNull() ?: return@forEach
                monkey.nOfInspections++
                worryLevel =
                    if (PART_2) monkey.operation(worryLevel) % divisorProduct
                    else monkey.operation(worryLevel) / 3
                val throwToNumber = if (monkey.test(worryLevel)) monkey.monkeyTrue else monkey.monkeyFalse
                monkeys.first { it.number == throwToNumber }.items.add(worryLevel)
            }
        }
    }

    val monkeysSortedByActivity = monkeys.sortedByDescending { it.nOfInspections }
    val monkeyBusiness = monkeysSortedByActivity
        .take(N_OF_MONKEYS_TO_FOCUS)
        .fold(initial = 1L) { acc, monkey -> acc * monkey.nOfInspections }
    println("Monkey business: $monkeyBusiness")
}
