package Day7

import readInput

class File(val name: String, val size: Int)
class Folder(val name: String, val subfolders: MutableList<Folder>, val files: MutableList<File>, val parent: Folder?) {
    fun folderSize(): Int {
        var size = files.sumOf { it.size }
        return size + subfolders.sumOf { it.folderSize() }
    }

    fun findSubfolders(size: Int): List<Folder> {
        val sizedFolders = subfolders.flatMap { it.findSubfolders(size) }
        return if (folderSize() <= size) {
            sizedFolders + this
        } else {
            sizedFolders
        }
    }
}

fun main() {

    fun parseInput(input: List<String>): Folder? {
        var current: Folder?  = null

        for (line in input) {
            if (line == "\$ cd /") {
                val root = Folder(name = "/", subfolders = mutableListOf(), files = mutableListOf(), parent = null)
                current = root
            } else if (line.startsWith("\$ cd ")) {
                val where = line.substringAfter("\$ cd ")
                if (where == "..") {
                    current = current?.parent
                } else {
                    current = current?.subfolders?.first { it.name == where }
                }
            } else if (line ==  "\$ ls") {
                // do nothing
            } else {
                val (first, second) = line.split(' ')
                if (first == "dir") {
                    current?.subfolders?.add(Folder(name = second, mutableListOf(), mutableListOf(), parent = current))
                } else {
                    current?.files?.add(File(name = second, size = first.toInt()))
                }

            }
        }
        while (current?.parent != null) {
            current = current.parent
        }
        return current
    }

    fun part1(input: List<String>): Int {
        val rootFolder = parseInput(input)

        return rootFolder?.findSubfolders(100000)?.sumBy { it.folderSize() } ?: 0
    }

    fun part2(input: List<String>): Int {
        val rootFolder = parseInput(input)

        val diskSpace = 70000000
        val updateSpace = 30000000
        val freeSpace = diskSpace - rootFolder!!.folderSize()
        val requiredSpace = updateSpace - freeSpace

        return rootFolder.findSubfolders(Int.MAX_VALUE).filter { it.folderSize() >= requiredSpace }.minOf { it.folderSize() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day7/Day7_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day7/Day7")
    println(part1(input))
    println(part2(input))
}