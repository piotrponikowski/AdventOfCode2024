import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day8Test : FunSpec({

    val realInput = readLines("day8.txt")
    val exampleInput = readLines("day8.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day8(exampleInput).part1() shouldBe 14
        }

        test("should solve real input") {
            Day8(realInput).part1() shouldBe 273
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day8(exampleInput).part2() shouldBe 34
        }

        test("should solve real input") {
            Day8(realInput).part2() shouldBe 1017
        }
    }
})
