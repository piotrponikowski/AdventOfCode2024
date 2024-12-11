class Day11(input: String) {

    private val startingStones = input.split(" ")
        .map { stone -> stone.toLong() }.associateWith { 1L }

    fun part1() = solve(25)

    fun part2() = solve(75)

    private fun solve(steps: Int) = (0..<steps)
        .fold(startingStones) { currentStones, _ -> blink(currentStones) }
        .map { (_, count) -> count }.sum()

    private fun blink(stones: Map<Long, Long>): Map<Long, Long> {
        val nextStones = mutableMapOf<Long, Long>()

        stones.forEach { (stone, count) ->
            updateStone(stone).forEach { newStone ->
                val currentCount = nextStones[newStone] ?: 0
                nextStones[newStone] = currentCount + count
            }
        }

        return nextStones
    }

    private fun updateStone(stone: Long): List<Long> {
        return if (stone == 0L) {
            listOf(1L)
        } else if (stone.toString().length % 2 == 0) {
            val splitSize = stone.toString().length / 2
            val stone1 = stone.toString().take(splitSize).toLong()
            val stone2 = stone.toString().takeLast(splitSize).toLong()
            listOf(stone1, stone2)
        } else {
            listOf(stone * 2024)
        }
    }
}