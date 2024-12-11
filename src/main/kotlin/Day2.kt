class Day2(input: List<String>) {

    private val reports = input.map { report -> report.split(" ").map { level -> level.toInt() } }

    fun part1() = reports.count { report -> isSafe(report) }

    fun part2() = reports.map { report -> splitLevels(report) }
        .count { splitReports -> splitReports.any { report -> isSafe(report) } }

    private fun isSafe(report :List<Int>) = isIncreasing(report) || isDecreasing(report)
    
    private fun isIncreasing(report: List<Int>) = report.windowed(2)
        .all { (levelPrev, levelNext) -> levelNext > levelPrev && levelNext - levelPrev in 1..3 }

    private fun isDecreasing(report: List<Int>) = report.windowed(2)
        .all { (levelPrev, levelNext) -> levelPrev > levelNext && levelPrev - levelNext in 1..3 }

    private fun splitLevels(report: List<Int>) =
        report.indices.map { index -> report.filterIndexed { other, _ -> index != other } }
}