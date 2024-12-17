import java.lang.IllegalArgumentException
import kotlin.math.pow

class Day17(input: List<String>) {

    private val registers = input.take(3).map { it.split(": ").last().toInt() }
    private val instructions = input.last().split(": ").last().split(",").map { it.toInt() }

    fun part1() {
        println(registers)
        println(instructions)

        val currentRegisters = registers.toMutableList()
        var pointer = 0
        val output = mutableListOf<Int>()

        while (pointer < instructions.size) {
            val instruction = instructions[pointer]
            val operand = instructions[pointer + 1]
            var jumped = false

            if (instruction == 0) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toInt()
                val result = numerator / denominator
                currentRegisters[0] = result
                println("adv result = $result")
            } else if (instruction == 1) {
                val value = currentRegisters[1]
                val result = value.xor(operand)
                currentRegisters[1] = result
                println("bxl result = $result")
            } else if (instruction == 2) {
                val value = combo(operand, currentRegisters)
                val result = value % 8
                currentRegisters[1] = result
                println("bst result = $result")
            } else if (instruction == 3) {
                val value = currentRegisters[0]
             
                if (value == 0) {
                    println("jnz result = skipped")
                } else {
                    pointer = operand
                    jumped = true
                    println("jnz result = $operand")
                }
            } else if (instruction == 4) {
                val value1 = currentRegisters[1]
                val value2 = currentRegisters[2]
                val result = value1.xor(value2)
                currentRegisters[1] = result
                println("bxc result = $result")
            } else if (instruction == 5) {
                val value = combo(operand, currentRegisters)
                val result = value % 8
                output += result
                println("out result = $result")
            } else if (instruction == 6) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toInt()
                val result = numerator / denominator
                currentRegisters[1] = result
                println("bdv result = $result")
            } else if (instruction == 7) {
                val numerator = currentRegisters[0]
                val denominator = 2.0.pow(combo(operand, currentRegisters).toDouble()).toInt()
                val result = numerator / denominator
                currentRegisters[2] = result
                println("cdv result = $result")
            }

            if (!jumped) {
                pointer += 2
            }
        }

        println(output.joinToString(","))
        println(currentRegisters)
    }

    private fun combo(operand: Int, registers: List<Int>) =
        when (operand) {
            0 -> operand
            1 -> operand
            2 -> operand
            3 -> operand
            4 -> registers[0]
            5 -> registers[1]
            6 -> registers[2]
            else -> throw IllegalArgumentException()
        }

    fun part2() = 2
}

fun main() {
    val realInput = readLines("day17.txt")
    val exampleInput = readLines("day17.txt", true)

    val r1 = Day17(exampleInput).part1()
    println(r1)

    val r2 = Day17(realInput).part1()
    println(r2)
}