import kotlin.collections.ArrayDeque

object Day12Part2Solver : Solver {
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
            val queue =
                ArrayDeque<Pair<MutableList<String>, MutableMap<String, Int>>>(passages.size / 2)

            queue.add(Pair(start, mutableMapOf()))

            return paths(queue, 0)
        }

        private fun addTransition(from: String, to: String) {
            val destinations = passages[from] ?: mutableListOf()

            destinations.add(to)

            passages[from] = destinations
        }

        private tailrec fun paths(
            queue: ArrayDeque<Pair<MutableList<String>, MutableMap<String, Int>>>,
            count: Int
        ): Int {
            val (caves, visited) = queue.removeFirstOrNull() ?: return count

            var nextCount = count

            for (cave in caves) {
                if (cave == "end") {
                    nextCount += 1

                    continue
                }

                // NOTE: What's the mess-up?!
                if (cave[0].isLowerCase()) {
                    val visitsCount = visited.getOrDefault(cave, 0)

                    if (visitsCount == 2) {
                        continue
                    }

                    val isAnyVisitedTwice =
                        visited.filter { it.key[0].isLowerCase() }.any { it.value == 2 }

                    if (visitsCount > 0 && isAnyVisitedTwice) {
                        continue
                    }
                }

                val adjacent = passages[cave] ?: continue

                val nextVisited = visited.toMutableMap()

                nextVisited[cave] = nextVisited.getOrDefault(cave, 0) + 1

                queue.addLast(Pair(adjacent, nextVisited))
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
