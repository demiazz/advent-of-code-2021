object Day13Part2Solver : Solver {
    private class Paper(private var width: Int, private var height: Int, points: List<Point>) {
        private val paper = Array(height) { Array(width) { false } }

        init {
            for ((x, y) in points) {
                paper[y][x] = true
            }
        }

        fun fold(positions: List<FoldPosition>): Paper {
            for ((isHorizontal, coordinate) in positions) {
                if (isHorizontal) {
                    foldHorizontal(coordinate)
                } else {
                    foldVertical(coordinate)
                }
            }

            return this
        }

        fun draw() {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    print(if (paper[y][x]) "#" else ".")
                }

                println()
            }
        }

        private fun foldHorizontal(y: Int) {
            for (step in 0 until y) {
                val from = y + step + 1
                val to = y - (step + 1)

                for (x in 0 until width) {
                    paper[to][x] = paper[to][x] || paper[from][x]
                }
            }

            height -= y + 1
        }

        private fun foldVertical(x: Int) {
            for (step in 0 until x) {
                val from = x + step + 1
                val to = x - (step + 1)

                for (y in 0 until height) {
                    paper[y][to] = paper[y][to] || paper[y][from]
                }
            }

            width -= x + 1
        }
    }

    private data class Point(val x: Int, val y: Int) {}

    private data class FoldPosition(val isHorizontal: Boolean, val coordinate: Int) {}

    private fun parse(input: Sequence<String>): Pair<Paper, List<FoldPosition>> {
        var width = 0
        var height = 0

        val points = mutableListOf<Point>()
        val foldPositions = mutableListOf<FoldPosition>()

        var isPoints = true

        for (line in input) {
            if (line.isEmpty()) {
                isPoints = false

                continue
            }

            if (isPoints) {
                val (x, y) = line.split(",").map { it.toInt() }

                points.add(Point(x, y))

                if (x > width) {
                    width = x
                }

                if (y > height) {
                    height = y
                }
            } else {
                val (coordinate, value) = line.substringAfter("fold along ").split("=")

                foldPositions.add(FoldPosition(coordinate == "y", value.toInt()))
            }
        }

        return Pair(Paper(width + 1, height + 1, points), foldPositions)
    }

    override fun solve(input: Sequence<String>): String {
        val (paper, folds) = parse(input)

        paper.fold(folds).draw()

        return "0"
    }
}
