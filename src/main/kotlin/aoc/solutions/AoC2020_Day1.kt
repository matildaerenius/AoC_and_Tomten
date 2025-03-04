package aoc.solutions

import java.io.File

/**
 * Advent of code 2020
 * Day 1
 * Report Repair
 * Innehåller både part 1 och 2
 */

/*
Löste ett leetcode problem i java som var exakt som denna så gjorde i princip samma sak :D
 */

fun findTwoSum(numbers: List<Int>, target: Int = 2020): Int? {
    val seen = mutableSetOf<Int>()
    for (num in numbers) {
        val complement = target - num
        if (complement in seen) {
            return num * complement
        }
        seen.add(num)
    }
    return null
}

fun findThreeSum(numbers: List<Int>, target: Int = 2020): Int? {
    val sortedNumbers = numbers.sorted()
    val size = sortedNumbers.size

    for (i in 0 until size - 2) {
        var left = i + 1
        var right = size - 1

        while (left < right) {
            val sum = sortedNumbers[i] + sortedNumbers[left] + sortedNumbers[right]
            when {
                sum == target -> return sortedNumbers[i] * sortedNumbers[left] * sortedNumbers[right]
                sum < target -> left++  // Öka vänster om summan är för liten
                else -> right--  // Minska höger om summan är för stor
            }
        }
    }
    return null
}

fun readInput(filename: String): List<Int> {
    return File(filename).readLines().map { it.toInt() }
}

fun main() {
    val filePath = "src/main/resources/aoc/2020_day1_input"
    val numbers = readInput(filePath)

    val part1Result = findTwoSum(numbers)
    val part2Result = findThreeSum(numbers)

    println("Part 1: $part1Result")
    println("Part 2: $part2Result")
}
