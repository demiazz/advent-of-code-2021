object Day8Part1Solver : Solver {
    private fun isEasyDigit(input: String): Boolean = input.length in 2..4 || input.length == 7

    private fun countDigits(input: String): Int =
        input.substringAfter(" | ").split(" ").filter(this::isEasyDigit).size

    override fun solve(input: Sequence<String>): String =
        input.sumOf { countDigits(it.substringAfter(" | ")) }.toString()
}
