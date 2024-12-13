import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({

    val realInput = readLines("day13.txt")
    val exampleInput = readLines("day13.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day13(exampleInput).part1() shouldBe 1
        }

        test("should solve real input") {
            Day13(realInput).part1() shouldBe 1
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day13(exampleInput).part2() shouldBe 2
        }

        test("should solve real input") {
            Day13(realInput).part2() shouldBe 2
        }
    }
})
