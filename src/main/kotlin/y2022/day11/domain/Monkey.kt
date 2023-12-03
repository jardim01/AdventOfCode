package y2022.day11.domain

data class Monkey(
    val number: Int,
    val items: MutableList<Long>,
    val divisor: Int,
    val operation: (Long) -> Long,
    val monkeyTrue: Int,
    val monkeyFalse: Int,
    var nOfInspections: Int = 0,
) {
    fun test(value: Long) = value % divisor == 0L
}

val monkeys = listOf(
    Monkey(
        number = 0,
        items = mutableListOf(89, 95, 92, 64, 87, 68),
        divisor = 2,
        operation = { it * 11 },
        monkeyTrue = 7,
        monkeyFalse = 4,
    ),
    Monkey(
        number = 1,
        items = mutableListOf(87, 67),
        divisor = 13,
        operation = { it + 1 },
        monkeyTrue = 3,
        monkeyFalse = 6,
    ),
    Monkey(
        number = 2,
        items = mutableListOf(95, 79, 92, 82, 60),
        divisor = 3,
        operation = { it + 6 },
        monkeyTrue = 1,
        monkeyFalse = 6,
    ),
    Monkey(
        number = 3,
        items = mutableListOf(67, 97, 56),
        divisor = 17,
        operation = { it * it },
        monkeyTrue = 7,
        monkeyFalse = 0,
    ),
    Monkey(
        number = 4,
        items = mutableListOf(80, 68, 87, 94, 61, 59, 50, 68),
        divisor = 19,
        operation = { it * 7 },
        monkeyTrue = 5,
        monkeyFalse = 2,
    ),
    Monkey(
        number = 5,
        items = mutableListOf(73, 51, 76, 59),
        divisor = 7,
        operation = { it + 8 },
        monkeyTrue = 2,
        monkeyFalse = 1,
    ),
    Monkey(
        number = 6,
        items = mutableListOf(92),
        divisor = 11,
        operation = { it + 5 },
        monkeyTrue = 3,
        monkeyFalse = 0,
    ),
    Monkey(
        number = 7,
        items = mutableListOf(99, 76, 78, 76, 79, 90, 89),
        divisor = 5,
        operation = { it + 7 },
        monkeyTrue = 4,
        monkeyFalse = 5,
    ),
)
