import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day15Test : FunSpec({

    val realInput = readGroups("day15.txt")
    val exampleInput1 = readGroups("day15-1.txt", true)
    val exampleInput2 = readGroups("day15-2.txt", true)


    context("Part 1") {
        test("should solve example 1") {
            Day15(exampleInput1).part1() shouldBe 2028
        }

        test("should solve example 2") {
            Day15(exampleInput2).part1() shouldBe 10092
        }

        test("should solve real input") {
            Day15(realInput).part1() shouldBe 1412971
        }
    }

    context("Part 2") {
        test("should solve example 1") {
            Day15(exampleInput1).part2() shouldBe 1751
        }

        test("should solve example 2") {
            Day15(exampleInput2).part2() shouldBe 9021
        }

        test("should solve real input") {
            Day15(realInput).part2() shouldBe 1429299
        }
    }
})
