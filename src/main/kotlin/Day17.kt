import java.lang.IllegalArgumentException
import kotlin.math.pow

class Day17(input: List<String>) {

    private val registers = input
        .take(3).map { line -> line.split(": ").last().toLong() }

    private val instructions = input
        .last().split(": ")
        .last().split(",")
        .map { number -> number.toLong() }

    fun part1() = runProgram(registers, instructions).joinToString(",")

    fun part2(): Long {
        var registerReplacement = 0L
        var matchSize = 1

        while (true) {
            val programResult = runProgram(registers, instructions, registerReplacement)
            val resultMatches = programResult.takeLast(matchSize) == instructions.takeLast(matchSize)

            if (resultMatches) {
                if (matchSize < instructions.size) {
                    registerReplacement *= 8
                    matchSize++
                } else {
                    break
                }
            } else {
                registerReplacement += 1
            }
        }

        return registerReplacement
    }

    private fun runProgram(registers: List<Long>, instructions: List<Long>, replacement: Long? = null): List<Long> {
        val currentRegisters = registers.toMutableList()
        if (replacement != null) {
            currentRegisters[0] = replacement
        }

        val output = mutableListOf<Long>()
        var pointer = 0

        while (pointer < instructions.size) {
            val instruction = instructions[pointer].toInt()
            val operand = instructions[pointer + 1]
            var jumped = false

            if (instruction == 0) {
                currentRegisters[0] = division(currentRegisters, operand)

            } else if (instruction == 1) {
                currentRegisters[1] = currentRegisters[1].xor(operand)

            } else if (instruction == 2) {
                currentRegisters[1] = modulo(currentRegisters, operand)

            } else if (instruction == 3) {
                if (currentRegisters[0] > 0) {
                    pointer = operand.toInt()
                    jumped = true
                }

            } else if (instruction == 4) {
                currentRegisters[1] = currentRegisters[1].xor(currentRegisters[2])

            } else if (instruction == 5) {
                output += modulo(currentRegisters, operand)

            } else if (instruction == 6) {
                currentRegisters[1] = division(currentRegisters, operand)

            } else if (instruction == 7) {
                currentRegisters[2] = division(currentRegisters, operand)
            }

            if (!jumped) {
                pointer += 2
            }
        }

        return output
    }
    
    private fun modulo(registers: List<Long>, operand: Long) = comboOperand(registers, operand) % 8
    
    private fun division(registers: List<Long>, operand: Long): Long {
        val numerator = registers[0]
        val denominator = 2.0.pow(comboOperand(registers, operand).toInt()).toLong()
        return numerator / denominator
    }

    private fun comboOperand(registers: List<Long>, operand: Long): Long =
        when {
            operand <= 3 -> operand
            operand <= 6 -> registers[operand.toInt() - 4]
            else -> throw IllegalArgumentException()
        }
}
