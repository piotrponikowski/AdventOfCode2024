class Day19(input: List<List<String>>) {

    private val towels = input.first().first().split(", ")
    private val designs = input.last()

    fun part1() = designs.count { design -> countDesigns(design) > 0 }

    fun part2() = designs.sumOf { design -> countDesigns(design) }

    private fun countDesigns(design: String): Long {
        val countCache = mutableMapOf("" to 1L)

        fun count(designPart: String): Long = countCache.getOrPut(designPart) {
            towels.filter { towel -> designPart.startsWith(towel) }
                .map { towel -> designPart.removePrefix(towel) }
                .sumOf { newDesign -> count(newDesign) }
        }

        return count(design)
    }
}