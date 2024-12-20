import kotlin.math.abs

class Day20(input: List<String>) {

    private val board =
        input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val startPosition = board.entries.first { (_, symbol) -> symbol == 'S' }.key
    private val endPosition = board.entries.first { (_, symbol) -> symbol == 'E' }.key

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
    private val accessibleTiles = listOf('.', 'S', 'E')

    fun part1(minSavedDistance: Int = 100) = solve(2, minSavedDistance)

    fun part2(minSavedDistance: Int = 100) = solve(20, minSavedDistance)

    private fun solve(maxCheatDistance: Int, minSavedDistance: Int): Int {
        val distances1 = findDistances(startPosition)
        val distances2 = findDistances(endPosition)

        val initialDistance = distances1[endPosition]!!
        var result = 0

        distances1.forEach { (point1, distance1) ->
            distances2.forEach { (point2, distance2) ->
                val cheatDistance = point1.distance(point2)
                val canCheat = cheatDistance <= maxCheatDistance

                if (canCheat) {
                    val newDistance = distance1 + cheatDistance + distance2
                    val savedDistance = initialDistance - newDistance

                    if (savedDistance >= minSavedDistance) {
                        result++
                    }
                }
            }
        }

        return result
    }

    private fun findDistances(from: Point): Map<Point, Int> {
        val visited = mutableMapOf(from to 0)

        var currentPoints = listOf(from)
        var distance = 1

        while (currentPoints.isNotEmpty()) {
            val nextPoints = mutableListOf<Point>()

            currentPoints.forEach { currentPoint ->
                directions.forEach { direction ->
                    val nextPoint = currentPoint + direction

                    if (board[nextPoint] in accessibleTiles && !visited.containsKey(nextPoint)) {
                        nextPoints += nextPoint
                        visited[nextPoint] = distance
                    }
                }
            }

            currentPoints = nextPoints
            distance++
        }

        return visited
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        fun distance(other: Point) = abs(other.x - x) + abs(other.y - y)
    }
}