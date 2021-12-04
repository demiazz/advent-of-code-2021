object Day3Part2Solver : Solver {
    private tailrec fun getRate(
        values: Array<String>,
        indices: List<Int>,
        bitIndex: Int,
        getExpected: (Int, Int) -> Char
    ): Int {
        if (indices.size == 1) {
            val valueIndex = indices.first()

            return Integer.parseInt(values[valueIndex], 2)
        }

        val ones = indices.sumOf { values[it][bitIndex].code - 48 }
        val zeros = indices.size - ones

        val expected = getExpected(ones, zeros)

        val nextIndices = indices.filter { values[it][bitIndex] == expected }

        return getRate(values, nextIndices, bitIndex + 1, getExpected)
    }

    private fun getOxygenGeneratingRate(
        values: Array<String>,
    ): Int =
        getRate(values, values.indices.toList(), 0) { ones, zeros ->
            if (ones < zeros) '0' else '1'
        }

    private fun getCO2ScrubberRate(
        values: Array<String>,
    ): Int =
        getRate(values, values.indices.toList(), 0) { ones, zeros ->
            if (ones < zeros) '1' else '0'
        }

    override fun solve(input: Sequence<String>): String {
        val values = input.toList().toTypedArray()
        val oxygenGeneratingRate = getOxygenGeneratingRate(values)
        val co2ScrubberRate = getCO2ScrubberRate(values)

        return (oxygenGeneratingRate * co2ScrubberRate).toString()
    }
}
