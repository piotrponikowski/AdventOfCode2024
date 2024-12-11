class Day10(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .associate { (point, symbol) -> point to if(symbol == '.') null else symbol.toString().toInt() }

    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
    
    fun part1() {
        println(board)
        
        val starts = board.filterValues { it == 0 }.keys
        println(starts)
        
        val result = starts.map { score(it) }.sum()
        println(result)
    }
    
    private fun score(start: Point):Int {
        var points = listOf(start)

        (1..9).forEach { value ->
            val nextPoints = mutableListOf<Point>()
            
            points.forEach { point ->
                edges.forEach { direction ->
                    val neighbour = point + direction
                    val edgeValue = board[neighbour]
                    if(edgeValue == value) {
                        nextPoints += neighbour
                    }
                }
            }
            
            points = nextPoints
        }
        
        return points.toSet().size
    }

    fun part2() = 2

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val realInput = readLines("day10.txt")
    val exampleInput = readLines("day10.txt", true)

    val r1 = Day10(exampleInput).part1()
    println(r1)

    val r2 = Day10(realInput).part1()
    println(r2)
}