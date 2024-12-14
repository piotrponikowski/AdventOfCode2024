import kotlin.math.abs

class Day14(input: List<String>, testSpace: Boolean = false) {

    private val robots = input.map { line -> parseRobot(line) }

    private val maxX = if (testSpace) 11L else 101L
    private val maxY = if (testSpace) 7L else 103L

    fun part1() = score(robots.map { robot -> move(robot, 100) })

    fun part2(): Int {
        var currentRobots = robots
        var index = 0
        
        while (!hasTree(currentRobots)) {
            currentRobots = currentRobots.map { robot -> move(robot, 1) }
            index++
        }

        return index
    }

    private fun hasTree(robots: List<Robot>) = robots
        .groupBy { robot -> robot.position }
        .all { (_, group) -> group.size == 1 }

    private fun score(robots: List<Robot>): Int {
        val positions = robots.map { robot -> robot.position }
        val quadrant1 = positions.count { (x, y) -> x < maxX / 2 && y < maxY / 2 }
        val quadrant2 = positions.count { (x, y) -> x < maxX / 2 && y > maxY / 2 }
        val quadrant3 = positions.count { (x, y) -> x > maxX / 2 && y < maxY / 2 }
        val quadrant4 = positions.count { (x, y) -> x > maxX / 2 && y > maxY / 2 }
        return quadrant1 * quadrant2 * quadrant3 * quadrant4
    }

    private fun move(robot: Robot, seconds: Int): Robot {
        var newX = robot.position.x + (robot.velocity.x * seconds)
        var newY = robot.position.y + (robot.velocity.y * seconds)

        if (newX < 0) {
            newX += ((abs(newX) / maxX) + 1) * maxX
        }

        if (newY < 0) {
            newY += ((abs(newY) / maxY) + 1) * maxY
        }

        newX %= maxX
        newY %= maxY

        return Robot(Point(newX, newY), robot.velocity)
    }

    private fun parseRobot(input: String) = Regex("-?\\d+").findAll(input)
        .map { group -> group.value.toLong() }.toList()
        .let { (px, py, vx, vy) -> Robot(Point(px, py), Point(vx, vy)) }

    data class Robot(val position: Point, val velocity: Point)

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}