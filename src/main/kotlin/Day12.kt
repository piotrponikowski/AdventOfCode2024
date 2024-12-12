class Day12(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    fun part1() = gatherAreas().fold(0) { result, area -> result + (computePerimeter(area).size * area.size) }

    fun part2() = gatherAreas().fold(0) { result, area -> result + (computeCorners(area).size * area.size) }

    private fun gatherAreas(): List<Set<Point>> {
        val visited = mutableSetOf<Point>()
        val result = mutableListOf<Set<Point>>()

        board.keys.forEach { point ->
            if (point !in visited) {
                val area = computeArea(point)
                visited += area
                result.add(area)
            }
        }

        return result
    }

    private fun computeArea(startingPoint: Point): Set<Point> {
        val result = mutableSetOf<Point>()
        val pointsToCheck = mutableListOf(startingPoint)

        while (pointsToCheck.isNotEmpty()) {
            val currentPoint = pointsToCheck.removeFirst()
            if (currentPoint !in result && board[startingPoint] == board[currentPoint]) {
                result += currentPoint

                pointsToCheck += Direction.entries
                    .map { edge -> currentPoint + edge }
                    .filter { nextPoint -> nextPoint !in result }
            }
        }

        return result
    }

    private fun computePerimeter(areaPoints: Set<Point>): List<Point> {
        val perimeter = mutableListOf<Point>()

        areaPoints.forEach { point ->
            Direction.entries.forEach { edge ->
                val neighbour = point + edge
                if (board[neighbour] != board[point]) {
                    perimeter += neighbour
                }
            }
        }
        return perimeter
    }

    private fun computeCorners(area: Set<Point>): List<Point> = Direction.neighbours
        .flatMap { (dir1, dir2) -> area.filter { point -> isCorner(point, dir1, dir2) } }

    private fun isCorner(p: Point, dir1: Direction, dir2: Direction) =
        isOpenCorner(p, dir1, dir2) || isClosedCorner(p, dir1, dir2)

    private fun isOpenCorner(p: Point, dir1: Direction, dir2: Direction) =
        board[p + dir1] != board[p] && board[p + dir2] != board[p]

    private fun isClosedCorner(p: Point, dir1: Direction, dir2: Direction) =
        board[p + dir1 + dir2] != board[p] && board[p + dir1] == board[p] && board[p + dir2] == board[p]

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1);

        companion object {
            val neighbours = listOf(U to L, L to D, D to R, R to U)
        }
    }
}