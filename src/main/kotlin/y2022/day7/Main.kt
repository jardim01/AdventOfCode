package y2022.day7

import utils.resourceReader
import y2022.day7.domain.Command
import y2022.day7.domain.Directory
import y2022.day7.domain.File
import y2022.day7.domain.isCd
import y2022.day7.domain.isCommand
import y2022.day7.domain.recurse

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
