import java.util.*

object Day11Part1Solver : Solver {
    private data class Point(val x: Int, val y: Int) {}

    private class Navigator(private val energy: Array<Array<Int>>) {
        private var step: Int = 0

        private val height = energy.size
        private val width = if (energy.isNotEmpty()) energy.first().size else 0

        private fun adjacent(current: Point): Sequence<Point> = sequence {
            val (x, y) = current

            val isNotLeft = x > 0
            val isNotRight = x < (width - 1)

            val isNotTop = y > 0
            val isNotBottom = y < (height - 1)

            if (isNotTop) {
                if (isNotLeft) {
                    yield(Point(x - 1, y - 1))
                }

                yield(Point(x, y - 1))

                if (isNotRight) {
                    yield(Point(x + 1, y - 1))
                }
            }

            if (isNotLeft) {
                yield(Point(x - 1, y))
            }

            if (isNotRight) {
                yield(Point(x + 1, y))
            }

            if (isNotBottom) {
                if (isNotLeft) {
                    yield(Point(x - 1, y + 1))
                }

                yield(Point(x, y + 1))

                if (isNotRight) {
                    yield(Point(x + 1, y + 1))
                }
            }
        }

        private fun octopuses(): Sequence<Point> = sequence {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    yield(Point(x, y))
                }
            }
        }

        private fun up(point: Point): Boolean {
            energy[point.y][point.x] += 1

            return energy[point.y][point.x] > 9
        }

        private fun reset(point: Point) {
            energy[point.y][point.x] = 0
        }

        private fun isFlashed(point: Point) = energy[point.y][point.x] == 0

        private fun step(): Int {
            step += 1

            val willFlash: Queue<Point> = LinkedList()

            for (current in octopuses()) {
                if (up(current)) willFlash.add(current)
            }

            var didFlashed = 0

            while (willFlash.isNotEmpty()) {
                val current = willFlash.remove()

                if (isFlashed(current)) {
                    continue
                }

                reset(current)

                didFlashed += 1

                adjacent(current).filter { !isFlashed(it) }.forEach {
                    if (up(it)) willFlash.add(it)
                }
            }

            return didFlashed
        }

        fun run(steps: Int): Int = (step until steps).sumOf { step() }
    }

    private fun parse(input: Sequence<String>): Navigator =
        Navigator(
            input
                .map { Array(it.length) { index -> it[index].code - '0'.code } }
                .toList()
                .toTypedArray()
        )

    override fun solve(input: Sequence<String>): String = parse(input).run(100).toString()
}
