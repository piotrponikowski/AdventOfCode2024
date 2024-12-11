class Day11(input: String) {

    private val startingStones = input
        .split(" ")
        .map { it.toLong() }
        .associateWith { 1L }

    fun part1():Long {
        var currentStones = startingStones
        println(currentStones)

        (0..<25).forEach {
            currentStones = blink(currentStones)
            println(currentStones.map { it.value }.sum())
        }
        
        return currentStones.map { it.value }.sum()
    }

    fun part2():Long {
        var currentStones = startingStones
        println(currentStones)

        (0..<75).forEach {
            currentStones = blink(currentStones)
            println()
        }
        
        return currentStones.map { it.value }.sum()
    }

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