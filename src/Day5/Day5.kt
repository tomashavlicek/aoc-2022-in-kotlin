package Day5

import readInputAsText
import java.util.Stack

fun main() {

    data class Instruction(val move: Int, val from: Int, val to: Int)

    fun parseInput(input: String): Pair<List<Stack<Char>>, MutableList<Instruction>> {
        val (cratesInput, instructionInput) = input.split("\n\n")
        val cratesSize = cratesInput.last().digitToInt()
        val cratesLines = cratesInput.split('\n').dropLast(1).reversed()
        val crates = List<Stack<Char>>(cratesSize) { Stack() }

        for (line in cratesLines) {
            line.windowed(size = 3, step = 4).forEachIndexed { index, s ->
                if (s[1] != ' ') {
                    crates[index].push(s[1])
                }
            }
        }

        val instructions = mutableListOf<Instruction>()
        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        for (line in instructionInput.split('\n')) {
            regex.matchEntire(line)?.destructured?.let { (move, from, to) ->
                instructions.add(Instruction(move.toInt(), from.toInt() - 1, to.toInt() - 1))
            }
        }

        return crates to instructions
    }

    fun part1(input: String): String {
        val (crates, instructions) = parseInput(input)

        for (instruction in instructions) {
            repeat(instruction.move) {
                val crate = crates[instruction.from].pop()
                crates[instruction.to].push(crate)
            }
        }

        return String(crates.map { it.pop() }.toCharArray())
    }

    fun part2(input: String): String {
        val (crates, instructions) = parseInput(input)

        for (instruction in instructions) {
            val batch = mutableListOf<Char>()
            repeat(instruction.move) {
                batch += crates[instruction.from].pop()
            }
            batch.reversed().forEach {
                crates[instruction.to].push(it)
            }
        }

        return String(crates.map { it.pop() }.toCharArray())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day5/Day5_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputAsText("Day5/Day5")
    println(part1(input))
    println(part2(input))
}
