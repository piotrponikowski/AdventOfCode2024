import java.lang.IllegalArgumentException
import kotlin.math.pow

class Day17(input: List<String>) {

    private val registers = input.take(3).map { it.split(": ").last().toLong() }
    private val instructions = input.last().split(": ").last().split(",").map { it.toLong() }

    fun part1() = solve(registers, instructions, registers[0]).joinToString(",")
    
    fun part2() = findOutput(instructions)

    private fun findOutput(target: List<Long>): Long {
        var a = if (target.size == 1) {
            0
        } else {
            8 * findOutput(target.subList(1, target.size))
        }

        while (solve(instructions, instructions, a) != target) {
            a++
        }

        return a
    }


//    fun part2() {
//
////        var index1 = registers[0]
////        while (true) {
////            val fixedRegisters = registers.toMutableList()
////            fixedRegisters[0] = index1
////            val result = solve(fixedRegisters, instructions)
////            if(result.size == instructions.size) {
////                println(result)
////                break
////            } else {
////                index1 *= 2
////            }
////        }
////
////        var index2 = index1
////        while (true) {
////            val fixedRegisters = registers.toMutableList()
////            fixedRegisters[0] = index2
////            val result = solve(fixedRegisters, instructions)
////            if(result.take(6) == instructions.take(6)) {
////                println(result)
////                break
////            } else {
////                index2++
////            }
////        }
////
////        val diff1 = index2 - index1
////        var index3 = index2
////        while (true) {
////            val fixedRegisters = registers.toMutableList()
////            fixedRegisters[0] = index3
////            val result = solve(fixedRegisters, instructions)
////            if(result.take(7) == instructions.take(7)) {
////                println(result)
////                break
////            } else {
////                index3 += diff1
////            }
////        }
//
////        var index = 69402619166958
////        var diff = 10000
////        while (true) {
////            val fixedRegisters = registers.toMutableList()
////            fixedRegisters[0] = index
////            val result = solve(fixedRegisters, instructions)
////            if (result.take(9) == instructions.take(9)) {
////                println(index)
////                println(result)
////                index += diff
//////                break
////            } else {
////                index += diff
////            }
////        }
//        
//        var index = 69549419806958
//        // to 69553177886958
//        var diff = 100
//
//        while (true) {
//            val fixedRegisters = registers.toMutableList()
//            fixedRegisters[0] = index
//            val result = solve(fixedRegisters, instructions)
//            if (result.take(11) == instructions.take(11)) {
//                println(result)
//                println(index)
//                break
//            }  else {
//                index += diff
//            }
//            
//            if(index > 69553177886958) {
//                break
//            }
//        }
//    }

    fun solve(registers: List<Long>, instructions: List<Long>, aStart: Long): List<Long> {
        val currentRegisters = registers.toMutableList()
        currentRegisters[0] = aStart
        var pointer = 0
        val output = mutableListOf<Long>()

        while (pointer < instructions.size) {
            val instruction = instructions[pointer]
            val operand = instructions[pointer + 1]
            var jumped = false

            if (instruction == 0L) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toInt()
                val result = numerator / denominator
                currentRegisters[0] = result
                //println("adv result = $result")
            } else if (instruction == 1L) {
                val value = currentRegisters[1]
                val result = value.xor(operand)
                currentRegisters[1] = result
                //println("bxl result = $result")
            } else if (instruction == 2L) {
                val value = combo(operand, currentRegisters)
                val result = value % 8
                currentRegisters[1] = result
                //println("bst result = $result")
            } else if (instruction == 3L) {
                val value = currentRegisters[0]

                if (value == 0L) {
                    //println("jnz result = skipped")
                } else {
                    pointer = operand.toInt()
                    jumped = true
                    //println("jnz result = $operand")
                }
            } else if (instruction == 4L) {
                val value1 = currentRegisters[1]
                val value2 = currentRegisters[2]
                val result = value1.xor(value2)
                currentRegisters[1] = result
                //println("bxc result = $result")
            } else if (instruction == 5L) {
                val value = combo(operand, currentRegisters)
                val result = value % 8
                output += result
                //println("out result = $result")
            } else if (instruction == 6L) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toLong()
                val result = numerator / denominator
                currentRegisters[1] = result
                //println("bdv result = $result")
            } else if (instruction == 7L) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toInt()
                val result = numerator / denominator
                currentRegisters[2] = result
                //println("cdv result = $result")
            }

            if (!jumped) {
                pointer += 2
            }
        }

        return output
    }

    private fun combo(operand: Long, registers: List<Long>): Long =
        when (operand) {
            0L -> operand
            1L -> operand
            2L -> operand
            3L -> operand
            4L -> registers[0]
            5L -> registers[1]
            6L -> registers[2]
            else -> throw IllegalArgumentException()
        }


}

fun main() {
    val realInput = readLines("day17.txt")
    val exampleInput = readLines("day17.txt", true)

//    val r1 = Day17(exampleInput).part2()
//    println(r1)

    val r2 = Day17(realInput).part2()
    println(r2)
}