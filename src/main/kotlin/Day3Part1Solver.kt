object Day3Part1Solver : Solver {
    private data class Buffer(val size: Int, val counters: Array<Int>)

    private fun countBits(input: String, counters: Array<Int>): Array<Int> {
        for (i in input.indices) {
            counters[i] += input[i].code - 48
        }

        return counters
    }

    override fun solve(input: Sequence<String>): String {
        val buffer: Buffer =
            input.foldIndexed(Buffer(0, emptyArray<Int>())) { index, buffer, value ->
                val counters =
                    if (index == 0) {
                        Array<Int>(value.length) { 0 }
                    } else buffer.counters

                Buffer(buffer.size + 1, countBits(value, counters))
            }

        val min = buffer.size / 2

        val (gamma, epsilon) =
            buffer.counters.reversed().map { it > min }.foldIndexed(Pair(0, 0)) {
                index, rates, bit ->
                    if (bit) {
                        Pair(rates.first, rates.second + (1 shl index))
                    } else {
                        Pair(rates.first + (1 shl index), rates.second)
                    }
            }

        return (gamma * epsilon).toString()
    }
}
