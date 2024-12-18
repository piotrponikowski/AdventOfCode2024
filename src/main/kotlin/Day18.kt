class Day18(input: List<String>, testSpace: Boolean = false) {

    private val points = input.map { line -> line.split(",") }.map { (x, y) -> Point(x.toLong(), y.toLong()) }

    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    private val boardSize = if (testSpace) 6L else 70L
    private val cutSize = if (testSpace) 12 else 1024
    private val cutPoints = points.take(cutSize)

    fun part1() = findPaths(cutPoints.toSet()).minOf { path -> path.stepsCount() }

    fun part2(): String {
        var currentCutSize = points.size

        while (true) {
            val currentCutPoints = points.take(currentCutSize)
            val paths = findPaths(currentCutPoints.toSet())

            if (paths.isNotEmpty()) {
                val resultPoint = points[currentCutSize]
                return "${resultPoint.x},${resultPoint.y}"
            }

            currentCutSize--
        }
    }

    private fun findPaths(blockedPoints: Set<Point>): List<Path> {
        val results = mutableListOf<Path>()
        val visited = mutableMapOf<Point, Int>()

        val startPosition = Point(0, 0)
        val endPosition = Point(boardSize, boardSize)
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
                } else if (!blockedPoints.contains(newPosition) && inBounds(newPosition)) {
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

    private fun inBounds(point: Point) = point.x in 0..boardSize && point.y >= 0 && point.y <= boardSize

    data class Path(val positions: List<Point>) {
        fun stepsCount() = positions.size - 1
        fun currentPosition() = positions.last()
    }

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}