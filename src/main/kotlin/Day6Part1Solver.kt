object Day6Part1Solver : Solver {
    private const val daysCount = 80
    private const val resetTimerAt = 0
    private const val resetTimerTo = 6
    private const val newbornTimer = 8

    private fun parse(input: Sequence<String>): Array<Int> {
        val ticks = Array(newbornTimer + 1) { 0 }

        input.first().split(",").map(Integer::parseInt).forEach { tick -> ticks[tick] += 1 }

        return ticks
    }

    private fun simulateDay(ticks: Array<Int>) {
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
