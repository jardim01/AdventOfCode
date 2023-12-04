package y2022.day7.domain

data class Directory(
    val name: String,
    val parent: Directory?,
    val subdirectories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
) {
    val size: Int
        get() = subdirectories.sumOf { it.size } + files.sumOf { it.size }
}

fun Directory.recurse(block: (Directory) -> Unit) {
    block(this)
    this.subdirectories.forEach {
        it.recurse(block)
    }
}
