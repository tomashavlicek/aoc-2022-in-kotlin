package Day11

import readInput
import java.math.BigInteger
import java.util.LinkedList
import java.util.Queue

fun main() {

    class Monkey(
        val items: Queue<Long>,
        val operation: (old: Long) -> Long,
        val testDivisible: Int,
        val ifTrue: Int,
        val ifFalse: Int,
        var inspectedItems: Int = 0
    )

    fun parseOperation(input: String): (old: Long) -> Long {
        val operationInput = input.substringAfter("Operation: new = old ")
        if (operationInput.first() == '+') {
            val number = operationInput.drop(2).toInt()
            return { old -> old + number }
        } else {
            if (operationInput == "* old") {
                return { old -> old * old }
            } else {
                val number = operationInput.drop(2).toInt()
                return { old -> old * number }
            }
        }
    }

    fun parseInput(input: List<String>): List<Monkey> {
        val monkeysInput = input.windowed(size = 6, step = 7)
        val monkeys = mutableListOf<Monkey>()
        for (monkeyInput in monkeysInput) {
            monkeys.add(
                Monkey(
                    items = LinkedList(monkeyInput[1].substringAfter("Starting items: ").filter { !it.isWhitespace() }.split(',').map { it.toLong() }),
                    operation = parseOperation(monkeyInput[2]),
                    testDivisible = monkeyInput[3].substringAfter("Test: divisible by ").toInt(),
                    ifTrue = monkeyInput[4].substringAfter("If true: throw to monkey ").toInt(),
                    ifFalse = monkeyInput[5].substringAfter("If false: throw to monkey ").toInt()
                )
            )
        }
        return monkeys
    }

    fun part1(input: List<String>): Int {
        val monkeys = parseInput(input)
        repeat(times = 20) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    var item = monkey.items.remove()
                    // inspect the item
                    item = monkey.operation(item)
                    monkey.inspectedItems++
                    // monkey gets bored with the item
                    item /= 3
                    // test worry level
                    if (item % monkey.testDivisible == 0L) {
                        monkeys[monkey.ifTrue].items.add(item)
                    } else {
                        monkeys[monkey.ifFalse].items.add(item)
                    }
                }
            }
        }

        return monkeys.map { it.inspectedItems }.sortedDescending().take(2).reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseInput(input)
        val commonWorryLevel = monkeys.map { it.testDivisible }.reduce { acc, i -> acc * i }

        repeat(times = 10000) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    var item = monkey.items.remove()
                    // inspect the item
                    item = monkey.operation(item) % commonWorryLevel
                    monkey.inspectedItems++
                    // test worry level
                    if (item % monkey.testDivisible == 0L) {
                        monkeys[monkey.ifTrue].items.add(item)
                    } else {
                        monkeys[monkey.ifFalse].items.add(item)
                    }
                }
            }
        }

        val (first, second) = monkeys.map { it.inspectedItems }.sortedDescending().take(2)
        return first.toLong() * second.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11/Day11_test")
    part1(testInput).also {
        println("Test input part1: $it")
        check(it == 10605)
    }
    part2(testInput).also {
        println("Test input part2: $it")
        check(it == 2713310158)
    }


    val input = readInput("Day11/Day11")
    println(part1(input))
    println(part2(input))
}
