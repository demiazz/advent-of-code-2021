import kotlin.system.exitProcess

val solversByDays =
    mapOf(
        1 to Pair(Day1Part1Solver, Day1Part2Solver),
        2 to Pair(Day2Part1Solver, Day2Part2Solver),
        3 to Pair(Day3Part1Solver, Day3Part2Solver),
        4 to Pair(Day4Part1Solver, Day4Part2Solver),
        5 to Pair(Day5Part1Solver, Day5Part2Solver),
        6 to Pair(Day6Part1Solver, Day6Part2Solver),
        7 to Pair(Day7Part1Solver, Day7Part2Solver),
        8 to Pair(Day8Part1Solver, Day8Part2Solver),
        9 to Pair(Day9Part1Solver, Day9Part2Solver),
        10 to Pair(Day10Part1Solver, Day10Part2Solver),
        11 to Pair(Day11Part1Solver, Day11Part2Solver),
        12 to Pair(Day12Part1Solver, Day12Part2Solver),
        13 to Pair(Day13Part1Solver, Day13Part2Solver),
        14 to Pair(Day14Part1Solver, Day14Part2Solver),
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
