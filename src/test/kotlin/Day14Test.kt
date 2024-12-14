import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Test : FunSpec({

    val realInput = readLines("day14.txt")
    val exampleInput = readLines("day14.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day14(exampleInput, true).part1() shouldBe 12
        }

        test("should solve real input") {
            Day14(realInput).part1() shouldBe 211773366
        }
    }

    context("Part 2") {
        test("should solve real input") {
            Day14(realInput).part2() shouldBe 7344
        }
    }
})
