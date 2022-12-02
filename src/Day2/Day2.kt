package Day2

import CircularList
import readInput

val shapes = CircularList(listOf(Shape.ROCK, Shape.PAPER, Shape.SCISSORS))

enum class Shape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun from(s: String): Shape {
            return when(s) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> ROCK
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (opponent, me) = it.split(' ')
            var roundScore = 0
            val opponentShape = Shape.from(opponent)
            val myShape = Shape.from(me)
            roundScore += myShape.score
            if (opponentShape == shapes[shapes.indexOf(myShape) -1]) {
                roundScore += 6 // Win
            } else if (opponentShape == myShape) {
                roundScore += 3 // Draw
            }
            roundScore
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            var roundScore = 0
            val (opponent, round) = it.split(' ')
            val opponentShape = Shape.from(opponent)
            shapes.indexOf(opponentShape)
            when (round) {
                "X" -> {
                    roundScore += shapes[shapes.indexOf(opponentShape) - 1].score
                }
                "Y" -> {
                    roundScore += 3
                    roundScore += shapes[shapes.indexOf(opponentShape)].score
                }
                "Z" -> {
                    roundScore += 6
                    roundScore += shapes[shapes.indexOf(opponentShape) + 1].score
                }
            }
            roundScore
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day2/Day2_test")
    println(part2(testInput))

    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day2/Day2")
    println(part1(input))
    println(part2(input))
}