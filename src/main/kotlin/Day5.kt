class Day5(input: List<List<String>>) {

    private val rules = input.first()
        .map { line -> line.split("|") }
        .map { (page1, page2) -> page1.toInt() to page2.toInt() }

    private val updates = input.last()
        .map { line -> line.split(",").map { page -> page.toInt() } }

    private val sortedUpdates = updates.map { update -> sort(update) }

    fun part1() = sortedUpdates
        .filter { update -> update in updates }
        .sumOf { update -> update[update.size / 2] }

    fun part2() = sortedUpdates
        .filter { update -> update !in updates }
        .sumOf { update -> update[update.size / 2] }

    private fun sort(update: List<Int>) = update.sortedWith { page1, page2 ->
        when {
            arePagesMatchingAnyRule(page1, page2) -> -1
            arePagesMatchingAnyRule(page2, page1) -> 1
            else -> 0
        }
    }

    private fun arePagesMatchingAnyRule(page1: Int, page2: Int) =
        rules.any { (rulePage1, rulePage2) -> rulePage1 == page1 && rulePage2 == page2 }
}