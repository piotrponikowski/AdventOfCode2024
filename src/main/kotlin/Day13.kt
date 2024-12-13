class Day13(input: List<List<String>>) {

    private val machines = input
        .map { lines -> lines.map { line -> parseNumbers(line) }.map { (x, y) -> Point(x, y) } }
        .map { (buttonA, buttonB, target) -> Machine(buttonA, buttonB, target) }

    fun part1() = machines.sumOf { machine -> canWin(machine) }

    fun part2() = machines.map { machine -> machine.hardTarget() }.sumOf { machine -> canWin(machine) }

    private fun canWin(machine: Machine): Long {
        val (ax, ay) = machine.buttonA
        val (bx, by) = machine.buttonB
        val (px, py) = machine.target

        val b = (px * ay - py * ax) / (ay * bx - by * ax)
        val a = (px * by - py * bx) / (by * ax - bx * ay)
        
        val isSolved = ax * a + bx * b == px && ay * a + by * b == py
        
        return if(isSolved) 3 * a + b else 0
    }

    private fun parseNumbers(input: String) = Regex("\\d+").findAll(input)
        .map { group -> group.value.toLong() }.toList()

    data class Machine(val buttonA: Point, val buttonB: Point, val target: Point) {
        fun hardTarget() = Machine(buttonA, buttonB, Point(target.x + 10000000000000, target.y + 10000000000000))
    }

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}