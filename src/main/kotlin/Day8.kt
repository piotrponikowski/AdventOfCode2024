class Day8(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()

    private val letters = board.filterValues { it != '.' }
    private val distinctLetters = letters.values.toSet()

    fun part1():Int {
        val solutions = mutableSetOf<Point>()

        distinctLetters.forEach { letter ->
            val positions = letters.filterValues { it == letter }.keys

            positions.forEach { position1 ->
                positions.forEach { position2 ->
                    if (position1 != position2) {
                        val dx = position2.x - position1.x
                        val dy = position2.y - position1.y

                        val point1 = Point(position1.x - dx, position1.y - dy)
                        val point2 = Point(position2.x + dx, position2.y + dy)

                        solutions += point1
                        solutions += point2

                    }
                }
            }
        }
        
        val solutions2 = solutions.filter { s -> s in board.keys }
        return solutions2.size
    }

    fun part2():Int {
        val solutions = mutableSetOf<Point>()

        distinctLetters.forEach { letter ->
            val positions = letters.filterValues { it == letter }.keys

            positions.forEach { position1 ->
                positions.forEach { position2 ->
                    if (position1 != position2) {
                        val dx = position2.x - position1.x
                        val dy = position2.y - position1.y
                        
                        val newSolutions = mutableSetOf<Point>()
                        
                        var newPosition = position1
                        while (newPosition in board.keys){
                            newPosition = Point(newPosition.x - dx, newPosition.y - dy)
                            newSolutions += newPosition
                        }

                        newPosition = position1
                        while (newPosition in board.keys){
                            newPosition = Point(newPosition.x + dx, newPosition.y + dy)
                            newSolutions += newPosition
                        }
                        
                        solutions += newSolutions
                    }
                }
            }
        }

        val solutions2 = solutions.filter { s -> s in board.keys }
        return solutions2.size
    }

    data class Point(val x: Int, val y: Int)
}