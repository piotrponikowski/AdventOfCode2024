class Day19(input: List<List<String>>) {

    private val towels = input.first().first().split(", ")
    private val designs = input.last()
    private val designCache = mutableMapOf<String, Long>()

    fun part1() = designs.count { design -> countDesign(design) > 0 }

    fun part2() = designs.sumOf { design -> countDesign(design) }

    private fun countDesign(design: String): Long {
        if (design.isEmpty()) return 1L

        val cachedCount = designCache[design]
        if (cachedCount != null) {
            return cachedCount
        }

        val count = towels
            .filter { towel -> design.startsWith(towel) }
            .map { towel -> design.drop(towel.length) }
            .sumOf { newDesign -> countDesign(newDesign) }

        designCache[design] = count
        return count
    }
}