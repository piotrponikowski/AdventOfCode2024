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
        .joinToString(",")

    
    private fun groups(): Set<List<String>> {
        val result = mutableSetOf<List<String>>()

        computers.keys.forEach { computer ->
            val connections = computers[computer]!!
            var currentGroups = setOf(listOf(computer))

            while (currentGroups.isNotEmpty()) {
                val nextGroups = mutableSetOf<List<String>>()
                
                currentGroups.forEach { currentGroup ->
                    connections.forEach { nextComputer ->
                        if (nextComputer !in currentGroup) {
                            val otherConnections = computers[nextComputer]!!
                            if (otherConnections.containsAll(currentGroup)) {
                                val nextGroup = (currentGroup + nextComputer).sorted()
                                
                                if (nextGroup !in result) {
                                    nextGroups += nextGroup
                                    result += nextGroup
                                }
                            }
                        }
                    }
                }
                
                currentGroups = nextGroups
            }
        }

        return result
    }
}