object Day1Part2Solver : Solver {
    private fun compare(list: List<Int>): Int = if (list.last() > list.first()) 1 else 0

    override fun solve(input: Sequence<String>): String =
        input.map(String::toInt).windowed(4, transform = this::compare).sum().toString()
}
