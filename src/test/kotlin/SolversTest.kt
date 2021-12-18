import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class SolversTest {
    @Test
    fun testDay1Part1Solver() {
        val input = Utils.getInputFor(1)

        assertEquals("7", Day1Part1Solver.solve(input))
    }

    @Test
    fun testDay1Part2Solver() {
        val input = Utils.getInputFor(1)

        assertEquals("5", Day1Part2Solver.solve(input))
    }

    @Test
    fun testDay2Part1Solver() {
        val input = Utils.getInputFor(2)

        assertEquals("150", Day2Part1Solver.solve(input))
    }

    @Test
    fun testDay2Part2Solver() {
        val input = Utils.getInputFor(2)

        assertEquals("900", Day2Part2Solver.solve(input))
    }

    @Test
    fun testDay3Part1Solver() {
        val input = Utils.getInputFor(3)

        assertEquals("198", Day3Part1Solver.solve(input))
    }

    @Test
    fun testDay3Part2Solver() {
        val input = Utils.getInputFor(3)

        assertEquals("230", Day3Part2Solver.solve(input))
    }

    @Test
    fun testDay4Part1Solver() {
        val input = Utils.getInputFor(4)

        assertEquals("4512", Day4Part1Solver.solve(input))
    }

    @Test
    fun testDay4Part2Solver() {
        val input = Utils.getInputFor(4)

        assertEquals("1924", Day4Part2Solver.solve(input))
    }

    @Test
    fun testDay5Part1Solver() {
        val input = Utils.getInputFor(5)

        assertEquals("5", Day5Part1Solver.solve(input))
    }

    @Test
    fun testDay5Part2Solver() {
        val input = Utils.getInputFor(5)

        assertEquals("12", Day5Part2Solver.solve(input))
    }

    @Test
    fun testDay6Part1Solver() {
        val input = Utils.getInputFor(6)

        assertEquals("5934", Day6Part1Solver.solve(input))
    }

    @Test
    fun testDay6Part2Solver() {
        val input = Utils.getInputFor(6)

        assertEquals("26984457539", Day6Part2Solver.solve(input))
    }

    @Test
    fun testDay7Part1Solver() {
        val input = Utils.getInputFor(7)

        assertEquals("37", Day7Part1Solver.solve(input))
    }

    @Test
    fun testDay7Part2Solver() {
        val input = Utils.getInputFor(7)

        assertEquals("168", Day7Part2Solver.solve(input))
    }

    @Test
    fun testDay8Part1Solver() {
        val input = Utils.getInputFor(8)

        assertEquals("26", Day8Part1Solver.solve(input))
    }

    @Test
    fun testDay8Part2Solver() {
        val input = Utils.getInputFor(8)

        assertEquals("61229", Day8Part2Solver.solve(input))
    }

    @Test
    fun testDay9Part1Solver() {
        val input = Utils.getInputFor(9)

        assertEquals("15", Day9Part1Solver.solve(input))
    }

    @Test
    fun testDay9Part2Solver() {
        val input = Utils.getInputFor(9)

        assertEquals("1134", Day9Part2Solver.solve(input))
    }

    @Test
    fun testDay10Part1Solver() {
        val input = Utils.getInputFor(10)

        assertEquals("26397", Day10Part1Solver.solve(input))
    }

    @Test
    fun testDay10Part2Solver() {
        val input = Utils.getInputFor(10)

        assertEquals("288957", Day10Part2Solver.solve(input))
    }

    @Test
    fun testDay11Part1Solver() {
        val input = Utils.getInputFor(11)

        assertEquals("1656", Day11Part1Solver.solve(input))
    }

    @Test
    fun testDay11Part2Solver() {
        val input = Utils.getInputFor(11)

        assertEquals("195", Day11Part2Solver.solve(input))
    }

    @Test
    fun testDay12Part1Solver() {
        val input = Utils.getInputFor(12)

        assertEquals("10", Day12Part1Solver.solve(input))
    }

    @Test
    fun testDay12Part2Solver() {
        val input = Utils.getInputFor(12)

        assertEquals("36", Day12Part2Solver.solve(input))
    }

    @Test
    fun testDay13Part1Solver() {
        val input = Utils.getInputFor(13)

        assertEquals("17", Day13Part1Solver.solve(input))
    }

    @Test
    fun testDay13Part2Solver() {
        val input = Utils.getInputFor(13)

        assertEquals("0", Day13Part2Solver.solve(input))
    }

    @Test
    fun testDay14Part1Solver() {
        val input = Utils.getInputFor(14)

        assertEquals("1588", Day14Part1Solver.solve(input))
    }

    @Test
    fun testDay14Part2Solver() {
        val input = Utils.getInputFor(14)

        assertEquals("2188189693529", Day14Part2Solver.solve(input))
    }

    @Test
    fun testDay15Part1Solver() {
        val input = Utils.getInputFor(15)

        assertEquals("40", Day15Part1Solver.solve(input))
    }

    @Test
    fun testDay15Part2Solver() {
        val input = Utils.getInputFor(15)

        assertEquals("315", Day15Part2Solver.solve(input))
    }

    @Test
    fun testDay16Part1Solver() {
        val samples =
            listOf(
                Pair("8A004A801A8002F478", "16"),
                Pair("620080001611562C8802118E34", "12"),
                Pair("C0015000016115A2E0802F182340", "23"),
                Pair("A0016C880162017C3686B18A3D4780", "31"),
            )

        for ((transmission, expected) in samples) {
            assertEquals(expected, Day16Part1Solver.solve(sequenceOf(transmission)))
        }
    }

    @Test
    fun testDay16Part2Solver() {
        val samples =
            listOf(
                Pair("C200B40A82", "3"),
                Pair("04005AC33890", "54"),
                Pair("880086C3E88112", "7"),
                Pair("CE00C43D881120", "9"),
                Pair("D8005AC2A8F0", "1"),
                Pair("F600BC2D8F", "0"),
                Pair("9C005AC2F8F0", "0"),
                Pair("9C0141080250320F1802104A08", "1"),
            )

        for ((transmission, expected) in samples) {
            assertEquals(expected, Day16Part2Solver.solve(sequenceOf(transmission)))
        }
    }
}
