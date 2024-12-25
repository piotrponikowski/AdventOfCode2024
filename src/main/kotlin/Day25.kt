class Day25(input: List<List<String>>) {

    private val locks = input.filter { data -> isLock(data) }.map { data -> parse(data) }
    private val keys = input.filter { data -> isKey(data) }.map { data -> parse(data) }

    fun part1() = locks.sumOf { lock -> keys.count { key -> isFit(lock, key) } }

    private fun isFit(lock: List<Int>, key: List<Int>) =
        lock.zip(key).all { (lockPin, keyPin) -> lockPin + keyPin <= 5 }

    private fun isLock(input: List<String>) = input.first().all { symbol -> symbol == '#' }

    private fun isKey(input: List<String>) = input.last().all { symbol -> symbol == '#' }

    private fun parse(input: List<String>) = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }.toMap()
        .let { data -> (0..4).map { x -> data.count { (point, symbol) -> point.x == x && symbol == '#' } - 1 } }
    
    data class Point(val x: Int, val y: Int)
}