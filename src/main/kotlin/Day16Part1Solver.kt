object Day16Part1Solver : Solver {
    private enum class OperatorLengthType {
        Bits,
        Packets
    }

    // ----- Bits Sequence

    private fun bitsOf(input: Sequence<String>): Sequence<Boolean> = sequence {
        for (byte in input.first()) {
            val bits = "$byte".toInt(16)

            yield(bits.and(0x8) > 0)
            yield(bits.and(0x4) > 0)
            yield(bits.and(0x2) > 0)
            yield(bits.and(0x1) > 0)
        }
    }

    // ----- Tokens Sequence

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

    private const val versionLength = 3
    private const val typeIdLength = 3
    private const val literalLength = 4
    private const val totalBitsLength = 15
    private const val subPacketsCountLength = 11

    private const val literalPacketTypeId = 4

    private fun tokensOf(input: Sequence<String>): Sequence<Int> = sequence {
        var state = TokenizerState.Version

        val buffer = Buffer(versionLength)

        var isLastLiteral = false

        var operatorLengthType = OperatorLengthType.Bits

        for (bit in bitsOf(input)) {
            when (state) {
                TokenizerState.Version -> {
                    if (!buffer.write(bit)) continue

                    yield(buffer.read())

                    state = TokenizerState.TypeId

                    buffer.reset(typeIdLength)
                }
                TokenizerState.TypeId -> {
                    if (!buffer.write(bit)) continue

                    val typeId = buffer.read()

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

                    buffer.reset(literalLength)
                }
                TokenizerState.Literal -> {
                    if (!buffer.write(bit)) continue

                    if (isLastLiteral) {
                        state = TokenizerState.Version

                        buffer.reset(versionLength)
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
                            OperatorLengthType.Bits -> totalBitsLength
                            OperatorLengthType.Packets -> subPacketsCountLength
                        }
                    )
                }
                TokenizerState.OperatorLength -> {
                    if (!buffer.write(bit)) continue

                    state = TokenizerState.Version

                    buffer.reset(versionLength)
                }
            }
        }
    }

    override fun solve(input: Sequence<String>): String = tokensOf(input).sum().toString()
}
