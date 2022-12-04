import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputAsText(name: String) = File("src", "$name.txt").readText()

fun readInputAsInts(name: String) = File("src", "$name.txt").readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun IntRange.containsFull(intRange: IntRange): Boolean {
    return (this.first <= intRange.first && this.last >= intRange.last)
}

fun IntRange.overlaps(intRange: IntRange): Boolean {
    for (element in intRange) {
        if (this.contains(element)) {
            return true
        }
    }
    return false
}