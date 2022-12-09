package Day9

import readInput
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

fun main() {
    data class Point(var x: Int = 0, var y: Int = 0)

    fun part1(input: List<String>): Int {
        val size = 800
        val visited = Array(size) { BooleanArray(size) }
        val head = Point(250,250)
        val oldHead = Point()
        val tail = Point(250,250)
        visited[250][250] = true

        input.forEach { line ->
            val (direction, steps) = line.split(' ')
            repeat(steps.toInt()) {
                oldHead.x = head.x
                oldHead.y = head.y
                when (direction) {
                    "R" -> {
                        head.x++
                    }
                    "U" -> {
                        head.y++
                    }
                    "L" -> {
                        head.x--
                    }
                    "D" -> {
                        head.y--
                    }
                }

                val ac = abs(tail.x - head.x).toDouble()
                val cb = abs(tail.y - head.y).toDouble()
                val distance = hypot(ac, cb)
                if (distance >= 2) {
                    tail.x = oldHead.x
                    tail.y = oldHead.y

                    visited[tail.y][tail.x] = true

                }
            }
        }

        return visited.sumOf { it.count { it } }
    }

    fun part2(input: List<String>): Int {
        val size = 800
        val visited = Array(size) { BooleanArray(size) }
        val knots = MutableList(10) { Point(400,400) }
        visited[400][400] = true

        input.forEach { line ->
            val (direction, steps) = line.split(' ')
            repeat(steps.toInt()) {
                when (direction) {
                    "R" -> {
                        knots[0].x++
                    }

                    "U" -> {
                        knots[0].y++
                    }

                    "L" -> {
                        knots[0].x--
                    }

                    "D" -> {
                        knots[0].y--
                    }
                }

                for (i in 0 until 9) {
                    val first = knots[i]
                    val second = knots[i + 1]
                    val dx = first.x - second.x
                    val dy = first.y - second.y
                    if (max(abs(dx), abs(dy)) > 1) {
                        second.x += dx.coerceIn(-1, 1)
                        second.y += dy.coerceIn(-1, 1)
                    }
                }

                val tail = knots.last()
                visited[tail.y][tail.x] = true
            }
        }

        return visited.sumOf { it.count { it } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day9/Day9_test")
    part2(testInput).apply {
        println(this)
        check(this == 36)
    }

    val input = readInput("Day9/Day9")
    println(part1(input))
    println(part2(input))
}