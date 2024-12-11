class Day10(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .associate { (point, symbol) -> point to if (symbol == '.') null else symbol.toString().toInt() }

    private val startingPoints = board.filterValues { value -> value == 0 }.keys

    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
    
    fun part1() = startingPoints
        .map { startingPoint -> paths(startingPoint) }
        .sumOf { endPoints -> endPoints.toSet().size }

    fun part2() = startingPoints
        .map { startingPoint -> paths(startingPoint) }
        .sumOf { endPoints -> endPoints.size }

    private fun paths(start: Point): List<Point> {
        var currentPoints = listOf(start)

        (1..9).forEach { value ->
            val nextPoints = mutableListOf<Point>()

            currentPoints.forEach { point ->
                edges.forEach { direction ->
                    val neighbour = point + direction
                    val edgeValue = board[neighbour]
                    if (edgeValue == value) {
                        nextPoints += neighbour
                    }
                }
            }

            currentPoints = nextPoints
        }

        return currentPoints
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val realInput = readLines("day10.txt")
    val exampleInput = readLines("day10.txt", true)

    val r1 = Day10(exampleInput).part2()
    println(r1)

    val r2 = Day10(realInput).part2()
    println(r2)
}