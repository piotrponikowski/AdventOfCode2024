import kotlin.math.abs

class Day12(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x.toLong(), y.toLong()) to symbol } }.toMap()

    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    private val plants = board.values.toSet()
    
    fun part1() {
        //14047374
        //1377008
        
        println(board)
        println(plants)
        var result = 0L

        val visited = mutableSetOf<Point>()

        board.keys.forEach {  startingPoint ->
            if(startingPoint !in visited) {
                val plant = board[startingPoint]
                val perimeter = mutableListOf<Point>()

                val area = areaPoints(startingPoint)
                visited += area
                println("$plant ${area.size}")

                area.forEach { plantPosition ->
                    edges.forEach { edge ->
                        val neighbour = plantPosition + edge
                        if (board[neighbour] != plant) {
                            perimeter += neighbour
                        }
                    }
                }

                result += (area.size * perimeter.size)
            }
        }

        println(result)
    }
    
    private fun areaPoints(start: Point):Set<Point> {
        val plantSymbol = board[start]
        val visited = mutableSetOf<Point>()
        val pointsToCheck = mutableListOf(start)

        
        while (pointsToCheck.isNotEmpty()) {
            val point = pointsToCheck.removeFirst()
            if(plantSymbol == board[point] && point !in visited) {
                visited += point

                pointsToCheck += edges.map { edge -> edge + point }.filter { newPoint -> newPoint !in visited }
            }
        }
        return visited.toSet()
    }

    fun part2() = 2

    private fun calculateArea(points: List<Point>): Long {
        val pairs = points.reversed().windowed(2)

        val sum1 = pairs.sumOf { (curr, next) -> curr.x * next.y }
        val sum2 = pairs.sumOf { (curr, next) -> curr.y * next.x }
        val area = abs(sum2 - sum1) / 2

        val length = pairs.sumOf { (curr, next) -> abs(next.x - curr.x) + abs(next.y - curr.y) }

        return area + length / 2 + 1
    }

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val realInput = readLines("day12.txt")
    val exampleInput = readLines("day12.txt", true)

    val r1 = Day12(exampleInput).part1()
    println(r1)

    val r2 = Day12(realInput).part1()
    println(r2)
    
    //14047374
}