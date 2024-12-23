class Day23(input: List<String>) {

    private val connections = input
        .map { line -> line.split("-") }
        .flatMap { (computer1, computer2) -> listOf(computer1 to computer2, computer2 to computer1) }

    private val groupedConnections = connections
        .groupBy { it.first }
        .map { (a, b) -> a to b.map { it.second } }
        .toMap()

    private val computers = (connections.map { it.first } + connections.map { it.second }).toSet()


//    fun part1() {
//        println(computers)
//        println(connections)
//
//        val result = computers.flatMap { solve(it) }.toSet()
//        println(result)
//        result.forEach { println(it) }
//
//        val r2 = result.filter { it.any { it.startsWith("t") } }.count()
//        println(r2)
//    }

    fun part1() = computers.flatMap { computer -> groups(computer) }.toSet()
        .filter { group -> group.size == 3 }
        .count { group -> group.any { computer -> computer.startsWith("t") } }

    fun part2() = computers.flatMap { computer -> groups(computer) }
        .maxBy { group -> group.size }
        .sorted()
        .joinToString(",")
    

    private fun groups(computer: String): Set<Set<String>> {
        val connectedComputers = groupedConnections[computer]!!
        
        val result = mutableSetOf(setOf(computer))
        val currentGroups = mutableListOf(setOf(computer))
        
        while (currentGroups.isNotEmpty()) {
            val currentGroup = currentGroups.removeFirst()
            
            connectedComputers.forEach { nextComputer ->
                if(nextComputer !in currentGroup) {
                    val nextConnections = groupedConnections[nextComputer]!!
                    if (nextConnections.containsAll(currentGroup)) {
                        val nextGroup = currentGroup + nextComputer
                        if (nextGroup !in result) {
                            result += nextGroup
                            currentGroups += nextGroup
                        }
                    }
                }
            }
        }
        
        return result
    }

//    fun part2() {
//        computers.forEach { computer ->
//            val connectedComputers = groupedConnections[computer]!!
//            var allConnected = true
//            connectedComputers.forEach { other1 ->
//                connectedComputers.forEach { other2 ->
//                    val connected = other2 in groupedConnections[other1]!! 
//                    if(!connected) {
//                        allConnected = false
//                    }
//                }
//            }
//            println(allConnected)
//        }
//    }

//    fun part2() {
//        val groups = computers.map { setOf(it) }.toMutableList()
//        val currentGroups = groups.toMutableList()
//
//        var index = 0
//        while (currentGroups.isNotEmpty()) {
//            val currentGroup = currentGroups.removeFirst()
//            val lastComputer = currentGroup.last()
//
//            val connectedComputers = groupedConnections[lastComputer]!!
//            connectedComputers.forEach { nextComputer ->
//                val canBeAdded = currentGroup
//                    .all { otherComputer -> groupedConnections[otherComputer]!!.contains(nextComputer) }
//
//                if (canBeAdded) {
//                    val newGroup = currentGroup + nextComputer
//                    if(newGroup !in groups) {
//                        groups += newGroup
//                        currentGroups += newGroup
//                        index++
//                        if(index % 10 == 0) {
//                            val sizes = groups.map { it.size }.groupBy { it }.map { (a, b) -> a to b.size }
//                            println(sizes)
//                            println(groups)
//                        }
//                    }
//                }
//            }
//        }
//
//
//        println(groups)
//
//    }

        private fun solve(computer: String): List<Set<String>> {
            val connectedComputers = groupedConnections[computer]!!
            val result = mutableListOf<Set<String>>()

            connectedComputers.map { computer1 ->
                connectedComputers.map { computer2 ->
                    if (computer1 != computer2) {
                        val connections1 = groupedConnections[computer1]!!
                        val connections2 = groupedConnections[computer2]!!

                        if (connections1.contains(computer2) && connections2.contains(computer1)) {
                            result += setOf(computer, computer1, computer2)
                        }
                    }
                }
            }

            return result
        }


//        private fun group(computer: String): Set<String> {
//            val result = mutableSetOf(computer)
//            val currentComputers = mutableListOf(computer)
//
//            while (currentComputers.isNotEmpty()) {
//                val currentComputer = currentComputers.removeFirst()
//                val nextComputers = groupedConnections[currentComputer]!!.filter { it !in result }
//                result += nextComputers
//                currentComputers += nextComputers
//            }
//
//            return result
//        }


    }

    fun main() {
        val realInput = readLines("day23.txt")
        val exampleInput = readLines("day23.txt", true)

        val r1 = Day23(exampleInput).part2()
        println(r1)

        val r2 = Day23(realInput).part2()
        println(r2)
    }