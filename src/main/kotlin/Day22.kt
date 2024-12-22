class Day22(input: List<String>) {

    private val secrets = input.map { it.toLong() }

    fun part1() {
        val results = secrets.map { solve(it) }
        println(results)
        println(results.sum())
    }

    fun part2()  {
        val prices = secrets.map { prices(it) }
        val priceChanges = prices.map { diffs(it) }
        val priceChangesWindowed = priceChanges
            .mapIndexed { index1, changes -> buildCache(prices[index1], changes) }
        
        val keys = priceChanges
            .flatMap { result -> result.windowed(4) }
            .map {it.joinToString("") { it.toString() } }
            .toSet()
        
//        val keys = listOf(listOf(-1,-1,0,2).joinToString { it.toString("") })
        
        
        var maxBananas  =0L
        keys.forEachIndexed { i, key ->
            println("Key $i of ${keys.size}")
            var bananas = 0L

            priceChangesWindowed.forEachIndexed { index, priceChange ->
                val priceIndex = priceChange[key]
                if(priceIndex != null) {
                    bananas += priceIndex
                }
            }
            
            if(bananas > maxBananas) {
                maxBananas = bananas
                println("Current Result $maxBananas")
            }
        }
        
        
        println("Result $maxBananas")
    }
    
    private fun buildCache(prices: List<Int>, changes: List<Int>):Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        val keys = changes.windowed(4).map { it.joinToString("") }
        keys.forEachIndexed { index, key -> 
            if(!result.containsKey(key)) {
                result[key] =  prices[index + 4]
            }
        }
        return result
    }
    
    private fun diffs(prices: List<Int>) = prices.windowed(2).map { (a, b) -> b-a }

    private fun prices(secret: Long): List<Int> {
        var current = secret
        val result = mutableListOf<Int>()
        (0..<2000).forEach {
            result += current.toString().last().toString().toInt()
            current = nextSecret(current)
        }

        return result
    }
    
    private fun solve(secret: Long): Long {
        var result = secret
        (0..<2000).forEach {
            result = nextSecret(result)
        }

        return result
    }

    private fun nextSecret(secret: Long): Long {
        var result = prune(mix(secret, secret * 64))
        result = prune(mix(result, result / 32))
        result = prune(mix(result, result * 2048))
        return result
    }

    private fun mix(secret: Long, value: Long) = secret.xor(value)

    private fun prune(secret: Long) = secret.mod(16777216L)
    
}

fun main() {
    val realInput = readLines("day22.txt")
    val exampleInput = readLines("day22.txt", true)

    val r1 = Day22(exampleInput).part2()
    println(r1)

    val r2 = Day22(realInput).part2()
    println(r2)
    
    //1570
}