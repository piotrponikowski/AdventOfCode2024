import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day25Test : FunSpec({

    val realInput = readGroups("day25.txt")
    val exampleInput = readGroups("day25.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day25(exampleInput).part1() shouldBe 3
        }

        test("should solve real input") {
            Day25(realInput).part1() shouldBe 3284
        }
    }
})
