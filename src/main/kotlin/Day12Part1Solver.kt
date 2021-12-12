import kotlin.collections.ArrayDeque

object Day12Part1Solver : Solver {
    private class CavesMap(input: Sequence<Pair<String, String>>) {
        val start: MutableList<String> = mutableListOf()
        val passages: MutableMap<String, MutableList<String>> = mutableMapOf()

        init {
            for ((left, right) in input) {
                if (left == "start") {
                    start.add(right)
                } else if (right == "end") {
                    addTransition(left, right)
                } else {
                    addTransition(left, right)
                    addTransition(right, left)
                }
            }
        }

        fun countOfPaths(): Int {
            val queue = ArrayDeque<Pair<MutableList<String>, MutableList<String>>>(passages.size / 2)

            queue.add(Pair(start, mutableListOf()))

            return paths(queue, 0)
        }

        private fun addTransition(from: String, to: String) {
            val destinations = passages[from] ?: mutableListOf()

            destinations.add(to)

            passages[from] = destinations
        }

        private tailrec fun paths(
            queue: ArrayDeque<Pair<MutableList<String>, MutableList<String>>>,
            count: Int
        ): Int {
            val (caves, path) = queue.removeFirstOrNull() ?: return count

            var nextCount = count

            for (cave in caves) {
                if (cave == "end") {
                    nextCount += 1

                    continue
                }

                if (cave[0].isLowerCase() && cave in path) {
                    continue
                }

                val adjacent = passages[cave] ?: continue

                val nextPath = path.toMutableList()
                nextPath.add(cave)

                queue.addLast(Pair(adjacent, nextPath))
            }

            return paths(queue, nextCount)
        }
    }

    private fun parse(input: Sequence<String>): CavesMap =
        CavesMap(
            input.map() {
                val (left, right) = it.split("-")

                Pair(left, right)
            }
        )

    override fun solve(input: Sequence<String>): String = parse(input).countOfPaths().toString()
}
