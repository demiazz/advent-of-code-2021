import kotlin.system.exitProcess

val solversByDays =
    mapOf(
        1 to Pair(Day1Part1Solver, Day1Part2Solver),
        2 to Pair(Day2Part1Solver, Day2Part2Solver),
        3 to Pair(Day3Part1Solver, Day3Part2Solver),
        4 to Pair(Day4Part1Solver, Day4Part2Solver),
    )

fun main() {
    val min = solversByDays.keys.minOrNull()!!
    val max = solversByDays.keys.maxOrNull()!!

    print("Select a day: ")

    val day = readLine()?.toInt()

    if (day == null || day < min || day > max) {
        println("Please, select day between $min and $max")

        exitProcess(1)
    }

    val solvers = solversByDays[day]!!

    println(solvers.first.solve(Utils.getInputFor(day)))
    println(solvers.second.solve(Utils.getInputFor(day)))
}
