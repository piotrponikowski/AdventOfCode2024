class Day6(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val startPosition = board.entries.first { it.value == '^' }.key
    private val startDirection = Direction.U

    fun part1(): Int {
        println(board)
        println(startPosition)

        val visited = mutableSetOf<Point>()

        var position = startPosition
        var direction = startDirection

        while (position in board.keys) {
            visited += position

            val nextPosition = position + direction
            if (board[nextPosition] == '#') {
                direction = direction.rotate()
            } else {
                position = nextPosition
            }
        }

        println(visited)
        return visited.size
    }

    fun part2() = board.entries.filter { it.value == '.' }.count { !canEscape(it.key) }

    fun canEscape(block: Point): Boolean {
        val visited = mutableSetOf<Pair<Point, Direction>>()

        var position = startPosition
        var direction = startDirection

        val newBoard = board.toMutableMap()
        newBoard[block] = '#'

        while (position in newBoard.keys && position to direction !in visited) {
            visited += position to direction

            val nextPosition = position + direction
            if (newBoard[nextPosition] == '#') {
                direction = direction.rotate()
            } else {
                position = nextPosition
            }
        }

        return position !in board.keys
    }


    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1);

        fun rotate() = when (this) {
            U -> R
            R -> D
            D -> L
            L -> U
        }
    }
}