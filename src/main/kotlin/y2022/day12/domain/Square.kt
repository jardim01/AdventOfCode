package y2022.day12.domain

data class Square(val x: Int, val y: Int, val char: Char) {
    val elevation: Int
        get() = if (char == 'S') 0
        else if (char == 'E') 'z' - 'a'
        else char - 'a'
}
