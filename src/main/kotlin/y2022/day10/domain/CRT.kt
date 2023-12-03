package y2022.day10.domain

private const val SPRITE_RANGE = 1
private const val CRT_WIDTH = 40
private const val LIT_PIXEL = '#'
private const val UNLIT_PIXEL = ' '

class CRT(clock: Clock, private val cpu: CPU) {
    private val pixels = StringBuilder()

    init {
        clear()
        clock.beforeCycle {
            val pixelIdx = it - 1
            val x = pixelIdx % CRT_WIDTH
            val y = pixelIdx / CRT_WIDTH
            val lit = cpu.x in (x - SPRITE_RANGE)..(x + SPRITE_RANGE)
            val charIdx = y * CRT_WIDTH + x
            val char = if (lit) LIT_PIXEL else UNLIT_PIXEL
            pixels.setCharAt(charIdx, char)
        }
    }

    private fun clear() {
        pixels.clear().append(UNLIT_PIXEL.repeat(240))
    }

    override fun toString(): String {
        return pixels.windowed(size = 40, step = 40).joinToString(separator = "\n")
    }
}

fun Char.repeat(n: Int) = toString().repeat(n)
