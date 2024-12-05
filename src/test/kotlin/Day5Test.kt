import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day5Test : FunSpec({

    val realInput = readGroups("day5.txt")
    val exampleInput = readGroups("day5.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day5(exampleInput).part1() shouldBe 143
        }

        test("should solve real input") {
            Day5(realInput).part1() shouldBe 5732
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day5(exampleInput).part2() shouldBe 123
        }

        test("should solve real input") {
            Day5(realInput).part2() shouldBe 4716
        }
    }
})
