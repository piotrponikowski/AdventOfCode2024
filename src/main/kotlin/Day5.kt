class Day5(input: List<List<String>>) {

    private val rules = input.first().map { it.split("|") }.map { (a, b) -> a.toInt() to b.toInt() }
    private val updates = input.last().map { it.split(",").map { it.toInt() } }

    fun part1(): Int {
        println(rules)
        println(updates)

        val result = updates.filter { isCorrect(it) }
        println(result)

        val score = result.map { it[it.size / 2] }
        println(score)
        return score.sum()
    }

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

    private fun fix(update: List<Int>, solutions: List<List<Int>> = emptyList(), step: Int = 0): List<Int> {
        return if (step == 0) {
            fix(update, update.map { listOf(it) }, 1)
        } else if (step == update.size) {
            solutions.first()
        } else {
            val newSolutions = solutions.flatMap { s -> update.filter { u -> u !in s }.map { u -> s + u } }
            val checkedSolutions = newSolutions.filter { isCorrect(it) }

            fix(update, checkedSolutions, step + 1)
        }
    }

    private fun isCorrect(update: List<Int>): Boolean {
        val correct = rules.all { rule ->
            val p1 = update.indexOf(rule.first)
            val p2 = update.indexOf(rule.second)
            p1 == -1 || p2 == -1 || p1 <= p2
        }

        return correct
    }
}

fun main() {
    val realInput = readGroups("day5.txt")
    val exampleInput = readGroups("day5.txt", true)

    val r1 = Day5(exampleInput).part2()
    println(r1)

    val r2 = Day5(realInput).part2()
    println(r2)
}