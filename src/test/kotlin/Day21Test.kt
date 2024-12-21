import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day21Test : FunSpec({

    val realInput = readLines("day21.txt")
    val exampleInput = readLines("day21.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day21(exampleInput).part1() shouldBe 126384
        }

        test("should solve real input") {
            Day21(realInput).part1() shouldBe 197560
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day21(exampleInput).part2() shouldBe 154115708116294
        }

        test("should solve real input") {
            Day21(realInput).part2() shouldBe 242337182910752
        }
    }
})
