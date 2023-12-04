package y2022.day8.domain

data class Tree(val size: Int) {
    init {
        require(size in 0..9)
    }
}
