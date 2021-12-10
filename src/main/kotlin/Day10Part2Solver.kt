object Day10Part2Solver : Solver {
    private val score: Map<Char, Int> = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

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

    private fun totalScoreOf(stack: List<Char>): Long =
        stack.reversed().fold(0) { totalScore, character ->
            (totalScore * 5) + score.getOrDefault(character, 0)
        }

    private tailrec fun parse(
        line: String,
        index: Int = 0,
        stack: MutableList<Char> = mutableListOf()
    ): Long {
        if (index == line.length) {
            return if (stack.isEmpty()) 0 else totalScoreOf(stack)
        }

        val char = line[index]

        if (isOpen(char)) {
            stack.add(char)
        } else {
            val open = stack.removeAt(stack.lastIndex)

            if (!isPair(open, char)) {
                return 0
            }
        }

        return parse(line, index + 1, stack)
    }

    override fun solve(input: Sequence<String>): String {
        val scores = input
            .map { parse(it, 0, mutableListOf()) }
            .filter { it > 0 }
            .sorted()
            .toList()
        val index = scores.size / 2

        return scores[index].toString()
    }
}
