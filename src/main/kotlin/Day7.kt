class Day7(input: List<String>) {

    private val equations = input
        .map { line -> line.split(": ") }
        .map { (expectedResult, numbers) -> expectedResult to numbers.split(" ") }
        .map { (expectedResult, numbers) -> expectedResult.toLong() to numbers.map { number -> number.toLong() } }
        .map { (expectedResult, numbers) -> Equation(expectedResult, numbers) }

    fun part1() = equations
        .filter { equation -> canSolve(equation, listOf(::plus, ::times)) }
        .sumOf { equation -> equation.expectedResult }

    fun part2() = equations
        .filter { equation -> canSolve(equation, listOf(::plus, ::times, ::join)) }
        .sumOf { equation -> equation.expectedResult }

    private fun canSolve(
        equation: Equation,
        operations: List<(Long, Long) -> Long>,
        results: List<Long> = emptyList(),
        index: Int = 0
    ): Boolean {
        return if (index == 0) {
            canSolve(equation, operations, results + equation.numbers.first(), 1)
        } else if (index < equation.numbers.size) {
            val currentNumber = equation.numbers[index]

            val nextResults = results
                .flatMap { result -> operations.map { operation -> operation.invoke(result, currentNumber) } }
                .filter { result -> result <= equation.expectedResult }

            canSolve(equation, operations, nextResults, index + 1)
        } else {
            results.any { result -> result == equation.expectedResult }
        }
    }

    data class Equation(val expectedResult: Long, val numbers: List<Long>)

    private fun plus(number1: Long, number2: Long) = number1 + number2

    private fun times(number1: Long, number2: Long) = number1 * number2

    private fun join(number1: Long, number2: Long) = (number1.toString() + number2.toString()).toLong()
}