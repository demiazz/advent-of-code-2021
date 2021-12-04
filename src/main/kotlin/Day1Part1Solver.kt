object Day1Part1Solver : Solver {
    private fun compare(left: Int, right: Int): Int = if (right > left) 1 else 0

    override fun solve(input: Sequence<String>): String =
        input.map(String::toInt).zipWithNext(this::compare).sum().toString()
}
