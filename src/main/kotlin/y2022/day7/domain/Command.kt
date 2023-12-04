package y2022.day7.domain

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

val Command.isCd get() = this.name == "cd"

val String.isCommand get() = this.startsWith("$")
