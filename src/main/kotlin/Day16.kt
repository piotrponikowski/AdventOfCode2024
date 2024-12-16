class Day16(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val rotations = mapOf(
        Direction.R to listOf(Direction.R, Direction.D, Direction.U),
        Direction.L to listOf(Direction.L, Direction.D, Direction.U),
        Direction.U to listOf(Direction.U, Direction.L, Direction.R),
        Direction.D to listOf(Direction.D, Direction.L, Direction.R)
    )
    
    fun part1() = solve().minOfOrNull { it.score }

    fun part2() = solve().let {results-> 
        val bestScore = results.map { it.score }.min()
        results.filter { it.score == bestScore }.flatMap { it.states.map { it.position } }.toSet().size
    }

    private fun solve():List<Path> {

        val start = board.entries.first { it.value == 'S' }.key
        val end = board.entries.first { it.value == 'E' }.key

        val startStates = listOf(Path(listOf(State(start, Direction.R)), 0, listOf(Debug(start, Direction.R, 0))))

        var currentPaths = startStates
        val result = mutableListOf<Path>()
        val visited = mutableMapOf<State, Int>()

        while (currentPaths.isNotEmpty()) {
            val nextPaths = mutableListOf<Path>()

            currentPaths.forEach { currentPath ->
                val state = currentPath.states.last()

                rotations[state.direction]!!.forEach { newDirection ->
                    val newPosition = state.position + newDirection
                    val newScore = currentPath.score + (if (state.direction == newDirection) 1 else 1001)
                    val newState = State(newPosition, newDirection)
                    val newDebug = Debug(newPosition, newDirection, newScore)
                    val newPath = Path(currentPath.states + newState, newScore, currentPath.debug + newDebug)

                    val validEnd = board[newPosition] == 'E'
                    val validPosition = board[newPosition] == '.'
                    val validPath = newState !in currentPath.states
                    val validScore = newScore <= (visited[newState]?: Int.MAX_VALUE)

                    
                    if (validEnd) {
                        result += newPath
                    } else if (validPosition && validPath && validScore) {
                        nextPaths += newPath
                        visited[newState] = newScore
                    }
                }
            }

            currentPaths = nextPaths.toList()
        }

        return result
    }

    private fun printPath(path: Path) {
        val minX = board.minOf { it.key.x }
        val maxX = board.maxOf { it.key.x }
        val minY = board.minOf { it.key.y }
        val maxY = board.maxOf { it.key.y }

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                val point = Point(x, y)
                val hasPoint = path.states.any { it.position == point }

                print(if (hasPoint) 'P' else board[point])
            }
            println()
        }
    }

    data class Path(val states: List<State>, val score: Int, val debug:List<Debug>)

    data class State(val position: Point, val direction: Direction)

    data class Debug(val position: Point, val direction: Direction, val score:Int)


    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val symbol: Char, val x: Int, val y: Int) {
        L('<', -1, 0), R('>', 1, 0), U('^', 0, -1), D('v', 0, 1);
    }
}

fun main() {
    val realInput = readLines("day16.txt")
    val exampleInput = readLines("day16-1.txt", true)

    val r1 = Day16(exampleInput).part1()
    println(r1)

    val r2 = Day16(realInput).part1()
    println(r2)
}