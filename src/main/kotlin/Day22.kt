class Day22(input: List<String>) {

    private val secrets = input.map { line -> line.toLong() }

    fun part1() = secrets.sumOf { secret -> calculateSecrets(secret).last() }

    fun part2(): Long {
        val buyTimes = secrets.map { secret -> calculateBuyTime(secret) }
        val buySignals = buyTimes.flatMap { cache -> cache.keys }.toSet()

        return buySignals.maxOf { buySignal -> buyTimes.sumOf { cache -> cache[buySignal] ?: 0L } }
    }

    private fun calculateBuyTime(secret: Long): Map<String, Long> {
        val prices = calculatePrices(secret)
        val changes = calculateChanges(prices)
        val result = mutableMapOf<String, Long>()

        changes.windowed(4).forEachIndexed { index, lastChanges ->
            result.computeIfAbsent(lastChanges.joinToString()) { prices[index + 4] }
        }

        return result
    }

    private fun calculateChanges(prices: List<Long>) = prices
        .windowed(2).map { (pricePrev, priceNext) -> priceNext - pricePrev }

    private fun calculatePrices(secret: Long) = calculateSecrets(secret)
        .map { nextSecret -> nextSecret % 10 }

    private fun calculateSecrets(secret: Long) = (0..<2000)
        .runningFold(secret) { currentSecret, _ -> nextSecret(currentSecret) }

    private fun nextSecret(secret: Long): Long {
        var result = prune(mix(secret, secret * 64))
        result = prune(mix(result, result / 32))
        result = prune(mix(result, result * 2048))
        return result
    }

    private fun mix(secret: Long, value: Long) = secret.xor(value)

    private fun prune(secret: Long) = secret.mod(16777216L)
}