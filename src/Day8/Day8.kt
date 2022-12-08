package Day8

import readInput

fun main() {

    fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { row ->
            row.windowed(1).map { it.toInt() }
        }
    }

    fun part1(input: List<String>): Int {
        val map = parseInput(input)

        var count = 0
        for ((rowIndex, row) in map.withIndex()) {
            for ((index, r) in row.withIndex()) {
                // all trees from sides are visible
                if (index == 0 || index == row.lastIndex || rowIndex == 0 || rowIndex == map.lastIndex) {
                    count += 1
                } else {
                    val left = row.subList(0, index).maxOrNull() ?: -1
                    val right = row.subList(index + 1, row.size).maxOrNull() ?: -1

                    val column = map.map { it[index] }
                    val top = column.subList(0, rowIndex).maxOrNull() ?: -1
                    val down = column.subList(rowIndex + 1, column.size).maxOrNull() ?: -1

                    if (left < r || right < r || top < r || down < r) {
                        count += 1
                    }
                }
            }
        }

        return count
    }

    fun List<Int>.countUntil(predicate: (Int) -> Boolean): Int {
        var count = 0
        for (item in this) {
            count++
            if (predicate(item))
                return count
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val map = parseInput(input)

        var highScore = 0
        for ((rowIndex, row) in map.withIndex()) {
            for ((index, r) in row.withIndex()) {
                if (index == 0 || index == row.lastIndex || rowIndex == 0 || rowIndex == map.lastIndex) {
                    continue
                } else {
                    val left = row.subList(0, index).reversed().countUntil { r <= it }
                    val right = row.subList(index + 1, row.size).countUntil { r <= it }

                    val column = map.map { it[index] }
                    val top = column.subList(0, rowIndex).reversed().countUntil { r <= it }
                    val down = column.subList(rowIndex + 1, column.size).countUntil { r <= it }

                    val score = left * right * top * down
                    if (score > highScore) {
                        highScore = score
                    }
                }
            }
        }

        return highScore
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day8/Day8_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 21)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 8)

    val input = readInput("Day8/Day8")
    println(part1(input))
    println(part2(input))
}