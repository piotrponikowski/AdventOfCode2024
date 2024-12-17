import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day17Test : FunSpec({

    val realInput = readLines("day17.txt")
    val exampleInput = readLines("day17.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day17(exampleInput).part1() shouldBe 1
        }

        test("should solve real input") {
            Day17(realInput).part1() shouldBe 1
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day17(exampleInput).part2() shouldBe 2
        }

        test("should solve real input") {
            Day17(realInput).part2() shouldBe 2
        }
    }
})
