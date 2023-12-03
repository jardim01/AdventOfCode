package y2022.day8

import resourceReader

data class Tree(val size: Int) {
    init {
        require(size in 0..9)
    }
}

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

data class TreeGrid(val grid: Grid<Tree>) {
    val width = grid.width
    val height = grid.height

    override fun toString() = grid.toString()

    fun forEach(block: (x: Int, y: Int, tree: Tree) -> Unit) {
        grid.rows.forEachIndexed { y, row ->
            row.forEachIndexed { x, tree ->
                block(x, y, tree)
            }
        }
    }

    fun <T> map(block: (tree: Tree, x: Int, y: Int) -> T): List<T> {
        return grid.rows.flatMapIndexed { y, row ->
            row.mapIndexed { x, tree ->
                block(tree, x, y)
            }
        }
    }

    private fun visibleFromNorth(x: Int, y: Int): Boolean {
        if (y == 0) return true
        val tree = grid[x, y]
        val column = grid.getColumn(x)
        return column.subList(0, y).all { it.size < tree.size }
    }

    private fun visibleFromSouth(x: Int, y: Int): Boolean {
        if (y == grid.height - 1) return true
        val tree = grid[x, y]
        val column = grid.getColumn(x)
        return column.subList(y + 1, grid.height).all { it.size < tree.size }
    }

    private fun visibleFromWest(x: Int, y: Int): Boolean {
        if (x == 0) return true
        val tree = grid[x, y]
        val row = grid.getRow(y)
        return row.subList(0, x).all { it.size < tree.size }
    }

    private fun visibleFromEast(x: Int, y: Int): Boolean {
        if (x == grid.width - 1) return true
        val tree = grid[x, y]
        val row = grid.getRow(y)
        return row.subList(x + 1, grid.width).all { it.size < tree.size }
    }

    fun treeIsVisible(x: Int, y: Int): Boolean {
        return visibleFromNorth(x, y) || visibleFromSouth(x, y) || visibleFromWest(x, y) || visibleFromEast(x, y)
    }

    private fun northViewingDistance(x: Int, y: Int): Int {
        val tree = grid[x, y]
        var viewingDistance = 0
        for (yy in y - 1 downTo 0) {
            viewingDistance++
            val t = grid[x, yy]
            if (t.size >= tree.size) break
        }
        return viewingDistance
    }

    private fun southViewingDistance(x: Int, y: Int): Int {
        val tree = grid[x, y]
        var viewingDistance = 0
        for (yy in y + 1 until grid.height) {
            viewingDistance++
            val t = grid[x, yy]
            if (t.size >= tree.size) break
        }
        return viewingDistance
    }

    private fun westViewingDistance(x: Int, y: Int): Int {
        val tree = grid[x, y]
        var viewingDistance = 0
        for (xx in x - 1 downTo 0) {
            viewingDistance++
            val t = grid[xx, y]
            if (t.size >= tree.size) break
        }
        return viewingDistance
    }

    private fun eastViewingDistance(x: Int, y: Int): Int {
        val tree = grid[x, y]
        var viewingDistance = 0
        for (xx in x + 1 until grid.width) {
            viewingDistance++
            val t = grid[xx, y]
            if (t.size >= tree.size) break
        }
        return viewingDistance
    }

    fun scenicScore(x: Int, y: Int): Int {
        return northViewingDistance(x, y) *
                southViewingDistance(x, y) *
                westViewingDistance(x, y) *
                eastViewingDistance(x, y)
    }
}

fun String.toTreeGrid(): TreeGrid {
    val rows = this.lines()
    require(rows.distinctBy { it.length }.size == 1)
    val grid: List<List<Tree>> = rows.map { row ->
        row.map {
            Tree(size = it.digitToInt())
        }
    }
    return TreeGrid(Grid(grid))
}


fun main() {
    val reader = "2022/8/input.txt".resourceReader()

    val grid = reader.readText().trim().toTreeGrid()

    var c = 0
    val visibleTrees = grid.grid.rows.flatMapIndexed { y, row ->
        row.filterIndexed { x, _ -> grid.treeIsVisible(x, y).also { c++ } }
    }

    println("Visible Trees: ${visibleTrees.size}")

    val scenicScoresGrid = grid.grid.transformIndexed { x, y, _ ->
        grid.scenicScore(x, y)
    }

    val highestScenicScore = scenicScoresGrid.rows.flatten().max()
    println("Highest scenic score: $highestScenicScore")
}