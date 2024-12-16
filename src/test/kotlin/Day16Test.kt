import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day16Test : FunSpec({

    val realInput = readLines("day16.txt")
    val exampleInput1 = readLines("day16-1.txt", true)
    val exampleInput2 = readLines("day16-2.txt", true)

    context("Part 1") {
        test("should solve example 1") {
            Day16(exampleInput1).part1() shouldBe 7036
        }

        test("should solve example 2") {
            Day16(exampleInput2).part1() shouldBe 11048
        }

        test("should solve real input") {
            Day16(realInput).part1() shouldBe 99460
        }
    }

    context("Part 2") {
        test("should solve example 1") {
            Day16(exampleInput1).part2() shouldBe 45
        }

        test("should solve example 2") {
            Day16(exampleInput2).part2() shouldBe 64
        }

        test("should solve real input") {
            Day16(realInput).part2() shouldBe 500
        }
    }
})
