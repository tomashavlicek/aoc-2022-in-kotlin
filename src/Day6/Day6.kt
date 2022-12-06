package Day6

import readInputAsText

fun main() {
    fun part1(input: String): Int {
        return input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4
    }

    fun part2(input: String): Int {
        return input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsText("Day6/Day6_test")
    println(part1(testInput))

    val input = readInputAsText("Day6/Day6")
    println(part1(input))
    println(part2(input))
}