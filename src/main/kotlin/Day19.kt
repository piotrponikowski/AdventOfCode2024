class Day19(input: List<List<String>>) {

    private val towels = input.first().first().split(", ")
    private val designs = input.last()

    fun part1() {
        println(towels)
        println(designs)

        val possibleDesigns = designs.filter { design -> checkDesign(design) }
        println(possibleDesigns)
        println(possibleDesigns.size)
    }

    fun part2() {
        
    }

    private fun checkDesign(design: String): Boolean {
        if (design.isEmpty()) return true

        return towels
            .filter { towel -> design.startsWith(towel) }
            .map { towel -> design.drop(towel.length) }
            .any { newDesign -> checkDesign(newDesign) }
    }

   
}

fun main() {
    val realInput = readGroups("day19.txt")
    val exampleInput = readGroups("day19.txt", true)

    val r1 = Day19(exampleInput).part2()
    println(r1)

//    val r2 = Day19(realInput).part2()
//    println(r2)
}