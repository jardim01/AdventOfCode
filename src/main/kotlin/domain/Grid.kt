package domain

class Grid<T>(val rows: List<List<T>>) {
    val width: Int = rows.first().size
    val height: Int = rows.size

    init {
        // grid must have at least one row
        require(rows.isNotEmpty())
        // all rows must have the same size
        require(rows.distinctBy { it.size }.size == 1)
    }

    operator fun get(x: Int, y: Int): T {
        require(x in 0 until width)
        require(y in 0 until height)
        return rows[y][x]
    }

    fun getRow(y: Int): List<T> {
        require(y in 0 until height)
        return rows[y]
    }

    fun getColumn(x: Int): List<T> {
        require(x in 0 until width)
        val column = mutableListOf<T>()
        repeat(height) { y ->
            val item = rows[y][x]
            column.add(item)
        }
        return column
    }

    fun <R> transformIndexed(transform: (x: Int, y: Int, element: T) -> R): Grid<R> {
        val newRows = rows.mapIndexed { y, row ->
            row.mapIndexed { x, element ->
                transform(x, y, element)
            }
        }
        return Grid(newRows)
    }

    fun <R> transform(transform: (T) -> R): Grid<R> {
        return transformIndexed { _, _, e ->
            transform(e)
        }
    }

    override fun toString() = rows.joinToString(separator = "\n") { row ->
        row.joinToString(separator = "") { it.toString() }
    }
}
