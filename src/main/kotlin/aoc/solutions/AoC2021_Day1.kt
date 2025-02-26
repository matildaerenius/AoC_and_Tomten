package aoc.solutions

import java.io.File

/**
 * Advent of code 2021
 * Day 1
 * Sonar sweep
 */

/*
 Part 1
 Räknar hur många gånger ett tal i listan är mindre än nästa tal
 */
fun countTimesItIncreases(list: List<Int>) : Int{
    var count = 0
    for (i in 0..<list.size-1){
        if (list[i] < list[i+1]) count++
    }
    return count
}

/*
 Part 2
 Jämför summan av tre på varandra följande värden och räknar hur många gånger summan ökar
 */
fun threeMeasurementWindows(list : List<Int>) : Int{
    var count = 0
    for (i in 0..<list.size-3){
        val sum1 = list[i] + list[i+1] + list[i+2]
        val sum2 = list[i+1] + list[i+2] + list[i+3]
        if (sum1 < sum2){
            count++
        }
    }
    return count
}

fun main() {
    val file = "src/main/kotlin/aoc/data/day1_input"
    val list: List<Int> = (File(file).readLines().map { it.toInt() })
    // part 1
    val result1 = countTimesItIncreases(list)
    println("Antal ökningar: $result1")
    // part 2
    val result2 = threeMeasurementWindows(list)
    println("Antal ökningar i summor: $result2")
}

/*
Efter att ha löst problemet hittade jag dessa lösningar:

https://todd.ginsberg.com/post/advent-of-code/2021/day1/
    // part 1
    fun solvePart1(): Int =
        input
            .zipWithNext()
            .count { it.first < it.second }

    // part 2
    fun solvePart2(): Int =
    input
        .windowed(3, 1) { it.sum() }
        .zipWithNext()
        .count { it.first < it.second }

https://www.reddit.com/r/adventofcode/comments/r66vow/2021_day_1_solutions/?rdt=38768
    // part 1
    solve { input ->
    input.lines().map { it.toInt() }.windowed(2).count { it.last() > it.first() }
}
    // part 2
    solve { input ->
    input.lines().map { it.toInt() }.windowed(4).count { it.last() > it.first() }
}
 */