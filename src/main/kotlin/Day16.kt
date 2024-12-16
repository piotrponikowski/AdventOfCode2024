class Day16(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    fun part1() = findPaths().minOfOrNull { path -> path.score }

    fun part2() = findPaths()
        .let { paths -> paths to paths.minOfOrNull { path -> path.score } }
        .let { (paths, minScore) -> paths.filter { path -> path.score == minScore } }
        .flatMap { path -> path.positions }.toSet().size

    private fun findPaths(): List<Path> {
        val results = mutableListOf<Path>()
        val visited = mutableMapOf<State, Int>()

        val startPosition = board.entries.first { (_, symbol) -> symbol == 'S' }.key
        val startPath = Path(listOf(startPosition), Direction.R, 0)
        val currentPaths = mutableListOf(startPath)

        while (currentPaths.isNotEmpty()) {
            val currentPath = currentPaths.removeFirst()
            val currentPosition = currentPath.positions.last()
            val directions = Direction.rotations(currentPath.direction)

            directions.forEach { newDirection ->
                val newPosition = currentPosition + newDirection
                val newScore = currentPath.score + if (currentPath.direction == newDirection) 1 else 1001
                val newPath = Path(currentPath.positions + newPosition, newDirection, newScore)
                
                val nextTile = board[newPosition]
                if (nextTile == '.') {
                    val stateKey = State(newPosition, newDirection)
                    val stateScore = visited[stateKey] ?: Int.MAX_VALUE

                    if (newScore <= stateScore) {
                        currentPaths += newPath
                        visited[stateKey] = newScore
                    }
                } else if (nextTile == 'E') {
                    results += newPath
                }
            }
        }

        return results
    }

    data class Path(val positions: List<Point>, val direction: Direction, val score: Int)

    data class State(val position: Point, val direction: Direction)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1);

        companion object {
            fun rotations(direction: Direction) = when (direction) {
                R -> listOf(R, D, U)
                L -> listOf(L, D, U)
                U -> listOf(U, L, R)
                D -> listOf(D, L, R)
            }
        }
    }
}