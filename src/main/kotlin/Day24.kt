import java.lang.IllegalArgumentException

class Day24(input: List<List<String>>) {

    private val inputs = input.first().map { it.split(": ") }.map { (a, b) -> a to b.toInt() }

    private val operations = input.last()
        .map { it.split(" -> ") }
        .map { (a, b) -> a.split(" ") + b }
        .map { (a, b, c, d) -> Operation(a, b, c, d) }

    fun part1() {
        println(inputs)
        println(operations)

        solve()
    }

    fun part2() = 2

    private fun solve() {
        val currentInputs = inputs.toMap().toMutableMap()
        val currentOperations = operations.toMutableList()

        while (currentOperations.isNotEmpty()) {
            val solvedOperations = mutableSetOf<Operation>()
            
            currentOperations.forEach { currentOperation ->
                val value1 = currentInputs[currentOperation.input1]
                val value2 = currentInputs[currentOperation.input2]

                if (value1 != null && value2 != null) {
                    val outputValue = when (currentOperation.gate) {
                        "AND" -> value1.and(value2)
                        "OR" -> value1.or(value2)
                        "XOR" -> value1.xor(value2)
                        else -> throw IllegalArgumentException()
                    }

                    currentInputs[currentOperation.output] = outputValue
                    solvedOperations += currentOperation
                }
            }

            currentOperations -= solvedOperations
        }

        val sortedKeys = currentInputs.keys
            .filter { a -> a.startsWith("z") }
            .sorted().reversed()
        
        
        val result = sortedKeys
            .map { currentInputs[it]!! }
            .joinToString("")

        println(result)
        println(result.toLong(2))
    }

    data class Operation(val input1: String, val gate: String, val input2: String, val output: String)
}

fun main() {
    val realInput = readGroups("day24.txt")
    val exampleInput = readGroups("day24.txt", true)

    val r1 = Day24(exampleInput).part1()
    println(r1)

    val r2 = Day24(realInput).part1()
    println(r2)
}