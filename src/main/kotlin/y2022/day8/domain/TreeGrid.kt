package y2022.day8.domain

import domain.Grid

data class TreeGrid(val grid: Grid<Tree>) {
    companion object {
        fun fromString(text: String): TreeGrid {
            val rows = text.lines()
            require(rows.distinctBy { it.length }.size == 1)
            val grid: List<List<Tree>> = rows.map { row ->
                row.map {
                    Tree(size = it.digitToInt())
                }
            }
            return TreeGrid(Grid(grid))
        }
    }

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
