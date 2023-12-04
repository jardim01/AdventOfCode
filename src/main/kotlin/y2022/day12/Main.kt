package y2022.day12

import algorithms.dijkstra
import algorithms.shortestPath
import utils.resourceReader
import y2022.day12.domain.Graph
import y2022.day12.domain.Square
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

private const val PART_2 = true

fun main() {
    val reader = "2022/12/input.txt".resourceReader()
    val heightmap: List<List<Square>> = buildList {
        var y = 0
        reader.forEachLine { line ->
            var x = 0
            val row = line.map { c -> Square(x++, y, c) }
            add(row)
            y++
        }
    }
    val weights = mutableMapOf<Pair<Square, Square>, Int>()
    heightmap.forEachIndexed { y, row ->
        row.forEachIndexed { x, square ->
            val left = row.getOrNull(x - 1)
            val right = row.getOrNull(x + 1)
            val up = heightmap.getOrNull(y - 1)?.getOrNull(x)
            val down = heightmap.getOrNull(y + 1)?.getOrNull(x)

            val squareEdges = setOfNotNull(left, right, up, down).toMutableSet()
            squareEdges.forEach { other ->
                val delta = other.elevation - square.elevation
                if (delta <= 1) weights[square to other] = 1
            }
        }
    }

    val end = heightmap.flatten().first { it.char == 'E' }
    val graph = Graph(weights = weights)
    val minSteps = if (!PART_2) {
        val start = heightmap.flatten().first { it.char == 'S' }
        val shortestPathTree = dijkstra(graph, start)
        shortestPath(shortestPathTree, start, end).size - 1
    } else {
        val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val minSteps = CopyOnWriteArrayList<Int>()
        heightmap
            .flatten()
            .filter { it.elevation == 0 }
            .map { start ->
                threadPool.submit {
                    val shortestPathTree = dijkstra(graph, start)
                    val steps = shortestPath(shortestPathTree, start, end).size - 1
                    minSteps.add(steps)
                }
            }
            .forEach { it.get() }
        threadPool.shutdown()
        minSteps.min()
    }
    println("Min steps: $minSteps")
}
