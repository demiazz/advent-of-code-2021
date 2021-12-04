object Day4Part1Solver : Solver {
    private const val boardSize = 5

    private enum class ParserState {
        Numbers,
        Empty,
        Board
    }

    private class Parser {
        private var state: ParserState = ParserState.Empty

        private val numbers: MutableList<Int> = mutableListOf()
        private val boards: MutableList<Board> = mutableListOf()

        private val buffer: MutableList<List<Int>> = mutableListOf()

        private fun parseNumbers(line: String) {
            numbers.addAll(line.split(",").map(Integer::parseInt))
        }

        private fun parseBoardNumbers(line: String) {
            buffer.add(line.split(" ").filter(String::isNotEmpty).map(Integer::parseInt))
        }

        private fun flushBoard() {
            if (buffer.isEmpty()) {
                return
            }

            boards.add(Board(buffer))

            buffer.clear()
        }

        fun parse(input: Sequence<String>): Pair<List<Int>, List<Board>> {
            for (line in input) {
                state =
                    if (numbers.isEmpty()) {
                        ParserState.Numbers
                    } else if (line.isEmpty()) {
                        ParserState.Empty
                    } else {
                        ParserState.Board
                    }

                when (state) {
                    ParserState.Numbers -> parseNumbers(line)
                    ParserState.Empty -> flushBoard()
                    ParserState.Board -> parseBoardNumbers(line)
                }
            }

            flushBoard()

            return Pair(numbers, boards)
        }
    }

    private data class BoardCell(val rowIndex: Int, val columnIndex: Int, val seen: Boolean)

    private class Board(input: List<List<Int>>) {
        private val numbers: MutableMap<Int, BoardCell> = mutableMapOf()

        private val rows: Array<Int> = Array(boardSize) { 0 }
        private val columns: Array<Int> = Array(boardSize) { 0 }

        init {
            for ((rowIndex, row) in input.withIndex()) {
                for ((columnIndex, number) in row.withIndex()) {
                    numbers[number] = BoardCell(rowIndex, columnIndex, false)
                }
            }
        }

        fun mark(number: Int): Boolean {
            val (rowIndex, columnIndex) = numbers[number] ?: return false

            rows[rowIndex] += 1
            columns[columnIndex] += 1

            numbers[number] = BoardCell(rowIndex, columnIndex, true)

            return rows[rowIndex] == boardSize || columns[columnIndex] == boardSize
        }

        fun scoreFor(calledNumber: Int): Int {
            val sumOfUnseen =
                numbers.entries.fold(0) { sum, (number, cell) ->
                    if (cell.seen) {
                        sum
                    } else {
                        sum + number
                    }
                }

            return sumOfUnseen * calledNumber
        }
    }

    override fun solve(input: Sequence<String>): String {
        val (numbers, boards) = Parser().parse(input)

        for (number in numbers) {
            for (board in boards) {
                if (board.mark(number)) {
                    return board.scoreFor(number).toString()
                }
            }
        }

        throw Exception("No winners at all")
    }
}
