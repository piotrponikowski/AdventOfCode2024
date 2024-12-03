import kotlin.math.abs

class Day1(input: List<String>) {

    private val leftNumbers = input.map { line -> line.split("   ").first().toInt() }.sorted()
    private val rightNumbers = input.map { line -> line.split("   ").last().toInt() }.sorted()

    fun part1() = leftNumbers.zip(rightNumbers).sumOf { (left, right) -> abs(left - right) }

    fun part2() = leftNumbers.sumOf { left -> left * rightNumbers.count { right -> right == left } }
}