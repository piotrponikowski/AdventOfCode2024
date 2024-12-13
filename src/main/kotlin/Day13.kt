class Day13(input: List<List<String>>) {

    private val machines = input.map { Machine(parsePoint(it[0]), parsePoint(it[1]), parsePoint(it[2])) }

    fun part1(): Int {
        println(machines)
        val result = machines.map { (solveMachine(it)) }.sum()
        println(result)
        return result
    }

    fun part2(): Long {
        println(machines)
        val result = machines.map { (solveMachine2(it)) }.sum()
        println(result)
        return result
    }

    private fun solveMachine(machine: Machine): Int {
        val maxA = (machine.prize.x / machine.buttonA.x) + 1
        val maxB = (machine.prize.y / machine.buttonB.y) + 1

        val p = hasPrize(machine, 80, 40)

        (0..maxA).forEach { a ->
            (0..maxB).forEach { b ->
//                println("$a, $b")
                if (hasPrize(machine, a, b)) {
                    return (a * 3) + b
                }
            }
        }

        return 0
    }

    private fun solveMachine2(machine: Machine): Long {
        val ax = machine.buttonA.x.toBigInteger()
        val ay = machine.buttonA.y.toBigInteger()
        val bx = machine.buttonB.x.toBigInteger()
        val by = machine.buttonB.y.toBigInteger()
        val px = (machine.prize.x + 10000000000000).toBigInteger()
        val py = (machine.prize.y + 10000000000000).toBigInteger()

        val b = (px * ay - py * ax) / (ay * bx - by * ax)
        val a = (px * by - py * bx) / (by * ax - bx * ay)

        return if (ax * a + bx * b == px && ay * a + by * b == py) {
            3 * a.toLong() + b.toLong()
        } else {
            0
        }
    }


    private fun hasPrize(machine: Machine, a: Int, b: Int): Boolean {
        val x = (machine.buttonA.x * a) + (machine.buttonB.x * b)
        val y = (machine.buttonA.y * a) + (machine.buttonB.y * b)

        return x == machine.prize.x && y == machine.prize.y
    }

    private fun parsePoint(input: String) = Regex("\\d+")
        .findAll(input)
        .map { a -> a.value.toInt() }
        .toList()
        .let { (a, b) -> Point(a, b) }

    data class Machine(val buttonA: Point, val buttonB: Point, val prize: Point)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val realInput = readGroups("day13.txt")
    val exampleInput = readGroups("day13.txt", true)

    val r1 = Day13(exampleInput).part2()
    println(r1)

    val r2 = Day13(realInput).part2()
    println(r2)
}