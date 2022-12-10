package Day10

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val signals = mutableListOf<Int>()
        var x = 1
        var cycle = 1
        input.forEach { s ->
            if (s == "noop") {
                cycle++
            } else {
                cycle++
                if (cycle % 40 == 20) {
                    println("cycle: $cycle X $x")
                    signals.add(cycle * x)
                }
                cycle++
                x += s.split(' ')[1].toInt()
            }

            if (cycle % 40 == 20) {
                println("cycle: $cycle X $x")
                signals.add(cycle * x)
            }
        }

        return signals.sum()
    }

    fun part2(input: List<String>) {
        val picture = List(7) { CharArray(40) }
        var x = 1
        var cycle = 0
        var row: Int
        var pixelDrawn: Int

        fun draw() {
            row = cycle / 40
            pixelDrawn = cycle % 40
            if (pixelDrawn in IntRange(x - 1, x + 1)) {
                picture[row][pixelDrawn] = '#'
            } else {
                picture[row][pixelDrawn] = '.'
            }
        }

        input.forEach { s ->
            if (s == "noop") {
                cycle++
            } else {
                cycle++
                draw()
                cycle++
                x += s.split(' ')[1].toInt()
            }

            draw()
        }

        picture.forEach { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10/Day10_test")
    part1(testInput).also {
        println(it)
        check(it == 13140)
    }

    val input = readInput("Day10/Day10")
    println(part1(input))
    part2(input)
}
