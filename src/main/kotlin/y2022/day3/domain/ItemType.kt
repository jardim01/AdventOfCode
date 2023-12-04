package y2022.day3.domain

data class ItemType(val type: Char) {
    val priority = if (type.isLowerCase()) {
        type.code - 'a'.code + 1
    } else {
        type.code - 'A'.code + 27
    }
}
