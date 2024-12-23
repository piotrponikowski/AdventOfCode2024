import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day23Test : FunSpec({

    val realInput = readLines("day23.txt")
    val exampleInput = readLines("day23.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day23(exampleInput).part1() shouldBe 7
        }

        test("should solve real input") {
            Day23(realInput).part1() shouldBe 1240
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day23(exampleInput).part2() shouldBe "co,de,ka,ta"
        }

        test("should solve real input") {
            Day23(realInput).part2() shouldBe "am,aq,by,ge,gf,ie,mr,mt,rw,sn,te,yi,zb"
        }
    }
})
