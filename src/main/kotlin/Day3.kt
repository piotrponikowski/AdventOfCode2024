class Day3(val input: String) {

    private val PATTERN = Regex("""mul\((\d+),(\d+)\)""")
    private val PATTERN2 = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")

    fun part1(): Int {
        println(input)
        val result = PATTERN.findAll(input).map { it.destructured }.map { (a, b) -> a.toInt() * b.toInt() }.sum()
        return result
    }

    fun part2(): Int {
        val instructions = PATTERN2.findAll(input).map { it.value }.toList()
        var result = 0
        var enabled = true
        instructions.forEach { i ->
            if (i == "do()") {
                enabled = true
            } else if (i == "don't()") {
                enabled = false
            } else if (enabled) {
                println(i)
                val (a, b) = PATTERN.matchEntire(i)!!.destructured
                result += (a.toInt() * b.toInt())
            }
        }
        return result
    }
}