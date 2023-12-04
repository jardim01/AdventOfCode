package utils

import kotlin.math.pow

fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()
