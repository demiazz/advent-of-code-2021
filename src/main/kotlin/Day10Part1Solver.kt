object Day10Part1Solver : Solver {
    private val score: Map<Char, Int> = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    private fun isOpen(char: Char): Boolean =
        when (char) {
            '(', '[', '{', '<' -> true
            else -> false
        }

    private fun isPair(open: Char, close: Char): Boolean =
        (open == '(' && close == ')') ||
            (open == '[' && close == ']') ||
            (open == '{' && close == '}') ||
            (open == '<' && close == '>')

    private tailrec fun parse(
        line: String,
        index: Int = 0,
        stack: MutableList<Char> = mutableListOf()
    ): Int {
        if (index == line.length) {
            return 0
        }

        val char = line[index]

        if (isOpen(char)) {
            stack.add(char)
        } else {
            val open = stack.removeAt(stack.lastIndex)

            if (!isPair(open, char)) {
                return score.getOrDefault(char, 0)
            }
        }

        return parse(line, index + 1, stack)
    }

    override fun solve(input: Sequence<String>): String =
        input.map { parse(it, 0, mutableListOf()) }.sum().toString()
}
