object Day14Part2Solver : Solver {
    private fun parse(
        input: Sequence<String>
    ): Triple<MutableMap<String, Long>, MutableMap<Char, Long>, Map<String, Char>> {
        var isTemplate = true

        val pairsCount: MutableMap<String, Long> = mutableMapOf()
        val charsCount: MutableMap<Char, Long> = mutableMapOf()
        val instructions: MutableMap<String, Char> = mutableMapOf()

        for (line in input) {
            if (line.isEmpty()) {
                isTemplate = false

                continue
            }

            if (isTemplate) {
                line.fold(charsCount) { count, char ->
                    count[char] = count.getOrDefault(char, 0) + 1

                    count
                }

                line.windowed(2, 1).fold(pairsCount) { count, pair ->
                    count[pair] = count.getOrDefault(pair, 0) + 1

                    count
                }
            } else {
                val (pair, element) = line.split(" -> ")

                instructions[pair] = element[0]
            }
        }

        return Triple(pairsCount, charsCount, instructions)
    }

    private fun applyInstructions(
        pairsCount: MutableMap<String, Long>,
        charsCount: MutableMap<Char, Long>,
        instructions: Map<String, Char>
    ) {
        val nextPairsCount = pairsCount.toMutableMap()

        for ((pair, count) in pairsCount) {
            val instruction = instructions[pair] ?: continue

            charsCount[instruction] = charsCount.getOrDefault(instruction, 0) + count

            val left = "${pair[0]}$instruction"
            val right = "$instruction${pair[1]}"

            nextPairsCount[left] = nextPairsCount.getOrDefault(left, 0) + count
            nextPairsCount[right] = nextPairsCount.getOrDefault(right, 0) + count

            nextPairsCount[pair] = nextPairsCount.getOrDefault(pair, 0) - count
        }

        pairsCount.clear()
        pairsCount.putAll(nextPairsCount)
    }

    override fun solve(input: Sequence<String>): String {
        val (pairsCount, charsCount, instructions) = parse(input)

        for (step in 1..40) {
            applyInstructions(pairsCount, charsCount, instructions)
        }

        val min = charsCount.minOf { it.value }
        val max = charsCount.maxOf { it.value }

        return (max - min).toString()
    }
}
