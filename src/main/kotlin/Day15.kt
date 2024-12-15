class Day15(input: List<List<String>>) {

    private val board = input.first()
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val board2 = input.first()
        .flatMapIndexed { y, line -> modifyLine(line).mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val instructions = input.last().flatMap { line -> line.toCharArray().map { parseDirection(it) } }

    fun part1() {
        printBoard(board)
        //println(instructions)

        var currentBoard = board
        var index = 0
        instructions.forEach { instruction ->
            currentBoard = move(currentBoard, instruction)
            //printBoard(currentBoard)

            index++
            //println(index)
        }

        println(score(currentBoard))
    }

    fun part2() {
        //printBoard(board2)
        //println(instructions)


        var currentBoard = board2
        var index = 0
        printBoard(currentBoard)

        instructions.forEach { instruction ->
            currentBoard = move2(currentBoard, instruction)
            //printBoard(currentBoard)

            index++
            //println(index)
        }

        println(score2(currentBoard))
    }

    private fun modifyLine(line: String) = line.toCharArray().flatMap {
        when (it) {
            '#' -> listOf('#', '#')
            'O' -> listOf('[', ']')
            '.' -> listOf('.', '.')
            '@' -> listOf('@', '.')
            else -> listOf()
        }
    }

    private fun move2(board: Map<Point, Char>, instruction: Direction): Map<Point, Char> {
        val newState = board.toMutableMap()

        val robotPosition = board.entries.first { it.value == '@' }.key

        val nextRobotPoint = robotPosition + instruction

        var boxPoints = mutableSetOf<Point>()
        if (board[nextRobotPoint] in listOf('[', ']')) {
            boxPoints += nextRobotPoint
        }

        while (true) {
            val nextBoxPoints = mutableSetOf<Point>()

            boxPoints.forEach { boxPoint ->
                val symbol = board[boxPoint]
                if (symbol == '[') {
                    nextBoxPoints += boxPoint
                    nextBoxPoints += (boxPoint + Direction.R)
                } else if (symbol == ']') {
                    nextBoxPoints += boxPoint
                    nextBoxPoints += (boxPoint + Direction.L)
                }
            }

            nextBoxPoints += boxPoints.map { it + instruction }.filter { board[it] in listOf('[', ']') }


            if (nextBoxPoints == boxPoints) {
                break
            } else {
                boxPoints = nextBoxPoints
            }
        }
        val boxes = boxPoints.map { it to board[it]!! }
        val canMoveBoxes = boxes.all { board[it.first+instruction] in listOf('.', '[', ']') }
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

    private fun move(board: Map<Point, Char>, instruction: Direction): Map<Point, Char> {
        val newState = board.toMutableMap()

        val robotPosition = board.entries.first { it.value == '@' }.key
        val pointsToMove = mutableListOf<Point>()

        var currentPoint = robotPosition
        while (board[currentPoint] in listOf('@', 'O')) {
            pointsToMove += currentPoint

            currentPoint += instruction
        }

        if (board[currentPoint] == '.') {
            val newPoints = pointsToMove.map { it + instruction }
            val newRobot = newPoints.first()
            val newBoxes = newPoints.drop(1)

            pointsToMove.forEach { point -> newState[point] = '.' }
            newState[newRobot] = '@'
            newBoxes.forEach { point -> newState[point] = 'O' }

        }

        return newState
    }

    private fun score(board: Map<Point, Char>) = board
        .filter { it.value == 'O' }
        .map { it.key.y * 100 + it.key.x }
        .sum()

    private fun score2(board: Map<Point, Char>) = board
        .filter { it.value == '[' }
        .map { it.key.y * 100 + it.key.x }
        .sum()

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


    private fun parseDirection(symbol: Char): Direction {
        if (symbol == '^') return Direction.U
        if (symbol == 'v') return Direction.D
        if (symbol == '>') return Direction.R
        if (symbol == '<') return Direction.L
        throw IllegalArgumentException()
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1);
    }
}

fun main() {
    val realInput = readGroups("day15.txt")
    val exampleInput = readGroups("day15.txt", true)

    val r1 = Day15(exampleInput).part2()
    println(r1)

    val r2 = Day15(realInput).part2()
    println(r2)

    //1420868
    //9292
    //1421171
    //1412971
}