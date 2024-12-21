class Day21(private val codes: List<String>) {

    private val numericKeypad = mapOf(
        Point(0, 0) to '7',
        Point(1, 0) to '8',
        Point(2, 0) to '9',
        Point(0, 1) to '4',
        Point(1, 1) to '5',
        Point(2, 1) to '6',
        Point(0, 2) to '1',
        Point(1, 2) to '2',
        Point(2, 2) to '3',
        Point(1, 3) to '0',
        Point(2, 3) to 'A',
    )

    private val directionalKeypad = mapOf(
        Point(1, 0) to '^',
        Point(2, 0) to 'A',
        Point(0, 1) to '<',
        Point(1, 1) to 'v',
        Point(2, 1) to '>',
    )

    private val directions = mapOf(
        Point(-1, 0) to '<',
        Point(1, 0) to '>',
        Point(0, -1) to '^',
        Point(0, 1) to 'v'
    )

    private val numericCache = prepareCache(numericKeypad)
    private val directionalCache = prepareCache(directionalKeypad)

    fun part1() = codes.sumOf { code -> complexity(code, 2) }

    fun part2() = codes.sumOf { code -> complexity(code, 25) }


    private val cache = mutableMapOf<Pair<List<Char>, Int>, Long>()

    private fun complexity(code:String, maxDepth: Int) = minMoves(code.toList(), maxDepth) * code.dropLast(1).toLong()
    
    private fun minMoves(code: List<Char>, maxDepth: Int, depth: Int = 0): Long {
        val cacheKey = code to depth
        val cachedResult = cache[cacheKey]
        if (cachedResult != null) {
            return cachedResult
        }

        val keyboard = if (depth == 0) numericCache else directionalCache

        var currentMove = 'A'
        var result = 0L

        code.forEach { nextMove ->
            val possibleMoves = keyboard[currentMove to nextMove]!!
            
            if (depth == maxDepth) {
                result += possibleMoves.minOf { moves -> moves.size + 1 } 
            } else {
                result += possibleMoves.minOf { moves -> minMoves(moves+'A',  maxDepth, depth + 1) } 
            }
            
            currentMove = nextMove
        }

        cache[cacheKey] = result
        return result
    }

    private fun prepareCache(keypad: Map<Point, Char>) =
        keypad.values.flatMap { from ->
            keypad.values.map { to -> Pair(from, to) to findMoves(keypad, from, to) }
        }.toMap()

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

        return resultPaths.map { path -> path.moves }
    }

    data class Path(val position: Point, val moves: List<Char>)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}