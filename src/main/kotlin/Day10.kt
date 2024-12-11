class Day10(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .associate { (point, symbol) -> point to if (symbol == '.') null else symbol.toString().toInt() }

    private val startingPoints = board.filterValues { value -> value == 0 }.keys
    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    fun part1() = startingPoints
        .map { startingPoint -> searchEndPoints(startingPoint) }
        .sumOf { endPoints -> endPoints.toSet().size }

    fun part2() = startingPoints
        .map { startingPoint -> searchEndPoints(startingPoint) }
        .sumOf { endPoints -> endPoints.size }

    private fun searchEndPoints(start: Point) = (1..9)
        .fold(listOf(start)) { points, expectedValue -> calculateNextPoints(points, expectedValue) }

    private fun calculateNextPoints(points: List<Point>, expectedValue: Int) = points
        .flatMap { point ->
            edges.mapNotNull { direction ->
                val neighbour = point + direction
                val neighbourValue = board[neighbour]
                neighbour.takeIf { neighbourValue == expectedValue }
            }
        }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}
