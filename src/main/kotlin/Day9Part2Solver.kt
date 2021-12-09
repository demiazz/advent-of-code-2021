import java.util.*

object Day9Part2Solver : Solver {
    private data class Location(val x: Int, val y: Int, val height: Int) {}

    private data class HeightsMap(val heights: Array<Array<Int>>) {
        val mapHeight = heights.size
        val mapWidth = if (heights.isEmpty()) 0 else heights.first().size

        fun lowestLocations(): Sequence<Location> = sequence {
            for (x in 0 until mapWidth) {
                next@ for (y in 0 until mapHeight) {
                    val height = heights[y][x]

                    for (location in adjacent(x, y)) {
                        if (location.height <= height) {
                            continue@next
                        }
                    }

                    yield(Location(x, y, height))
                }
            }
        }

        fun adjacent(x: Int, y: Int): Sequence<Location> = sequence {
            if (y > 0) {
                yield(Location(x, y - 1, heights[y - 1][x]))
            }

            if (y < (mapHeight - 1)) {
                yield(Location(x, y + 1, heights[y + 1][x]))
            }

            if (x > 0) {
                yield(Location(x - 1, y, heights[y][x - 1]))
            }

            if (x < (mapWidth - 1)) {
                yield(Location(x + 1, y, heights[y][x + 1]))
            }
        }

        private tailrec fun basinSize(
            queue: Queue<Location>,
            seen: MutableSet<Location>,
            size: Int
        ): Int {
            if (queue.isEmpty()) {
                return size
            }

            val location = queue.remove()

            if (location in seen) {
                return basinSize(queue, seen, size)
            }

            queue.addAll(
                adjacent(location.x, location.y).filter { !seen.contains(it) && it.height < 9 }
            )

            seen.add(location)

            return basinSize(queue, seen, size + 1)
        }

        fun basinSizes(): Sequence<Int> {
            val seen = mutableSetOf<Location>()

            return lowestLocations().map { basinSize(LinkedList(listOf(it)), seen, 0) }
        }
    }

    private fun parseRow(input: String): Array<Int> =
        Array(input.length) { index -> input[index].code - '0'.code }

    private fun parse(input: Sequence<String>): HeightsMap =
        HeightsMap(input.map(this::parseRow).toList().toTypedArray())

    override fun solve(input: Sequence<String>): String =
        parse(input).basinSizes().toList().sorted().takeLast(3).fold(1, Int::times).toString()
}
