import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day17Test : FunSpec({

    val realInput = readLines("day17.txt")
    val exampleInput1 = readLines("day17-1.txt", true)
    val exampleInput2 = readLines("day17-2.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day17(exampleInput1).part1() shouldBe "4,6,3,5,6,3,5,2,1,0"
        }

        test("should solve real input") {
            Day17(realInput).part1() shouldBe "2,3,6,2,1,6,1,2,1"
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day17(exampleInput2).part2() shouldBe 117440L
        }

        test("should solve real input") {
            Day17(realInput).part2() shouldBe 90938893795561
        }
    }
})
