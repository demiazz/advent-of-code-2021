object Day2Part1Solver : Solver {
    private enum class Direction {
        Forward,
        Up,
        Down
    }

    private data class Command(val direction: Direction, val value: Int)

    private data class Location(val position: Int, val depth: Int)

    private fun parse(command: String): Command {
        val (direction, value) = command.split(" ")

        return Command(
            Direction.valueOf(direction.replaceFirstChar { it.uppercaseChar() }),
            value.toInt()
        )
    }

    private fun apply(location: Location, command: String): Location {
        val (position, depth) = location
        val (direction, value) = parse(command)

        return when (direction) {
            Direction.Forward -> Location(position + value, depth)
            Direction.Up -> Location(position, depth - value)
            Direction.Down -> Location(position, depth + value)
        }
    }

    override fun solve(input: Sequence<String>): String {
        val (position, depth) = input.fold(Location(0, 0), this::apply)

        return (position * depth).toString()
    }
}
