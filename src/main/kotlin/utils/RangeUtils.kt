package utils

fun IntRange.contains(other: IntRange) = other.first >= this.first && other.last <= this.last
fun IntRange.overlaps(other: IntRange) =
    this.first in other || this.last in other || this.contains(other) || other.contains(this)
