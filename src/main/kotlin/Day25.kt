class Day25(input: List<List<String>>) {

    private val locks = input.filter { data -> isLock(data) }.map { data -> parsePins(data) }
    private val keys = input.filter { data -> isKey(data) }.map { data -> parsePins(data) }

    fun part1() = locks.sumOf { lock -> keys.count { key -> isFit(lock, key) } }

    private fun isFit(lock: List<Int>, key: List<Int>) =
        lock.zip(key).all { (lockPins, keyPins) -> lockPins + keyPins <= 5 }

    private fun isLock(input: List<String>) = input.first().all { symbol -> symbol == '#' }

    private fun isKey(input: List<String>) = input.last().all { symbol -> symbol == '#' }

    private fun parsePins(input: List<String>) = (0..4).map { x -> input.count { line -> line[x] == '#' } - 1 }

    data class Point(val x: Int, val y: Int)
}