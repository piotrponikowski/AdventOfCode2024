class Day3(private val input: String) {

    private val pattern = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")

    fun part1() = pattern.findAll(input)
        .filter { group -> group.value.startsWith("mul") }
        .map { group -> computeGroup(group) }
        .sum()

    fun part2() = pattern.findAll(input)
        .fold(0 to true) { (result, enabled), group ->
            when (group.value) {
                "do()" -> result to true
                "don't()" -> result to false
                else -> when (enabled) {
                    true -> result + computeGroup(group) to true
                    false -> result to false
                }
            }
        }.let { (result, _) -> result }

    private fun computeGroup(match: MatchResult) = match.destructured
        .let { (value1, value2) -> value1.toInt() * value2.toInt() }
}