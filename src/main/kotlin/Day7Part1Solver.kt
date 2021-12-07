import kotlin.math.abs

object Day7Part1Solver : Solver {
    private fun parse(input: Sequence<String>): List<Int> =
        input.first().split(",").map(Integer::parseInt)

    private fun targetFor(positions: List<Int>): Int {
        val sortedPositions = positions.sorted()
        val pivot = sortedPositions.size / 2

        return if (sortedPositions.size % 2 == 0) {
            (sortedPositions[pivot - 1] + sortedPositions[pivot]) / 2
        } else {
            sortedPositions[pivot]
        }
    }

    private fun fuelFor(positions: List<Int>, target: Int): Int =
        positions.sumOf { abs(it - target) }

    override fun solve(input: Sequence<String>): String {
        val positions = parse(input)
        val target = targetFor(positions)

        return fuelFor(positions, target).toString()
    }
}
