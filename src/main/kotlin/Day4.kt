class Day4(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()
    
    fun part1() {
        var result = 0
        board.keys.forEach { point ->
            Direction.entries.forEach { direction ->
                print("Checking $point $direction ")
                if(check(point, direction)) {
                    print(" true ")
                    result += 1
                }
                println()
            }
        }
        
        println(result)
    }

    fun part2() {
        var result = 0
        board.keys.forEach { point ->
            var temp = 0
            
            Direction2.entries.forEach { direction ->
                print("Checking $point $direction ")
                if(check2(point, direction)) {
                    print(" true ")
                    temp += 1
                }
                println()
            }
            
            if(temp == 2) {
                result += 1
            }
        }

        println(result)
    }

    private fun check(start: Point, direction: Direction):Boolean {
        val xp = start
        val mp = xp + direction
        val ap = mp + direction
        val sp = ap + direction
        
        val x = board[xp]
        val m = board[mp]
        val a = board[ap]
        val s = board[sp]

        return x == 'X' && m == 'M' && a == 'A' && s == 'S'
    }

    private fun check2(start: Point, direction: Direction2):Boolean {
        val mp = start - direction
        val ap = mp + direction
        val sp = ap + direction
        
        val m = board[mp]
        val a = board[ap]
        val s = board[sp]

        return m == 'M' && a == 'A' && s == 'S'
    }
    
    

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)

        operator fun plus(other: Direction2) = Point(x + other.x, y + other.y)

        operator fun minus(other: Direction2) = Point(x - other.x, y - other.y)
    }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1),
        LU(-1, -1), LD(-1,1), RU(1,-1), RD(1, 1)
    }

    enum class Direction2(val x: Int, val y: Int) {
        LU(-1, -1), LD(-1,1), RU(1,-1), RD(1, 1)
    }
}

fun main() {
    val realInput = readLines("day4.txt")
    val exampleInput = readLines("day4.txt", true)

    val r1 = Day4(exampleInput).part2()
    println(r1)

    val r2 = Day4(realInput).part2()
    println(r2)
}