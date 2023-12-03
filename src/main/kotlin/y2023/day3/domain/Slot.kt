package y2023.day3.domain

data class Slot<T : SlotItem>(val x: Int, val y: Int, val item: T)
