object Day4Part1Solver : Solver {
    private const val boardSize = 5

    private class Parser {
        private var numbers: MutableList<Int> = mutableListOf()
        private val boards: MutableList<Board> = mutableListOf()

        private val buffer: MutableList<List<Int>> = mutableListOf()

        private fun flushBoard() {
            if (buffer.isEmpty()) {
                return
            }

            boards.add(Board(buffer))

            buffer.clear()
        }

        fun parse(input: Sequence<String>): Pair<List<Int>, List<Board>> {
            for (line in input) {
                if (numbers.isEmpty()) {
                    numbers.addAll(line.split(",").map(Integer::parseInt))
                } else if (line.isEmpty()) {
                    flushBoard()
                } else {
                    val row = line.split(" ").filter(String::isNotEmpty).map(Integer::parseInt)

                    buffer.add(row)
                }
            }

            flushBoard()

            return Pair(numbers, boards)
        }
    }

    private class Board(input: List<List<Int>>) {
        private val cells: MutableMap<Int, Pair<Int, Int>> = mutableMapOf()

        private val rows: Array<Int> = Array(boardSize) { 0 }
        private val columns: Array<Int> = Array(boardSize) { 0 }

        init {
            for ((rowIndex, row) in input.withIndex()) {
                for ((columnIndex, number) in row.withIndex()) {
                    cells[number] = Pair(rowIndex, columnIndex)
                }
            }
        }

        fun mark(number: Int): Boolean {
            val (rowIndex, columnIndex) = cells.remove(number) ?: return false

            rows[rowIndex] += 1
            columns[columnIndex] += 1

            return rows[rowIndex] == boardSize || columns[columnIndex] == boardSize
        }

        fun scoreFor(calledNumber: Int): Int = cells.keys.sum() * calledNumber
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
