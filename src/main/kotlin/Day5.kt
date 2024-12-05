class Day5(input: List<List<String>>) {

    private val rules = input.first()
        .map { line -> line.split("|") }
        .map { (rule1, rule2) -> rule1.toInt() to rule2.toInt() }

    private val updates = input.last()
        .map { line -> line.split(",").map { page -> page.toInt() } }

    fun part1() = updates
        .filter { update -> isUpdateCorrect(update) }
        .sumOf { update -> update[update.size / 2] }

    fun part2() = updates
        .filter { update -> !isUpdateCorrect(update) }
        .map { update -> sort(update) }
        .sumOf { update -> update[update.size / 2] }

    private fun sort(update: List<Int>) = update.sortedWith { page1, page2 ->
        when {
            isMatchingAnyRule(page1, page2) -> -1
            isMatchingAnyRule(page2, page1) -> 1
            else -> 0
        }
    }

    private fun isMatchingAnyRule(page1: Int, page2: Int) =
        rules.any { (rule1, rule2) -> rule1 == page1 && rule2 == page2 }

    private fun isUpdateCorrect(update: List<Int>) = rules
        .map { (page1, page2) -> update.indexOf(page1) to update.indexOf(page2) }
        .all { (index1, index2) -> index1 == -1 || index2 == -1 || index1 <= index2 }
}