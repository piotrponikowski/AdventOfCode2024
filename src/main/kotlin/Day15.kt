import java.lang.IllegalArgumentException

class Day15(input: List<List<String>>) {

    private val board = input.first()
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val modifiedBoard = input.first()
        .flatMapIndexed { y, line -> modifyInput(line).mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val instructions = input.last().flatMap { line -> line.map { symbol -> Direction.valueOf(symbol) } }

    fun part1() = 2

    fun part2(): Int {
        //printBoard(board2)
        //println(instructions)


        var currentBoard = modifiedBoard

        //printBoard(currentBoard)

        instructions.forEach { instruction ->
            currentBoard = move2(currentBoard, instruction)
            //printBoard(currentBoard)
            //println(index)
        }

        return score(currentBoard)
    }


    private fun move2(board: Map<Point, Char>, instruction: Direction): Map<Point, Char> {
        val newState = board.toMutableMap()

        val robotPosition = board.entries.first { it.value == '@' }.key

        val nextRobotPoint = robotPosition + instruction

        val boxPoints = scanBoxes(board, robotPosition, instruction)
        val boxes = boxPoints.map { it to board[it]!! }
        val canMoveBoxes = boxes.all { board[it.first + instruction] in listOf('.', '[', ']') }
        if (canMoveBoxes) {
            boxes.forEach {
                newState[it.first] = '.'
            }
            boxes.forEach {
                newState[it.first + instruction] = it.second
            }
        }

        if (newState[nextRobotPoint] == '.') {
            newState[robotPosition] = '.'
            newState[nextRobotPoint] = '@'
        }

        return newState
    }

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

    private fun printBoard(board: Map<Point, Char>) {
        val minX = board.minOf { it.key.x }
        val maxX = board.maxOf { it.key.x }
        val minY = board.minOf { it.key.y }
        val maxY = board.maxOf { it.key.y }

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                print(board[Point(x, y)])
            }
            println()
        }
        println()
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