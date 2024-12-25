class Day25(input: List<List<String>>) {

    private val locks = input.filter { isLock(it) }.map { parseLock(it) }
    private val keys = input.filter { isKey(it) }.map { parseKey(it) }


    fun part1(): Int {
        println(locks.size)
        println(keys.size)

        println(locks)
        println(keys)

        var result = 0
        locks.forEach { lock ->
            keys.forEach { key ->

                if (isFit(lock, key)) {

                    result++
                }
            }
        }

        return result

    }

    private fun isFit(lock: List<Int>, key: List<Int>) =
        lock.zip(key).all { (a, b) -> a + b <= 5 }


    private fun isLock(input: List<String>) = input.first().all { it == '#' }

    private fun isKey(input: List<String>) = input.last().all { it == '#' }

    private fun parseLock(input: List<String>): List<Int> {
        val data = input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()
        return (0..4).map { x -> data.count { it.key.x == x && it.value == '#' } - 1 }
    }

    private fun parseKey(input: List<String>): List<Int> {
        val data = input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()
        return (0..4).map { x -> data.count { it.key.x == x && it.value == '#' } - 1 }
    }


    data class Point(val x: Int, val y: Int)
}

fun main() {
    val realInput = readGroups("day25.txt")
    val exampleInput = readGroups("day25.txt", true)

    val r1 = Day25(exampleInput).part1()
    println(r1)

    val r2 = Day25(realInput).part1()
    println(r2)
}