class Day5(input: List<List<String>>) {

    private val rules = input.first()
        .map { line -> line.split("|") }
        .map { (page1, page2) -> page1.toInt() to page2.toInt() }
    
    private val updates = input.last()
        .map { line -> line.split(",").map { page -> page.toInt() } }

    fun part1() = updates
        .filter { update -> isCorrect(update) }
        .sumOf { update -> update[update.size / 2] }

    fun part2(): Int {
        println(rules)
        println(updates)

        val incorrect = updates.filter { !isCorrect(it) }
        println(incorrect)

        val corrected = incorrect.map {
            println("Fixing $it")
            fix2(it)
        }
        println(corrected)

        val score = corrected.map { it[it.size / 2] }
        println(score)
        return score.sum()
    }

    private fun fix2(update: List<Int>): List<Int> {
        val comparator = Comparator { u1: Int, u2: Int ->
            if (rules.any { (r1, r2) -> r1 == u1 && r2 == u2 }) {
                1
            } else if (rules.any { (r1, r2) -> r1 == u2 && r2 == u1 }) {
                -1
            } else {
                0
            }
        }

        return update.sortedWith(comparator)
    }

    private fun isCorrect(update: List<Int>) = rules
        .map { (page1, page2) -> update.indexOf(page1) to update.indexOf(page2) }
        .all { (index1, index2) -> index1 == -1 || index2 == -1 || index1 <= index2 }
}

fun main() {
    val realInput = readGroups("day5.txt")
    val exampleInput = readGroups("day5.txt", true)

    val r1 = Day5(exampleInput).part2()
    println(r1)

    val r2 = Day5(realInput).part2()
    println(r2)
}