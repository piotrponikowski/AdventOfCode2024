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
            if (stone == 0L) {
                nextStones[1] = (nextStones[1] ?: 0) + count
            } else if (stone.toString().length % 2 == 0) {
                val splitBy = stone.toString().length / 2
                val stone1 = stone.toString().take(splitBy).toLong()
                val stone2 = stone.toString().takeLast(splitBy).toLong()

                nextStones[stone1] = (nextStones[stone1] ?: 0) + count
                nextStones[stone2] = (nextStones[stone2] ?: 0) + count
            } else {
                val newStone = stone * 2024
                nextStones[newStone] = (nextStones[newStone] ?: 0) + count
            }
        }

        return nextStones
    }
}