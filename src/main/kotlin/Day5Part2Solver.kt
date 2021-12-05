object Day5Part2Solver : Solver {
    private val pattern = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")

    private class Section(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun apply(points: MutableMap<String, Int>): MutableMap<String, Int> {
            val coordinates =
                if (x1 == x2) {
                    val fromY = y1.coerceAtMost(y2)
                    val toY = y1.coerceAtLeast(y2)

                    (fromY..toY).asSequence().map { Pair(x1, it) }
                } else if (y1 == y2) {
                    val fromX = x1.coerceAtMost(x2)
                    val toX = x1.coerceAtLeast(x2)

                    (fromX..toX).asSequence().map { Pair(it, y1) }
                } else {
                    sequence<Pair<Int, Int>> {
                        var x = x1
                        var y = y1

                        val stepX = if (x1 < x2) 1 else -1
                        val stepY = if (y1 < y2) 1 else -1

                        while (x != x2 && y != y2) {
                            val point = Pair(x, y)

                            yield(point)

                            x += stepX
                            y += stepY
                        }
                    }
                }

            for ((x, y) in coordinates) {
                val key = "$x:$y"

                points[key] = (points[key] ?: 0) + 1
            }

            return points
        }
    }

    private fun parse(input: Sequence<String>): Sequence<Section> =
        input
            .map {
                val (x1, y1, x2, y2) =
                    pattern.findAll(it).first().groupValues.drop(1).map(Integer::parseInt)

                Section(x1, y1, x2, y2)
            }
            .filter { true }

    private fun mapSections(sections: Sequence<Section>): MutableMap<String, Int> =
        sections.fold(mutableMapOf<String, Int>()) { points, section -> section.apply(points) }

    override fun solve(input: Sequence<String>): String =
        mapSections(parse(input)).filterValues { it > 1 }.count().toString()
}
