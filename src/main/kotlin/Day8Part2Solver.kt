import java.util.*
import kotlin.math.pow

object Day8Part2Solver : Solver {
    private fun toBitSet(input: String): BitSet {
        val signal = input.fold(BitSet(8)) { bitSet, char ->
            bitSet.flip(char.code - 'a'.code)

            bitSet
        }

        return signal
    }

    private fun parseWires(input: String): Map<Int, List<BitSet>> =
        input.split(" ").fold(mutableMapOf<Int, MutableList<BitSet>>()) { map, wires ->
            val size = wires.length
            val signals = map[size] ?: mutableListOf()

            signals.add(toBitSet(wires))

            map[size] = signals

            map
        }

    private fun parseEasyDigits(wires: Map<Int, List<BitSet>>): List<BitSet> {
        val (one) = wires[2]!!
        val (four) = wires[4]!!
        val (seven) = wires[3]!!
        val (eight) = wires[7]!!

        return listOf(one, four, seven, eight)
    }

    private fun isZero(signal: BitSet, seven: BitSet): Boolean {
        val mask: BitSet = (seven.clone() as BitSet)

        mask.and(signal)

        return mask.cardinality() == 3
    }

    private fun isNine(signal: BitSet, four: BitSet): Boolean {
        val mask: BitSet = (four.clone() as BitSet)

        mask.and(signal)

        return mask.cardinality() == 4
    }

    private fun parseSixSegmentsDigits(
        wires: List<BitSet>,
        four: BitSet,
        seven: BitSet
    ): List<BitSet> {
        var zero: BitSet? = null
        var six: BitSet? = null
        var nine: BitSet? = null

        for (signal in wires) {
            if (isNine(signal, four)) {
                nine = signal
            } else if (isZero(signal, seven)) {
                zero = signal
            } else {
                six = signal
            }
        }

        return listOf(zero!!, six!!, nine!!)
    }

    private fun isTwo(signal: BitSet, nine: BitSet): Boolean {
        val mask: BitSet = nine.clone() as BitSet

        mask.and(signal)

        return mask.cardinality() == 4
    }

    private fun isThree(signal: BitSet, seven: BitSet): Boolean {
        val mask: BitSet = seven.clone() as BitSet

        mask.and(signal)

        return mask.cardinality() == 3
    }

    private fun parseFiveSegmentsDigits(wires: List<BitSet>, seven: BitSet, nine: BitSet): List<BitSet> {
        var two: BitSet? = null
        var three: BitSet? = null
        var five: BitSet? = null

        for (signal in wires) {
            if (isTwo(signal, nine)) {
                two = signal
            } else if (isThree(signal, seven)) {
                three = signal
            } else {
                five = signal
            }
        }

        return listOf(two!!, three!!, five!!)
    }

    private fun parseSignals(wires: Map<Int, List<BitSet>>): Map<BitSet, Int> {
        val (one, four, seven, eight) = parseEasyDigits(wires)
        val (zero, six, nine) = parseSixSegmentsDigits(wires[6]!!, four, seven)
        val (two, three, five) = parseFiveSegmentsDigits(wires[5]!!, seven, nine)

        return mapOf(
            zero to 0,
            one to 1,
            two to 2,
            three to 3,
            four to 4,
            five to 5,
            six to 6,
            seven to 7,
            eight to 8,
            nine to 9
        )
    }

    private fun parseDigits(input: String): List<BitSet> = input.split(" ").map(this::toBitSet)

    private fun parseDisplay(input: String): Int {
        val (wires, digits) = input.split(" | ")

        val signals = parseSignals(parseWires(wires))

        return parseDigits(digits)
            .mapIndexed { index, signal ->
                signals[signal]!! * 10.0.pow((3 - index).toDouble()).toInt()
            }
            .sum()
    }

    override fun solve(input: Sequence<String>): String {
        return input.map(this::parseDisplay).sum().toString()
    }
}
