class Day23(input: List<String>) {

    private val computers = input
        .map { line -> line.split("-") }
        .flatMap { (computer1, computer2) -> listOf(computer1 to computer2, computer2 to computer1) }
        .groupBy({ (computer1, _) -> computer1 }, { (_, computer2) -> computer2 })
        .toMap()

    fun part1() = groups()
        .filter { group -> group.size == 3 }
        .count { group -> group.any { computer -> computer.startsWith("t") } }

    fun part2() = groups()
        .maxBy { group -> group.size }
        .sorted()
        .joinToString(",")

    
    private fun groups(): Set<Set<String>> {
        val result = mutableSetOf<Set<String>>()

        computers.keys.forEach { computer ->
            val connections = computers[computer]!!
            
            val startGroup = setOf(computer)
            var currentGroups = setOf(startGroup)
            result += startGroup

            while (currentGroups.isNotEmpty()) {
                val nextGroups = mutableSetOf<Set<String>>()
                
                currentGroups.forEach { currentGroup ->
                    connections.forEach { nextComputer ->
                        if (nextComputer !in currentGroup) {
                            val otherConnections = computers[nextComputer]!!
                            if (otherConnections.containsAll(currentGroup)) {
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
        }

        return result
    }
}

fun main() {
    val realInput = readLines("day23.txt")
    val exampleInput = readLines("day23.txt", true)

//    val r1 = Day23(exampleInput).part2()
//    println(r1)

    val r2 = Day23(realInput).part2()
    println(r2)
}