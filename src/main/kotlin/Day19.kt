class Day19(input: List<List<String>>) {

    private val towels = input.first().first().split(", ")
    private val designs = input.last()
    private val cache = mutableMapOf<String, Long>()

    fun part1() = designs.count { design -> checkDesign(design) }

    fun part2() = designs.sumOf { design -> countDesign(design) }


    private fun countDesign(design: String): Long {
        if (design.isEmpty()) return 1L

        val cachedCount = cache[design]
        if(cachedCount != null) {
            return cachedCount
        }

        val result = towels
            .filter { towel -> design.startsWith(towel) }
            .map { towel -> design.drop(towel.length) }
            .sumOf { newDesign -> countDesign(newDesign) }
        
        cache[design] = result
        return result
    }

    private fun checkDesign(design: String): Boolean {
        if (design.isEmpty()) return true

        return towels
            .filter { towel -> design.startsWith(towel) }
            .map { towel -> design.drop(towel.length) }
            .any { newDesign -> checkDesign(newDesign) }
    }
}