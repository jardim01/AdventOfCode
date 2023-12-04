package y2022.day2.domain

data class Round(val g1: Gesture, val g2: Gesture) {
    val p1Result = g1.vs(g2)
    val p2Result = g2.vs(g1)
    val p1Points = p1Result.points + g1.points
    val p2Points = p2Result.points + g2.points
}
