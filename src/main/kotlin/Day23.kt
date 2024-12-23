class Day23(input: List<String>) {

    private val computers = input
        .map { line -> line.split("-") }
        .flatMap { (computer1, computer2) -> listOf(computer1 to computer2, computer2 to computer1) }
        .groupBy({ (computer1, _) -> computer1 }, { (_, computer2) -> computer2 })
        .toMap()

    fun part1() = allGroups()
        .filter { group -> group.size == 3 }
        .toSet()
        .count { group -> group.any { computer -> computer.startsWith("t") } }

    fun part2() = allGroups()
        .maxBy { group -> group.size }
        .sorted()
        .joinToString(",")

    fun allGroups() = computers.keys
        .fold(emptyList<Set<String>>()) { result, computer -> result + groups(computer) }

    var index = 0
    private fun groups(computer: String): List<Set<String>> {
        println(index++)
        val connectedComputers = computers[computer]!!
        val cache = computers.filterKeys { computer2 -> computer2 in connectedComputers }

        val result = mutableListOf(setOf(computer))
        var currentGroups = setOf(setOf(computer))

        while (currentGroups.isNotEmpty()) {
            val nextGroups = mutableSetOf<Set<String>>()

            currentGroups.forEach { currentGroup ->
                connectedComputers.forEach { nextComputer ->
                    if (nextComputer !in currentGroup) {
                        val nextConnections = cache[nextComputer]!!
                        if (nextConnections.containsAll(currentGroup)) {
                            val nextGroup = currentGroup + nextComputer
                            if (nextGroup !in nextGroups) {
                                nextGroups += nextGroup
                            }
                        }
                    }
                }
            }

            result += nextGroups
            currentGroups = nextGroups
        }

        return result
    }
}

fun main() {
    val realInput = readLines("day23.txt")
    val exampleInput = readLines("day23.txt", true)

//    val r1 = Day23(exampleInput).part2()
//    println(r1)

    val r2 = Day23(realInput).allGroups().groupBy {it}
        .map { (a, b) -> a to b.size }
        .sortedBy { it.second }
        .forEach { println(it) }
    println(r2)
}