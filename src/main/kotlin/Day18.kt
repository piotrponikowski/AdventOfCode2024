class Day18(input: List<String>, testSpace: Boolean = false) {

    private val points = input.map { it.split(",").map { it.toLong() }.let { (a, b) -> Point(a, b) } }
    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    
    
    private val maxX = if (testSpace) 6L else 70L
    private val maxY = if (testSpace) 6L else 70L

    private val pointsSize = if (testSpace) 12 else 1024
    private val firstPoints = points.take(pointsSize)

    fun part1() = findPaths(firstPoints.toSet()).minOf { path -> path.stepsCount() }

    fun part2(): String {
        var cutSize = pointsSize

        while (true) {
            val simPoints = points.take(cutSize)
            val result = findPaths(simPoints.toSet())
            if (result.isEmpty()) {
                val lastFallenPoint = simPoints.last()
                return "${lastFallenPoint.x},${lastFallenPoint.y}"
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
            val currentPosition = currentPath.currentPosition()

            edges.forEach { newDirection ->
                val newPosition = currentPosition + newDirection
                val newPath = Path(currentPath.positions + newPosition)

                if (newPosition == endPosition) {
                    results += newPath
                } else if (!points.contains(newPosition) && inBounds(newPosition)) {
                    val stateScore = visited[newPosition] ?: Int.MAX_VALUE
                    val newScore = newPath.positions.size

                    if (newScore <= stateScore) {
                        currentPaths += newPath
                        visited[newPosition] = newScore
                    }
                }
            }
        }

        return results
    }

    private fun inBounds(point: Point) = point.x in 0..maxX && point.y >= 0 && point.y <= maxY

    data class Path(val positions: List<Point>) {
        fun stepsCount() = positions.size - 1
        fun currentPosition() = positions.last()
    }

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
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