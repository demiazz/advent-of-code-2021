import java.util.*

object Day16Part2Solver : Solver {
    private enum class OperatorType {
        Sum,
        Product,
        Minimum,
        Maximum,
        GreaterThan,
        LessThan,
        EqualTo;

        companion object {
            fun valueOf(typeId: Int): OperatorType =
                when (typeId) {
                    0 -> Sum
                    1 -> Product
                    2 -> Minimum
                    3 -> Maximum
                    5 -> GreaterThan
                    6 -> LessThan
                    7 -> EqualTo
                    else -> throw Error("Unknown operator type was given")
                }
        }
    }

    private enum class OperatorLengthType {
        Bits,
        Packets;

        fun size(): Int =
            when (this) {
                Bits -> 15
                Packets -> 11
            }
    }

    private const val lengthSize = 3
    private const val typeIdSize = 3
    private const val literalPositionSize = 1
    private const val literalSize = 4
    private const val lengthTypeSize = 1
    private const val totalBitsSize = 15
    private const val childrenCountSize = 11

    private const val headerSize = lengthSize + typeIdSize
    private const val literalGroupSize = literalPositionSize + literalSize

    private const val literalPacketTypeId = 4

    // ----- Bits

    private fun bitsOf(input: Sequence<String>): Sequence<Boolean> = sequence {
        for (byte in input.first()) {
            val bits = "$byte".toInt(16)

            yield(bits.and(0x8) > 0)
            yield(bits.and(0x4) > 0)
            yield(bits.and(0x2) > 0)
            yield(bits.and(0x1) > 0)
        }
    }

    // ----- Tokens

    private abstract class Token() {}

    private data class TypeIdToken(val value: Int) : Token() {}

    private data class LiteralToken(val value: Int, val isLast: Boolean) : Token() {}

    private data class OperatorLengthToken(
        val value: Int,
        val type: OperatorLengthType,
    ) : Token() {}

    private enum class TokenizerState {
        Version,
        TypeId,
        LiteralPosition,
        Literal,
        OperatorLengthType,
        OperatorLength,
    }

    private class Buffer(length: Int) {
        private var cursor = length - 1
        private var data = 0

        private var isDone = length == 0

        fun reset(length: Int) {
            cursor = length - 1
            data = 0
            isDone = false
        }

        fun write(bit: Boolean): Boolean {
            if (isDone) throw Error("Buffer is filled up already")

            if (bit) {
                data = data.or((0x1).shl(cursor))
            }

            cursor -= 1

            if (cursor < 0) {
                isDone = true
            }

            return isDone
        }

        fun read(): Int {
            if (!isDone) throw Error("Buffer is not filled up yet")

            return data
        }
    }

    private fun tokensOf(input: Sequence<String>): Sequence<Token> = sequence {
        var state = TokenizerState.Version

        val buffer = Buffer(lengthSize)

        var isLastLiteral = false

        var operatorLengthType = OperatorLengthType.Bits

        for (bit in bitsOf(input)) {
            when (state) {
                TokenizerState.Version -> {
                    if (!buffer.write(bit)) continue

                    state = TokenizerState.TypeId

                    buffer.reset(typeIdSize)
                }
                TokenizerState.TypeId -> {
                    if (!buffer.write(bit)) continue

                    val typeId = buffer.read()

                    yield(TypeIdToken(buffer.read()))

                    state =
                        if (typeId == literalPacketTypeId) {
                            TokenizerState.LiteralPosition
                        } else {
                            TokenizerState.OperatorLengthType
                        }
                }
                TokenizerState.LiteralPosition -> {
                    isLastLiteral = !bit

                    state = TokenizerState.Literal

                    buffer.reset(literalSize)
                }
                TokenizerState.Literal -> {
                    if (!buffer.write(bit)) continue

                    yield(LiteralToken(buffer.read(), isLastLiteral))

                    if (isLastLiteral) {
                        state = TokenizerState.Version

                        buffer.reset(lengthSize)
                    } else {
                        state = TokenizerState.LiteralPosition
                    }
                }
                TokenizerState.OperatorLengthType -> {
                    operatorLengthType =
                        if (bit) OperatorLengthType.Packets else OperatorLengthType.Bits

                    state = TokenizerState.OperatorLength

                    buffer.reset(
                        when (operatorLengthType) {
                            OperatorLengthType.Bits -> totalBitsSize
                            OperatorLengthType.Packets -> childrenCountSize
                        }
                    )
                }
                TokenizerState.OperatorLength -> {
                    if (!buffer.write(bit)) continue

                    yield(OperatorLengthToken(buffer.read(), operatorLengthType))

                    state = TokenizerState.Version

                    buffer.reset(lengthSize)
                }
            }
        }
    }

