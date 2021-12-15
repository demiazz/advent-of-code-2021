import java.util.*
import kotlin.math.abs

object Day15Part2Solver : Solver {
    private data class Point(val x: Int, val y: Int) {}

    private class Cave(val risks: Array<Array<Int>>) {
        companion object {
            private const val scale = 5

            private val offsets: List<Point> =
                listOf(
                    Point(0, -1),
                    Point(-1, 0),
                    Point(1, 0),
                    Point(0, 1),
                )
        }

        private val internalWidth = if (risks.isEmpty()) 0 else risks.first().size
        private val internalHeight = risks.size

        private val width = internalWidth * scale
        private val height = internalHeight * scale

        val from = Point(0, 0)
        val to = Point(width - 1, height - 1)

        private val xRange = 0 until width
        private val yRange = 0 until height

        fun adjacent(point: Point): Sequence<Point> = sequence {
            for ((offsetX, offsetY) in offsets) {
                val adjacentX = point.x + offsetX

                if (adjacentX !in xRange) {
                    continue
                }

                val adjacentY = point.y + offsetY

                if (adjacentY !in yRange) {
                    continue
                }

                yield(Point(adjacentX, adjacentY))
            }
        }

        fun costOf(point: Point): Int {
            val x = point.x % internalWidth
            val y = point.y % internalHeight

            val cost = risks[y][x] + (point.x / internalWidth) + (point.y / internalHeight)

            return if (cost > 9) {
                cost - 9
            } else {
                cost
            }
        }
    }

    private fun parse(input: Sequence<String>): Cave =
        Cave(
            input
                .map() { row -> Array(row.length) { column -> row[column].code - '0'.code } }
                .toList()
                .toTypedArray()
        )

    private fun heuristic(left: Point, right: Point): Int =
        abs(left.x - right.x) + abs(left.y - right.y)

    private fun calculateLowestRisk(cave: Cave): Int {
        val queue =
            PriorityQueue<Pair<Point, Int>>() { left, right -> left.second.compareTo(right.second) }

        val transitions = mutableMapOf<Point, Point?>(cave.from to null)
        val costs = mutableMapOf(cave.from to cave.costOf(cave.from))

        queue.add(Pair(cave.from, 0))

        while (queue.isNotEmpty()) {
            val (current) = queue.remove()

            if (current == cave.to) {
                break
            }

            for (next in cave.adjacent(current)) {
                val nextCost = costs[current]!! + cave.costOf(next)
                val previousCost = costs[next] ?: Int.MAX_VALUE

                if (nextCost < previousCost) {
                    costs[next] = nextCost

                    queue.add(Pair(next, nextCost + heuristic(next, cave.to)))

                    transitions[next] = current
                }
            }
        }

        var risk = 0

        var current: Point? = cave.to

        while (current != null && current != cave.from) {
            risk += cave.costOf(current)

            current = transitions[current]
        }

        return risk
    }

    override fun solve(input: Sequence<String>): String =
        calculateLowestRisk(parse(input)).toString()
}
