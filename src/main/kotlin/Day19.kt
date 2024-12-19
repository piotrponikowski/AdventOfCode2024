class Day19(input: List<List<String>>) {

    private val towels = input.first().first().split(", ")
    private val designs = input.last()
    private val designCache = mutableMapOf<String, Long>()

    fun part1() = designs.count { design -> countDesigns(design) > 0 }

    fun part2() = designs.sumOf { design -> countDesigns(design) }

    private fun countDesigns(design: String): Long {
        if (design.isEmpty()) return 1L

        val cachedCount = designCache[design]
        if (cachedCount != null) {
            return cachedCount
        }

        val count = towels
            .filter { towel -> design.startsWith(towel) }
            .map { towel -> design.drop(towel.length) }
            .sumOf { newDesign -> countDesigns(newDesign) }

        designCache[design] = count
        return count
    }
}