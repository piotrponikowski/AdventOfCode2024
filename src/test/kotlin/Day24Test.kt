import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day24Test : FunSpec({

    val realInput = readGroups("day24.txt")
    val exampleInput1 = readGroups("day24-1.txt", true)
    val exampleInput2 = readGroups("day24-2.txt", true)
    
    context("Part 1") {
        test("should solve example 1") {
            Day24(exampleInput1).part1() shouldBe 4
        }

        test("should solve example 2") {
            Day24(exampleInput2).part1() shouldBe 2024
        }

        test("should solve real input") {
            Day24(realInput).part1() shouldBe 53190357879014
        }
    }

    context("Part 2") {
        test("should solve real input") {
            Day24(realInput).part2() shouldBe "bks,hnd,nrn,tdv,tjp,z09,z16,z23"
        }
    }
})
