class Day7(input: List<String>) {

    private val equations = input
        .map { line -> line.split(": ") }
        .map { (result, numbers) -> result to numbers.split(" ") }
        .map { (result, numbers) -> result.toLong() to numbers.map { number -> number.toLong() } }
        .map { (result, numbers) -> Equation(result, numbers) }

    private val basicOperations = listOf(Operation.ADD, Operation.MULTIPLY)
    private val allOperations = listOf(Operation.ADD, Operation.MULTIPLY, Operation.JOIN)

    fun part1() = equations
        .filter { equation -> canSolve(equation, basicOperations) }
        .sumOf { equation -> equation.result }

    fun part2() = equations
        .filter { equation -> canSolve(equation, allOperations) }
        .sumOf { equation -> equation.result }

    private fun canSolve(equation: Equation, operations: List<Operation>) =
        canSolve(equation, operations, listOf(equation.numbers.first()), 1)

    private fun canSolve(equation: Equation, operations: List<Operation>, results: List<Long>, step: Int): Boolean {
        return if (step < equation.numbers.size) {
            val currentNumber = equation.numbers[step]

            val nextResults = results
                .flatMap { result -> operations.map { operation -> calculate(operation, result, currentNumber) } }
                .filter { result -> result <= equation.result }

            canSolve(equation, operations, nextResults, step + 1)
        } else {
            results.any { result -> result == equation.result }
        }
    }

    private fun calculate(operation: Operation, number1: Long, number2: Long) =
        when (operation) {
            Operation.ADD -> number1 + number2
            Operation.MULTIPLY -> number1 * number2
            Operation.JOIN -> (number1.toString() + number2.toString()).toLong()
        }

    enum class Operation { ADD, MULTIPLY, JOIN }

    data class Equation(val result: Long, val numbers: List<Long>)
}