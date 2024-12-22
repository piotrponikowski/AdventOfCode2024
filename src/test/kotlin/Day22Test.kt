import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day22Test : FunSpec({

    val realInput = readLines("day22.txt")
    val exampleInput1 = readLines("day22-1.txt", true)
    val exampleInput2 = readLines("day22-2.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day22(exampleInput1).part1() shouldBe 37327623
        }

        test("should solve real input") {
            Day22(realInput).part1() shouldBe 13753970725
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day22(exampleInput2).part2() shouldBe 23
        }

        test("should solve real input") {
            Day22(realInput).part2() shouldBe 1570
        }
    }
})
