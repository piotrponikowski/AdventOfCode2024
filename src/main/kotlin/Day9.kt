import java.math.BigInteger

class Day9(input: String) {

    private val digits = input.toCharArray().map { it.toString().toInt() }

    fun part1() {
        val data = mutableListOf<Int?>()
        var space = true
        var id = 0

        digits.forEach { digit ->
            if (space) {
                (0..<digit).forEach { _ ->
                    data += id
                }
                id += 1
                space = false
            } else {
                (0..<digit).forEach { _ ->
                    data += null
                }
                space = true
            }
        }
        println(data)

        val newData = data
        while (true) {
            val index1 = newData.indexOfFirst { it == null }
            val index2 = newData.indexOfLast { it != null }
            if (index2 > index1) {
                val val1 = newData[index1]
                val val2 = newData[index2]

                newData[index1] = val2
                newData[index2] = val1
            } else {
                break
            }
        }
        println(newData.toList().map { it ?: '.' }.joinToString(""))
        
        var result = BigInteger.ZERO
        newData.filterNotNull().forEachIndexed { index, c ->
            result += index.toBigInteger() * c.toBigInteger()
        }

        println(result)
    }

    fun part2() {
        val data = mutableListOf<Int?>()
        var space = true
        var id = 0

        digits.forEach { digit ->
            if (space) {
                (0..<digit).forEach { _ ->
                    data += id
                }
                id += 1
                space = false
            } else {
                (0..<digit).forEach { _ ->
                    data += null
                }
                space = true
            }
        }
        println(data.toList().map { it ?: '.' }.joinToString(""))


        val newData = data
        val maxId = newData.filterNotNull().maxOf { it }

        (maxId downTo 0).forEach { currentId ->
            println("Current ID = $currentId")
      
            val dataStart = newData.indexOfFirst { it == currentId }
            val dataEnd = newData.indexOfLast { it == currentId }
            val dataSize = (dataEnd - dataStart) + 1
            
            val spaceStart = newData
                .windowed(dataSize)
                .mapIndexed { index, ints -> index to ints }
                .filter { (index, _) -> index < dataStart }
                .find { (_, ints) -> ints.all { it == null } }?.first
            
            if(spaceStart != null) {
                //println("Moving $currentId")

                (0..<dataSize).forEach { offset ->
                    newData[dataStart + offset] = null
                    newData[spaceStart + offset] = currentId
                }

                //println(data.toList().map { it ?: '.' }.joinToString(""))
            }
        }
        
        
        println(newData.toList().map { it ?: '.' }.joinToString(""))

        var result = BigInteger.ZERO
        newData.forEachIndexed { index, c ->
            if(c != null) {
                result += index.toBigInteger() * c.toBigInteger()
            }
        }

        println(result)
    }
}

fun main() {
    val realInput = readText("day9.txt")
    val exampleInput = readText("day9.txt", true)

    //6242766523059
    
    val r1 = Day9(exampleInput).part2()
    println(r1)

    val r2 = Day9(realInput).part2()
    println(r2)
}