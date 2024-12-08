class Day8(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val antennas = board
        .filterValues { symbol -> symbol != '.' }
        .map { (position, frequency) -> Antenna(position, frequency) }

    fun part1() = solve(::nextPosition)

    fun part2() = solve(::allPositions)

    private fun solve(searchMethod: (Point, Point) -> List<Point>) = antennas
        .groupBy { antenna -> antenna.frequency }.values
        .map { group -> group.map { antenna -> antenna.position } }
        .flatMap { positions -> findAntiNodes(positions, searchMethod) }
        .toSet().size

    private fun findAntiNodes(positions: List<Point>, searchMethod: (Point, Point) -> List<Point>) =
        positions.flatMapIndexed { index1, position1 ->
            positions.flatMapIndexed { index2, position2 ->
                if (index2 > index1) {
                    searchMethod(position1, position2) + searchMethod(position2, position1)
                } else {
                    emptyList()
                }
            }
        }

    private fun nextPosition(position1: Point, position2: Point): List<Point> {
        val nextPosition = position1 + (position1 - position2)
        return if (nextPosition in board.keys) listOf(nextPosition) else emptyList()
    }

    private fun allPositions(position1: Point, position2: Point): List<Point> {
        var currentPosition = position1
        val positions = mutableListOf<Point>()

        while (currentPosition in board.keys) {
            positions += currentPosition
            currentPosition += (position1 - position2)
        }

        return positions
    }

    data class Antenna(val position: Point, val frequency: Char)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    }
}