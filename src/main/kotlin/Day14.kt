class Day14(input: List<String>) {

    private val robots = input.map { parseRobot(it) }
    private val minX = 0L
    private val maxX = 101L
    private val minY = 0L
    private val maxY = 103L

//    private val minX = 0L
//    private val maxX = 11L
//    private val minY = 0L
//    private val maxY = 7L


    fun part1() {
        println(robots)

        var r = robots[10]
//        (0..100).forEach {
//            println(moveRobot(r, it))
//
//        }

        val newRobots = robots.map { moveRobot(it, 100) }
        val s1 = score1(newRobots).size
        val s2 = score2(newRobots).size
        val s3 = score3(newRobots).size
        val s4 = score4(newRobots).size

        println(s1)
        println(s2)
        println(s3)
        println(s4)

        print(newRobots)
        println(s1*s2*s3*s4)

        println(newRobots)
        
        //87904138
    }

    fun part2() {
        println(robots)

        var newRobots = robots
        var index = 0

        
        while(!hasTree(newRobots)) {
            newRobots = newRobots.map { moveRobot(it, 1) }
         
            index++
        }
    
        print(newRobots)
        println(index)

        //87904138
    }
    
    private fun hasTree(robots: List<Robot>) = robots.groupBy { it.position }.all { it.value.size == 1 }

    private fun score1(robots: List<Robot>) = robots.map { it.position }
        .filter { (x, y) -> x < maxX / 2 && y < maxY / 2 }

    private fun score2(robots: List<Robot>) = robots.map { it.position }
        .filter { (x, y) -> x < maxX / 2 && y > maxY / 2 }

    private fun score3(robots: List<Robot>) = robots.map { it.position }
        .filter { (x, y) -> x > maxX / 2 && y < maxY / 2 }

    private fun score4(robots: List<Robot>) = robots.map { it.position }
        .filter { (x, y) -> x > maxX / 2 && y > maxY / 2 }

    private fun moveRobot(robot: Robot, steps: Int): Robot {
        var nx = robot.position.x + (robot.velocity.x * steps)
        var ny = robot.position.y + (robot.velocity.y * steps)

        while (nx < minX) {
            nx += maxX
        }
        nx %= maxX

        while (ny < minY) {
            ny += maxY
        }
        ny %= maxY

        return Robot(Point(nx, ny), robot.velocity)
    }
    
    private fun print(robots: List<Robot>) {
        (minY..<maxY).forEach { y->
            (minX..<maxX).forEach { x->
                val c = robots.filter { it.position.x == x && it.position.y == y }.count()
                if(c>0) {
                    print(c)
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    private fun parseRobot(line: String): Robot {
        val (positionInput, velocityInput) = line.split(" ")
        val (px, py) = positionInput.split("=").last().split(",").map { it.toLong() }
        val (vx, vy) = velocityInput.split("=").last().split(",").map { it.toLong() }
        return Robot(Point(px, py), Point(vx, vy))
    }

    data class Robot(val position: Point, val velocity: Point)

    data class Point(val x: Long, val y: Long) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {
    val realInput = readLines("day14.txt")
    val exampleInput = readLines("day14.txt", true)

//    val r1 = Day14(exampleInput).part2()
//    println(r1)

    val r2 = Day14(realInput).part2()
    println(r2)
}