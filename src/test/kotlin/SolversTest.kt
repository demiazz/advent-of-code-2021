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
}
