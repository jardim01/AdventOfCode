package y2023.day3.domain

sealed class SlotItem
object Dot : SlotItem()
data class Symbol(val symbol: Char) : SlotItem()
class SchematicNumber(var number: Int) : SlotItem()
