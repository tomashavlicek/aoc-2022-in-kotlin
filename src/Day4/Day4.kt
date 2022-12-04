package Day4

import readInput
import containsFull

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { elfPair ->
            elfPair.split(',').map { elf ->
                val parts = elf.split('-')
                IntRange(parts[0].toInt(), parts[1].toInt())
            }
        }.count { parts ->
            return@count parts.first().containsFull(parts.last()) || parts.last().containsFull(parts.first())
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { elfPair ->
            elfPair.split(',').map { elf ->
                val parts = elf.split('-')
                IntRange(parts[0].toInt(), parts[1].toInt())
            }
        }.count { parts ->
            return@count parts.first().intersect(parts.last()).isNotEmpty()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day4/Day4_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day4/Day4")
    println(part1(input))
    println(part2(input))
}