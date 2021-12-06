object Day6Part2Solver : Solver {
    private const val daysCount = 256
    private const val resetTimerAt = 0
    private const val resetTimerTo = 6
    private const val newbornTimer = 8

    private fun parse(input: Sequence<String>): Array<Long> {
        val ticks = Array(newbornTimer + 1) { 0.toLong() }

        input.first().split(",").map(Integer::parseInt).forEach { tick ->
            // NOTE: Array<Long> have not implemented the `set` operator (?). That's the reason, why we can use `+=`.
            ticks[tick] = ticks[tick] + 1
        }

        return ticks
    }

    private fun simulateDay(ticks: Array<Long>) {
        val breedingFishes = ticks[resetTimerAt]

        for (tick in resetTimerAt until newbornTimer) {
            ticks[tick] = ticks[tick + 1]
        }

        ticks[resetTimerTo] += breedingFishes
        ticks[newbornTimer] = breedingFishes
    }

    override fun solve(input: Sequence<String>): String {
        val ticks = parse(input)

        for (day in 1..daysCount) {
            simulateDay(ticks)
        }

        return ticks.sum().toString()
    }
}
