class Day18(input: List<String>, testSpace: Boolean = false) {

    private val points = input.map { it.split(",").map { it.toLong() }.let { (a, b) -> Point(a, b) } }

    private val maxX = if (testSpace) 6L else 70L
    private val maxY = if (testSpace) 6L else 70L
    private val pointsSize = if (testSpace) 12 else 1024

    fun part1() {
        println(points)
        val somePoints=points.take(pointsSize)
        
        println(printMaze(somePoints))
        val result = findPaths(somePoints.toSet())
        println(result.minOf { it.positions.size - 1 })
    }

    fun part2() {
        var cutSize = pointsSize
        while (true) {
            val somePoints=points.take(cutSize)
            val result = findPaths(somePoints.toSet())
            if(result.isEmpty()) {
                val test = somePoints.last()
                println("${test.x},${test.y}")
                break
            }

            cutSize++
        }
    }

    private fun findPaths(points: Set<Point>): List<Path> {
        val results = mutableListOf<Path>()
        val visited = mutableMapOf<Point, Int>()

        val startPosition = Point(0, 0)
        val endPosition = Point(maxX, maxY)
        val startPath = Path(listOf(startPosition))
        val currentPaths = mutableListOf(startPath)

        while (currentPaths.isNotEmpty()) {
            val currentPath = currentPaths.removeFirst()
            val currentPosition = currentPath.positions.last()

            Direction.entries.forEach { newDirection ->
                val newPosition = currentPosition + newDirection
                val newPath = Path(currentPath.positions + newPosition)
                
                if (newPosition == endPosition) {
                    results += newPath
                } else if (!points.contains(newPosition) && inBounds(newPosition)) {
                    val stateScore = visited[newPosition] ?: Int.MAX_VALUE
                    val newScore = newPath.positions.size

                    if (newScore < stateScore) {
                        currentPaths += newPath
                        visited[newPosition] = newScore
                    }
                }
            }
        }

        return results
    }
    
    private fun inBounds(point:Point) = point.x in 0..maxX && point.y >= 0 && point.y <= maxY

    private fun printMaze(points: List<Point>) {
        (0..maxY).forEach { y ->
            (0..maxX).forEach { x ->
                val exists = points.any { it.x == x && it.y == y }
                print(if (exists) '#' else '.')
            }
            println()
        }
    }

    data class Path(val positions: List<Point>)

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1);
    }
}

fun main() {
    val realInput = readLines("day18.txt")
    val exampleInput = readLines("day18.txt", true)

    val r1 = Day18(exampleInput, true).part2()
    println(r1)

    val r2 = Day18(realInput).part2()
    println(r2)
}