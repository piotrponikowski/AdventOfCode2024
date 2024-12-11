class Day6(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val obstacles = board.filterValues { symbol -> symbol == '#' }.keys
    private val startingPosition = board.entries.first { (_, symbol) -> symbol == '^' }.key
    private val startingState = State(startingPosition, Direction.U)

    fun part1() = visitedPositions().size

    fun part2() = visitedPositions()
        .filter { possibleObstacle -> possibleObstacle != startingState.position }
        .count { possibleObstacle -> !path(possibleObstacle).second }

    private fun visitedPositions() = path()
        .let { (visitedStates, _) -> visitedStates.map { state -> state.position } }.toSet()

    private fun path(additionalObstacle: Point? = null): Pair<Set<State>, Boolean> {
        val visitedStates = mutableSetOf<State>()
        var currentState = startingState

        while (currentState.position in board.keys && currentState !in visitedStates) {
            visitedStates += currentState

            var nextState = currentState.move()
            if (nextState.position in obstacles || nextState.position == additionalObstacle) {
                nextState = currentState.rotate()
            }

            currentState = nextState
        }

        return visitedStates to (currentState.position !in board.keys)
    }

    data class State(val position: Point, val direction: Direction) {
        fun rotate() = State(position, direction.rotate())
        fun move() = State(position + direction, direction)
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