    // ----- Packets Sequence

    private abstract class Packet() {
        abstract val size: Int

        abstract fun evaluate(): Long
    }

    private class LiteralPacket(groups: List<Long>) : Packet() {
        private val value: Long =
            groups.withIndex().fold(0) { result, (index, group) ->
                result.or(group.shl(((groups.size - 1) - index) * literalSize))
            }

        override val size = headerSize + (groups.size * literalGroupSize)

        override fun evaluate(): Long = value
    }

    private class OperatorPacket(
        private val typeId: Int,
        private val lengthType: OperatorLengthType,
        private var left: Int,
    ) : Packet() {
        private val packets = mutableListOf<Packet>()

        override val size: Int
            get() = headerSize + lengthTypeSize + lengthType.size() + children.sumOf { it.size }

        val children: List<Packet>
            get() = packets

        val isComplete: Boolean
            get() = left == 0

        fun add(packet: Packet) {
            if (isComplete) {
                throw Error("Trying to add packet to the already complete packet")
            }

            packets.add(packet)

            left -=
                when (lengthType) {
                    OperatorLengthType.Bits -> packet.size
                    OperatorLengthType.Packets -> 1
                }

            if (left < 0) {
                throw Error("Mismatch of expected length of the package and actual data")
            }
        }

        override fun evaluate(): Long {
            val values = children.map { it.evaluate() }

            return when (OperatorType.valueOf(typeId)) {
                OperatorType.Sum -> values.sum()
                OperatorType.Product -> values.fold(1.toLong()) { result, value -> result * value }
                OperatorType.Minimum -> values.minOf { it }
                OperatorType.Maximum -> values.maxOf { it }
                OperatorType.GreaterThan -> if (values.first() > values.last()) 1 else 0
                OperatorType.LessThan -> if (values.first() < values.last()) 1 else 0
                OperatorType.EqualTo -> if (values.first() == values.last()) 1 else 0
            }
        }
    }

    private fun expressionOf(input: Sequence<String>): Packet {
        var typeId = 0

        val values = mutableListOf<Long>()

        val parents: Deque<OperatorPacket> = ArrayDeque()

        for (token in tokensOf(input)) {
            val packet =
                when (token) {
                    is TypeIdToken -> {
                        typeId = token.value

                        continue
                    }
                    is LiteralToken -> {
                        values.add(token.value.toLong())

                        if (!token.isLast) continue

                        val packet = LiteralPacket(values.toList())

                        values.clear()

                        packet
                    }
                    is OperatorLengthToken -> {
                        val (value, lengthType) = token

                        OperatorPacket(typeId, lengthType, value)
                    }
                    else -> continue
                }

            when (packet) {
                is LiteralPacket -> {
                    if (parents.isEmpty()) return packet

                    parents.last.add(packet)
                }
                is OperatorPacket -> {
                    parents.add(packet)

                    continue
                }
            }

            while (parents.last.isComplete) {
                if (parents.size == 1) return parents.last

                val child = parents.removeLast()

                parents.last.add(child)
            }
        }

        return parents.last
    }

    override fun solve(input: Sequence<String>): String = expressionOf(input).evaluate().toString()
}
