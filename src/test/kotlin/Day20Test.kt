import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day20Test : FunSpec({

    val realInput = readLines("day20.txt")
    val exampleInput = readLines("day20.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day20(exampleInput).part1(1) shouldBe 44
        }

        test("should solve real input") {
            Day20(realInput).part1() shouldBe 1502
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day20(exampleInput).part2(50) shouldBe 285
        }

        test("should solve real input") {
            Day20(realInput).part2() shouldBe 1028136
        }
    }
})
