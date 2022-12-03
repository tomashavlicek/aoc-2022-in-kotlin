package Day3

import readInput

fun main() {

    val priority = CharRange('a', 'z').plus(CharRange('A', 'Z'))

    fun part1(input: List<String>): Int {
        return input
            .map { rucksack: String ->
                val half = rucksack.length / 2
                listOf(rucksack.take(half).toSet(), rucksack.takeLast(half).toSet())
            }.sumOf {
                priority.indexOf(it.first().intersect(it.last()).first()) + 1
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toSet() }
            .windowed(size = 3, step = 3)
            .sumOf {
                val shared = it[0].intersect(it[1]).intersect(it[2]).first()
                priority.indexOf(shared) + 1
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day3/Day3_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day3/Day3")
    println(part1(input))
    println(part2(input))
}