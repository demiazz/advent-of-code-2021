object Day5Part1Solver : Solver {
    private val pattern = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")

    private data class IterationData(
        val pivot: Int,
        val from: Int,
        val to: Int,
        val toKey: (pivot: Int, coordinate: Int) -> String
    )

    private class Section(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun apply(points: MutableMap<String, Int>): MutableMap<String, Int> {
            if (x1 != x2 && y1 != y2) {
                return points
            }

            val (pivot, from, to, toKey) =
                if (x1 == x2) {
                    IterationData(x1, y1.coerceAtMost(y2), y1.coerceAtLeast(y2)) { x: Int, y: Int ->
                        "$x:$y"
                    }
                } else {
                    IterationData(y1, x1.coerceAtMost(x2), x1.coerceAtLeast(x2)) { y: Int, x: Int ->
                        "$x:$y"
                    }
                }

            for (coordinate in from..to) {
                val key = toKey(pivot, coordinate)

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
