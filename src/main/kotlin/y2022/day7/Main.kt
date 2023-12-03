package y2022.day7

import resourceReader

data class Command(val name: String, val args: List<String>) {
    companion object {
        private const val COMMAND_PREFIX = "$ "

        fun parse(str: String): Command {
            val argsIdx = str.indexOf(" ", startIndex = 2)
                .let {
                    if (it == -1) str.length
                    else it
                }

            val name = str.substring(COMMAND_PREFIX.length, argsIdx)
            val args =
                if (argsIdx == str.length) emptyList()
                else str.substring(argsIdx + 1).split(" ")
            return Command(name, args)
        }
    }
}

class Path(path: String) {
    val pathname = "$path/"
        .replace('\\', '/')
        .replace(Regex("/+"), "/")

    val parent
        get() = Path(pathname.substring(0, pathname.trimEnd('/').lastIndexOf('/')))

    override fun toString() = "Pathname(pathname=$pathname)"

    override fun equals(other: Any?): Boolean {
        return if (other !is Path) false
        else other.pathname == this.pathname
    }

    override fun hashCode() = pathname.hashCode()
}

fun Path(base: Path, vararg subdirectories: String): Path {
    return Path("${base.pathname}${subdirectories.joinToString(separator = "/")}")
}

val String.isCommand get() = this.startsWith("$")

val Command.isCd get() = this.name == "cd"
val Command.isLs get() = this.name == "ls"

data class File(val size: Int, val name: String)

data class Directory(
    val name: String,
    val parent: Directory?,
    val subdirectories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf()
) {
    val size: Int
        get() = subdirectories.sumOf { it.size } + files.sumOf { it.size }

    fun tree(): String {
        var str = name
        if (subdirectories.isNotEmpty()) str += "\n"
        subdirectories.forEach {
            str += it.tree().prependIndent("\t") + "\n"
        }
        str = str.trim()
        if (files.isNotEmpty()) str += "\n"
        files.forEach {
            str += it.toString().prependIndent("\t") + "\n"
        }
        str = str.trim()
        return str
    }
}

fun Directory.recurse(block: (Directory) -> Unit) {
    block(this)
    this.subdirectories.forEach {
        it.recurse(block)
    }
}

private const val FILESYSTEM_SIZE = 70000000
private const val UPDATE_SIZE = 30000000

fun main() {
    val reader = "2022/7/input.txt".resourceReader()

    val root = Directory("/", parent = null)

    var currentDir = root
    reader.forEachLine { line ->
        if (line.isCommand) {
            val command = Command.parse(line)
            if (command.isCd) {
                currentDir =
                    when (val dir = command.args.first()) {
                        "/" -> root
                        ".." -> currentDir.parent ?: throw IllegalStateException()
                        else -> {
                            currentDir.subdirectories.firstOrNull { it.name == dir }
                                ?: Directory(dir, parent = currentDir)
                                    .also {
                                        currentDir.subdirectories.add(it)
                                    }
                        }
                    }
            }
        } else {
            val parts = line.split(" ")
            val name = parts.last()
            if (parts.first() == "dir") {
                if (currentDir.subdirectories.none { it.name == name }) {
                    val newDir = Directory(name, parent = currentDir)
                    currentDir.subdirectories.add(newDir)
                }
            } else {
                val file = File(size = parts.first().toInt(), name = name)
                if (currentDir.files.none { it.name == file.name }) {
                    currentDir.files.add(file)
                }
            }
        }
    }

    val allDirectories = mutableListOf<Directory>()
    root.recurse {
        allDirectories.add(it)
    }

    val directoriesThatCanBeDeleted = allDirectories.filter { it.size < 100000 }

    val totalSizeOfDirectoriesThatCanBeDeleted = directoriesThatCanBeDeleted.sumOf { it.size }

    println("Total size: $totalSizeOfDirectoriesThatCanBeDeleted")

    val free = FILESYSTEM_SIZE - root.size
    println("Free: $free")
    val toFree = UPDATE_SIZE - free
    println("To free: $toFree")

    val d = allDirectories
        .sortedBy { it.size }
        .first { it.size >= toFree }
    println("Size: ${d.size}")
}