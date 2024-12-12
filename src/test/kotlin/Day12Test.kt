import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Test : FunSpec({

    val realInput = readLines("day12.txt")
    val exampleInput1 = readLines("day12-1.txt", true)
    val exampleInput2 = readLines("day12-2.txt", true)
    val exampleInput3 = readLines("day12-3.txt", true)
    val exampleInput4 = readLines("day12-4.txt", true)


    context("Part 1") {
        test("should solve example 1") {
            Day12(exampleInput1).part1() shouldBe 140
        }

        test("should solve example 2") {
            Day12(exampleInput2).part1() shouldBe 772
        }

        test("should solve example 3") {
            Day12(exampleInput3).part1() shouldBe 1184
        }

        test("should solve example 4") {
            Day12(exampleInput4).part1() shouldBe 1930
        }

        test("should solve real input") {
            Day12(realInput).part1() shouldBe 1377008
        }
    }

    context("Part 2") {
        test("should solve example 1") {
            Day12(exampleInput1).part2() shouldBe 80
        }

        test("should solve example 2") {
            Day12(exampleInput2).part2() shouldBe 436
        }

        test("should solve example 3") {
            Day12(exampleInput3).part2() shouldBe 368
        }

        test("should solve example 4") {
            Day12(exampleInput4).part2() shouldBe 1206
        }

        test("should solve real input") {
            Day12(realInput).part2() shouldBe 815788
        }
    }
})
