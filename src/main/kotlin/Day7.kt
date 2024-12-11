class Day7(input: List<String>) {

    private val equations = input
        .map { it.split(": ") }
        .map { it.first().toLong() to it.last().split(" ").map { it.toLong() } }
        .map { (a, b) -> Equation(a, b) }

    fun part1() {
        println(equations)
        val e = equations.map { solve(it) to it }.filter { (a, b) -> a != null }.map { it.second.result }.sum()
        println(e)
    }

    fun part2() {
        println(equations)
        val e = equations.map { solve(it) to it }.filter { (a, b) -> a != null }.map { it.second.result }.sum()
        println(e)
    }

    private fun solve(equation: Equation, solutions: List<List<Sign>> = listOf(listOf()), index: Int = 0): List<Sign>? {
        println("Solve $equation, $solutions, $index")
        return if (index < equation.numbers.size - 2) {
            val nextSolutions = solutions
                .flatMap { solution -> Sign.entries.map { sign -> solution + sign } }
                .filter { solution -> calculate(equation, solution) <= equation.result }

            solve(equation, nextSolutions, index + 1)

        } else {
            val nextSolutions = solutions
                .flatMap { solution -> Sign.entries.map { sign -> solution + sign } }
                .filter { solution -> calculate(equation, solution) == equation.result }

            return if (nextSolutions.isNotEmpty()) nextSolutions[0] else null
        }
    }

    private fun calculate(equation: Equation, signs: List<Sign>): Long {
        //println("Calculate $signs")
//        val a = listOf(Sign.MUL, Sign.ADD, Sign.MUL)
//        if(signs == a) {
//            println()
//        }
        
        val start = equation.numbers.first()
        val rest = equation.numbers.drop(1)
        val value = rest.foldIndexed(start) { index, result, number ->
            val sign = if (index < signs.size) signs[index] else null
            when (sign) {
                Sign.ADD -> result + number
                Sign.MUL -> result * number
                Sign.JOIN -> (result.toString() + number.toString()).toLong()
                else -> result
            }
        }

        //println("Calculated $signs, $value ")

        return value
    }

    data class Equation(val result: Long, val numbers: List<Long>)

    enum class Sign {
        ADD, MUL, JOIN
    }
}

fun main() {
    val realInput = readLines("day7.txt")
    val exampleInput = readLines("day7.txt", true)

    val r1 = Day7(exampleInput).part1()
    println(r1)

    val r2 = Day7(realInput).part1()
    println(r2)
}