package y2023.day2.domain

data class Game(val id: Int, val sets: List<CubeSet>) {
    companion object {
        /**
         * @param gameRepr the representation of the game. Example:
         * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
         */
        fun fromString(gameRepr: String): Game {
            val id = parseGameId(gameRepr)
            val sets = parseCubeSets(gameRepr)
            return Game(id = id, sets = sets)
        }

        /**
         * @param gameRepr the representation of the game. Example:
         * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
         */
        private fun parseGameId(gameRepr: String): Int {
            return Regex("Game (\\d+):").find(gameRepr)!!.groupValues[1].toInt()
        }

        /**
         * @param gameRepr the representation of the game. Example:
         * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
         */
        private fun parseCubeSets(gameRepr: String): List<CubeSet> {
            val sets = gameRepr.substringAfter(": ").split("; ")
            return sets.map(::parseCubeSet)
        }

        /**
         * @param repr the representation of the set. Example:
         * 3 blue, 4 red
         */
        private fun parseCubeSet(repr: String): CubeSet {
            val parts = repr.split(", ")
            var nOfRed = 0
            var nOfGreen = 0
            var nOfBlue = 0
            parts.forEach {
                val (color, count) = parseCube(it)
                when (color) {
                    CubeColor.RED -> nOfRed += count
                    CubeColor.GREEN -> nOfGreen += count
                    CubeColor.BLUE -> nOfBlue += count
                }
            }
            return CubeSet(nOfRed = nOfRed, nOfGreen = nOfGreen, nOfBlue = nOfBlue)
        }

        /**
         * @param repr the representation of the cube count and color. Example:
         * 3 blue
         */
        private fun parseCube(repr: String): Pair<CubeColor, Int> {
            val parts = repr.split(" ")
            val count = parts.first().trim().toInt()
            val color = CubeColor.parse(parts.last().trim())
            return color to count
        }
    }
}

fun Game.isPossible(nOfRed: Int, nOfGreen: Int, nOfBlue: Int): Boolean {
    return sets.all {
        it.nOfRed <= nOfRed && it.nOfGreen <= nOfGreen && it.nOfBlue <= nOfBlue
    }
}

fun Game.fewestCubeCountPossible(): CubeSet {
    val fewestRed = this.sets.maxOf { it.nOfRed }
    val fewestGreen = this.sets.maxOf { it.nOfGreen }
    val fewestBlue = this.sets.maxOf { it.nOfBlue }
    return CubeSet(nOfRed = fewestRed, nOfGreen = fewestGreen, nOfBlue = fewestBlue)
}
