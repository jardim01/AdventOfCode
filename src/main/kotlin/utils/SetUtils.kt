package utils

fun <T> intersect(vararg sets: Set<T>): Set<T> {
    return sets.reduce { acc, set -> acc.intersect(set) }
}
