import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day10Test : FunSpec({

    val realInput = readLines("day10.txt")
    val exampleInput = readLines("day10.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day10(exampleInput).part1() shouldBe 36
        }

        test("should solve real input") {
            Day10(realInput).part1() shouldBe 719
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day10(exampleInput).part2() shouldBe 81
        }

        test("should solve real input") {
            Day10(realInput).part2() shouldBe 1530
        }
    }
})
