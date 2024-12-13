import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Test : FunSpec({

    val realInput = readGroups("day13.txt")
    val exampleInput = readGroups("day13.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day13(exampleInput).part1() shouldBe 480
        }

        test("should solve real input") {
            Day13(realInput).part1() shouldBe 29388
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day13(exampleInput).part2() shouldBe 875318608908
        }

        test("should solve real input") {
            Day13(realInput).part2() shouldBe 99548032866004
        }
    }
})
