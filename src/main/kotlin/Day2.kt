class Day2(input: List<String>) {
    
    private val reports = input.map { a -> a.split(" ").map { b -> b.toInt() } }
    
    fun part1() {
        println(reports)
        
        val result = reports.map { isIncreasing(it) || isDecreasing(it) }.filter { it }.count()
        println(result)
    }

    fun part2() {
        println(reports)
        println(split(reports[0]))

        val result = reports.map { split(it) }.map { l -> l.any {  isIncreasing(it) || isDecreasing(it) }}.filter { it }.count()
        println(result) 
    }
    
    private fun isIncreasing(numbers: List<Int>) = numbers.windowed(2).all { (a, b) -> b > a && b-a in 1..3 }

    private fun isDecreasing(numbers: List<Int>) = numbers.windowed(2).all { (a, b) -> a > b && a-b in 1..3 }

    private fun split(numbers: List<Int>) = numbers.indices.map { index -> numbers.filterIndexed { index2, i -> index != index2 } }
}

fun main() {
    val realInput = readLines("day2.txt")
    val exampleInput = readLines("day2.txt", true)

    val r1 = Day2(exampleInput).part2()
    println(r1)

    val r2 = Day2(realInput).part2()
    println(r2)
}