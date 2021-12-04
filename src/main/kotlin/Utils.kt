import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.streams.asSequence

object Utils {
    fun getInputFor(day: Int): Sequence<String> {
        val name = "day_${day}.txt"
        val input =
            {}::class.java.getResourceAsStream(name) ?: throw Exception("Can't find a resource")
        val inputReader = InputStreamReader(input)

        return BufferedReader(inputReader).lines().asSequence()
    }
}
