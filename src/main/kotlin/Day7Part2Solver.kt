import kotlin.math.abs

object Day7Part2Solver : Solver {
    private fun parse(input: Sequence<String>): Pair<List<Int>, Int> {
        val positions = input.first().split(",").map(Integer::parseInt)
        val mean = positions.sum() / positions.size

        return Pair(positions, mean)
    }

    private fun fuelFor(positions: List<Int>, target: Int): Int =
        positions.sumOf {
            val distance = abs(target - it)

            distance * (distance + 1) / 2
        }

    override fun solve(input: Sequence<String>): String {
        val (positions, target) = parse(input)

        val floorFuel = fuelFor(positions, target)
        val ceilFuel = fuelFor(positions, target + 1)

        return floorFuel.coerceAtMost(ceilFuel).toString()
    }
}
