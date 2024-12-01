import kotlin.math.abs

class Day1(input: List<String>) {

    private val left = input.map { line -> line.split("   ")[0].toInt() }.sorted()
    private val right = input.map { line -> line.split("   ")[1].toInt() }.sorted()

    fun part1() = (left.indices).sumOf { index -> abs(left[index] - right[index]) }

    fun part2() = left.sumOf { l -> l * right.count { r -> r == l } }
}