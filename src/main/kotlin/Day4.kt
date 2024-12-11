class Day4(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    fun part1() = board.keys
        .sumOf { point -> Direction.entries.count { direction -> checkPhrase(point, direction, "XMAS") } }

    fun part2() = board.keys
        .map { point -> Direction.corners().count { direction -> checkPhrase(point - direction, direction, "MAS") } }
        .count { count -> count == 2 }

    private fun checkPhrase(start: Point, direction: Direction, phrase: String): Boolean {
        val points = (0..phrase.length - 2).runningFold(start) { point, _ -> point + direction }
        val letters = points.map { point -> board[point] }
        return letters.filterIndexed { index, letter -> letter == phrase[index] }.size == phrase.length
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
        operator fun minus(other: Direction) = Point(x - other.x, y - other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1),
        LU(-1, -1), LD(-1, 1), RU(1, -1), RD(1, 1);

        companion object {
            fun corners() = listOf(LU, LD, RU, RD)
        }
    }
}