class Day21(private val codes: List<String>) {

    private val numericKeypad = parseKeypad(listOf("789", "456", "123", " 0A"))
    private val directionalKeypad = parseKeypad(listOf(" ^A", "<v>"))

    private val directions = mapOf(
        Point(-1, 0) to '<',
        Point(1, 0) to '>',
        Point(0, -1) to '^',
        Point(0, 1) to 'v'
    )

    fun part1() = codes.sumOf { code -> complexity(code, 2) }

    fun part2() = codes.sumOf { code -> complexity(code, 25) }

    private fun complexity(code: String, maxDepth: Int) = solve(code, maxDepth) * code.dropLast(1).toLong()

    private fun solve(code: String, maxDepth: Int): Long {
        val minCountCache = mutableMapOf<Pair<List<Char>, Int>, Long>()

        val numericCache = prepareKeyboardCache(numericKeypad)
        val directionalCache = prepareKeyboardCache(directionalKeypad)

        fun minCount(code: List<Char>, maxDepth: Int, depth: Int): Long = minCountCache.getOrPut(code to depth) {
            val keyboardCache = if (depth == 0) numericCache else directionalCache
            val codeWithStart = (listOf('A') + code)

            codeWithStart.windowed(2).fold(0L) { result, (from, to) ->
                val possibleMoves = keyboardCache[from to to]!!
                when (depth == maxDepth) {
                    true -> result + possibleMoves.minOf { moves -> moves.size }
                    false -> result + possibleMoves.minOf { moves -> minCount(moves, maxDepth, depth + 1) }
                }
            }
        }

        return minCount(code.toList(), maxDepth, 0)
    }

    private fun prepareKeyboardCache(keypad: Map<Point, Char>) = keypad.values
        .flatMap { from -> keypad.values.map { to -> from to to } }
        .associate { (from, to) -> Pair(from, to) to findMoves(keypad, from, to) }

    private fun findMoves(keypad: Map<Point, Char>, from: Char, to: Char): List<List<Char>> {
        val startPosition = keypad.entries.first { (_, symbol) -> symbol == from }.key
        val endPosition = keypad.entries.first { (_, symbol) -> symbol == to }.key

        val resultPaths = mutableListOf<Path>()
        val currentPaths = mutableListOf(Path(startPosition, emptyList()))
        val visited = mutableMapOf(startPosition to 0)

        while (currentPaths.isNotEmpty()) {
            val currentPath = currentPaths.removeFirst()

            if (currentPath.position == endPosition) {
                resultPaths += currentPath
                continue
            }

            directions.forEach { (direction, symbol) ->
                val newPosition = currentPath.position + direction
                val newPath = Path(newPosition, currentPath.moves + symbol)

                if (keypad[newPosition] != null) {
                    val newScore = newPath.moves.size
                    val stateScore = visited[newPosition] ?: Int.MAX_VALUE

                    if (newScore <= stateScore) {
                        currentPaths += newPath
                        visited[newPosition] = newScore
                    }
                }
            }
        }

        return resultPaths.map { path -> path.moves + 'A' }
    }

    private fun parseKeypad(input: List<String>) = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .associate { (point, symbol) -> point to symbol }
        .filterValues { symbol -> symbol != ' ' }

    data class Path(val position: Point, val moves: List<Char>)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}