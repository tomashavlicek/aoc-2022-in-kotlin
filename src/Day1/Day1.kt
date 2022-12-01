package Day1

import readInputAsText

fun main() {
    fun part1(input: String): Int {
        return input.split("\n\n").maxOf { it.split("\n").sumOf { it.toInt() } }
    }

    fun part2(input: String): Int {
        return input.split("\n\n").map { it.split("\n").sumOf { it.toInt() } }.sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day1/Day1_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInputAsText("Day1/Day1")
    println(part1(input))
    println(part2(input))
}
