import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day9Test : FunSpec({

    val realInput = readText("day9.txt")
    val exampleInput = readText("day9.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day9(exampleInput).part1() shouldBe 1928
        }

        test("should solve real input") {
            Day9(realInput).part1() shouldBe 6242766523059
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day9(exampleInput).part2() shouldBe 2858
        }

        test("should solve real input") {
            Day9(realInput).part2() shouldBe 6272188244509
        }
    }
})
