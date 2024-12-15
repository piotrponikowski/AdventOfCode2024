import java.lang.IllegalArgumentException

class Day15(input: List<List<String>>) {

    private val board = input.first()
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val modifiedBoard = input.first()
        .flatMapIndexed { y, line -> modifyInput(line).mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val instructions = input.last().flatMap { line -> line.map { symbol -> Direction.valueOf(symbol) } }

    fun part1()  = solve(board)

    fun part2() = solve(modifiedBoard)

    private fun solve(board: Map<Point, Char>) =
        score(instructions.fold(board) { currentBoard, instruction -> move(currentBoard, instruction) })

    private fun move(board: Map<Point, Char>, instruction: Direction): Map<Point, Char> {
        val newState = board.toMutableMap()
        val robotPosition = board.entries.first { it.value == '@' }.key

        val boxes = scanBoxes(board, robotPosition, instruction)
        if (canMoveBoxes(board, boxes, instruction)) {
            boxes.forEach { box -> newState[box] = '.' }
            boxes.forEach { box -> newState[box + instruction] = board[box]!! }
        }

        val nextRobotPosition = robotPosition + instruction
        if (newState[nextRobotPosition] == '.') {
            newState[robotPosition] = '.'
            newState[nextRobotPosition] = '@'
        }

        return newState
    }

    private fun canMoveBoxes(board: Map<Point, Char>, boxes: Set<Point>, instruction: Direction) = boxes
        .all { box -> board[box + instruction] in listOf('.', 'O', '[', ']') }

    private fun scanBoxes(board: Map<Point, Char>, robotPosition: Point, instruction: Direction): Set<Point> {
        val result = mutableSetOf<Point>()
        val pointsToScan = mutableListOf(robotPosition + instruction)

        while (pointsToScan.isNotEmpty()) {
            val point = pointsToScan.removeFirst()
            if (point in result) {
                continue
            }

            val symbol = board[point]
            if (symbol in listOf('O', '[', ']')) {
                result += point

                pointsToScan += point + instruction
                if (symbol == '[') {
                    pointsToScan += point + Direction.R
                } else if (symbol == ']') {
                    pointsToScan += point + Direction.L
                }
            }
        }

        return result
    }

    private fun score(board: Map<Point, Char>) = board
        .filterValues { symbol -> symbol in listOf('O', '[') }
        .map { (point, _) -> point.y * 100 + point.x }
        .sum()

    private fun modifyInput(line: String) = line.flatMap { symbol ->
        when (symbol) {
            '#' -> listOf('#', '#')
            'O' -> listOf('[', ']')
            '.' -> listOf('.', '.')
            '@' -> listOf('@', '.')
            else -> throw IllegalArgumentException()
        }
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val symbol: Char, val x: Int, val y: Int) {
        L('<', -1, 0), R('>', 1, 0), U('^', 0, -1), D('v', 0, 1);

        companion object {
            fun valueOf(symbol: Char) = entries.first { direction -> direction.symbol == symbol }
        }
    }
}