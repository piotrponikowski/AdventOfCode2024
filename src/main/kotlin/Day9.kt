class Day9(input: String) {

    private val digits = input.toCharArray().map { symbol -> symbol.toString().toInt() }

    private val startingData = digits
        .mapIndexed { index, digit -> digit to (if (index % 2 == 0) index / 2 else null) }
        .flatMap { (count, value) -> (0..<count).map { value } }
        .toTypedArray()

    fun part1(): Long {
        val data = startingData.copyOf()

        var spaceIndex = findNextSpace(data, 0)
        var dataIndex = findPrevData(data, data.size - 1)

        while (spaceIndex < dataIndex) {
            val dataId = data[dataIndex]
            data[spaceIndex] = dataId
            data[dataIndex] = null

            spaceIndex = findNextSpace(data, spaceIndex)
            dataIndex = findPrevData(data, dataIndex)
        }

        return checksum(data)
    }

    fun part2() = 2

    private fun findNextSpace(data: Array<Int?>, fromIndex: Int) =
        findNext(data, fromIndex) { dataId -> dataId == null }

    private fun findPrevData(data: Array<Int?>, fromIndex: Int) =
        findPrev(data, fromIndex) { dataId -> dataId != null }

    private fun findNext(data: Array<Int?>, fromIndex: Int, condition: (Int?) -> Boolean): Int {
        var currentIndex = fromIndex
        while (!condition(data[currentIndex])) {
            currentIndex++
        }
        return currentIndex
    }

    private fun findPrev(data: Array<Int?>, fromIndex: Int, condition: (Int?) -> Boolean): Int {
        var currentIndex = fromIndex
        while (!condition(data[currentIndex])) {
            currentIndex--
        }
        return currentIndex
    }

    private fun checksum(data: Array<Int?>) = data
        .foldIndexed(0L) { index, result, dataId -> result + (index * (dataId ?: 0)) }
}