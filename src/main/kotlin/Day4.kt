class Day4(input: List<String>) {

    private val edges = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))
    private val corners = listOf(Point(-1, -1), Point(1, -1), Point(-1, 1), Point(1, 1))
    private val directions = edges + corners
    
    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    fun part1() = board.keys
        .sumOf { point -> directions.count { direction -> checkPhrase(point, direction, "XMAS") } }

    fun part2() = board.keys
        .map { point -> corners.count { direction -> checkPhrase(point - direction, direction, "MAS") } }
        .count { count -> count == 2 }

    private fun checkPhrase(start: Point, direction: Point, phrase: String): Boolean {
        val points = (0..phrase.length - 2).runningFold(start) { point, _ -> point + direction }
        val letters = points.map { point -> board[point] }
        return letters.filterIndexed { index, letter -> letter == phrase[index] }.size == phrase.length
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    }
}