package y2022.day8

import utils.resourceReader
import y2022.day8.domain.TreeGrid

fun main() {
    val reader = "2022/8/input.txt".resourceReader()

    val grid = TreeGrid.fromString(reader.readText().trim())

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
