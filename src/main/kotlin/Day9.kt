class Day9(input: String) {

    private val digits = input.toCharArray().map { symbol -> symbol.toString().toInt() }

    private val startingData = digits
        .mapIndexed { index, digit -> digit to (if (index % 2 == 0) index / 2 else null) }
        .flatMap { (count, value) -> (0..<count).map { value } }
        .toTypedArray()

    fun part1(): Long {
        val data = startingData.copyOf()

        var spaceIndex = data.indexOfFirst { dataId -> dataId == null }
        var dataIndex = data.indexOfLast { dataId -> dataId != null }

        while (spaceIndex < dataIndex) {
            data[spaceIndex] = data[dataIndex]
            data[dataIndex] = null

            spaceIndex = data.indexOfFirst { dataId -> dataId == null }
            dataIndex = data.indexOfLast { dataId -> dataId != null }
        }

        return checksum(data)
    }

    fun part2(): Long {
        val data = startingData.copyOf()
        val maxDataId = data.filterNotNull().maxOf { dataId -> dataId }

        (maxDataId downTo 0).forEach { dataId ->
            val dataStartIndex = data.indexOfFirst { value -> value == dataId }
            val dataEndIndex = data.indexOfLast { value -> value == dataId }
            val dataSize = (dataEndIndex - dataStartIndex) + 1
            val spaceStartIndex = findSpace(data, dataSize, dataStartIndex)
            
            if (spaceStartIndex != null) {
                val spaceEndIndex = (spaceStartIndex + dataSize) - 1
                (spaceStartIndex..spaceEndIndex).forEach { index -> data[index] = dataId }
                (dataStartIndex..dataEndIndex).forEach { index -> data[index] = null }
            }
        }

        return checksum(data)
    }

    private fun findSpace(data: Array<Int?>, size: Int, beforeIndex: Int): Int? {
        var scanIndex = 0
        var spaceStartIndex = 0
        var currentSize = 0

        while (scanIndex < beforeIndex) {
            currentSize = if (data[scanIndex] == null) currentSize + 1 else 0
            if (currentSize == 1) {
                spaceStartIndex = scanIndex
            }

            if (currentSize == size) {
                return spaceStartIndex
            } else {
                scanIndex++
            }
        }

        return null
    }

    private fun checksum(data: Array<Int?>) = data
        .foldIndexed(0L) { index, result, dataId -> result + (index * (dataId ?: 0)) }
}