class Day24(input: List<List<String>>) {

    private val inputs = input.first()
        .map { line -> line.split(": ") }
        .associate { (input, value) -> input to value.toInt() }

    private val gates = input.last()
        .map { line -> line.split(" -> ") }
        .map { (operation, output) -> operation.split(" ") + output }
        .map { (input1, type, input2, output) -> Operation(listOf(input1, input2), GateType.valueOf(type), output) }

    fun part1() = calculate()

    fun part2() = swappedWires()

    private fun swappedWires(): String {
        val and1 = gates
            .filter { gate -> gate.type == GateType.AND }
            .filter { gate -> gate.inputs.map { input -> input.take(1) }.containsAll(listOf("x", "y")) }

        val xor1 = gates
            .filter { gate -> gate.type == GateType.XOR }
            .filter { gate -> gate.inputs.map { input -> input.take(1) }.containsAll(listOf("x", "y")) }

        val and2 = gates
            .filter { gate -> gate.type == GateType.AND }
            .filter { gate -> gate !in and1 }

        val xor2 = gates
            .filter { gate -> gate.type == GateType.XOR }
            .filter { gate -> gate !in xor1 }

        val or = gates
            .filter { gate -> gate.type == GateType.OR }
        
        val and2Inputs = and2.flatMap { gate -> gate.inputs }.toSet()
        val xor2Inputs = xor2.flatMap { gate -> gate.inputs }.toSet()
        val orInputs = or.flatMap { gate -> gate.inputs }.toSet()

        // 1st level AND should output only to OR (except first gate)
        val condition1 = and1
            .filter { gate -> !gate.inputs.all { input -> input.endsWith("00") } }
            .map { gate -> gate.output }
            .filter { output -> output !in orInputs }

        // 2nd level AND should output only to OR
        val condition2 = and2
            .map { gate -> gate.output }
            .filter { output -> output !in orInputs }

        // 1st level XOR should output only to 2nd level XOR/AND (except first gate)
        val condition3 = xor1
            .map { gate -> gate.output }
            .filter { output -> !output.endsWith("00") }
            .filter { output -> output !in xor2Inputs || output !in and2Inputs }

        // 2nd level AND should output only to z
        val condition4 = xor2
            .map { gate -> gate.output }
            .filter { output -> !output.startsWith("z") }

        // OR should output only to 2nd level XOR (except last gate)
        val condition5 = or
            .map { gate -> gate.output }
            .filter { output -> !output.endsWith("45") }
            .filter { output -> output !in xor2Inputs }

        val allConditions = (condition1 + condition2 + condition3 + condition4 + condition5)
        return allConditions.toSet().toSortedSet().joinToString(",")
    }

    private fun calculate(): Long {
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

            unsolvedGates -= solvedGates
        }

        return decodeValue(values)
    }

    private fun decodeValue(values: Map<String, Int>, prefix: String = "z") = values.keys
        .filter { output -> output.startsWith(prefix) }
        .sorted().reversed()
        .map { output -> values[output]!! }
        .joinToString("").toLong(2)

    enum class GateType { AND, OR, XOR }

    data class Operation(val inputs: List<String>, val type: GateType, val output: String)
}