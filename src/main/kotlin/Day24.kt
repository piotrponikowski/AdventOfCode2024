class Day24(input: List<List<String>>) {

    private val inputs = input.first()
        .map { line -> line.split(": ") }
        .associate { (input, value) -> input to value.toInt() }

    private val gates = input.last()
        .map { line -> line.split(" -> ") }
        .map { (operation, output) -> operation.split(" ") + output }
        .map { (input1, type, input2, output) -> Operation(listOf(input1, input2), GateType.valueOf(type), output) }

    fun part1(): Long {
        println(inputs)
        println(gates)

        return solve(inputs)!!.toLong(2)
    }

    fun part2() = 2

    private fun solve(inputs: Map<String, Int>): String? {
        val values = inputs.toMutableMap()
        val unsolvedGates = gates.toMutableList()

        while (unsolvedGates.isNotEmpty()) {
            val solvedGates = mutableSetOf<Operation>()

            unsolvedGates.forEach { currentGate ->
                val (input1, input2) = currentGate.inputs
                val output = currentGate.output

                val inputValue1 = values[input1]
                val inputValue2 = values[input2]

                if (inputValue1 != null && inputValue2 != null) {
                    val outputValue = when (currentGate.type) {
                        GateType.AND -> inputValue1.and(inputValue2)
                        GateType.OR -> inputValue1.or(inputValue2)
                        GateType.XOR -> inputValue1.xor(inputValue2)
                    }

                    values[output] = outputValue
                    solvedGates += currentGate
                }
            }

            if (solvedGates.isEmpty()) {
                return null
            }

            unsolvedGates -= solvedGates
        }

        return decodeValue(values)
    }

    private fun decodeValue(values: Map<String, Int>, prefix: String = "z") = values.keys
        .filter { value -> value.startsWith(prefix) }
        .sorted().reversed()
        .map { values[it]!! }
        .joinToString("")

    enum class GateType { AND, OR, XOR }

    data class Operation(val inputs: List<String>, val type: GateType, val output: String)
}
