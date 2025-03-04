package aoc.solutions

import java.io.File

/**
 * Advent of code 2021
 * Day 12
 * Passage Pathing
 * OBS : Enbart part 1
 */

// Part 1

/*
Löste denna främst med hjälp av hur jag såg/lärt mig strukturen på 2020 day 7, men även reddit användes :)
 */

fun buildGraph(input: List<String>): Map<String, List<String>> {
    return input
        .map { it.split("-") }
        .flatMap { (a, b) -> listOf(a to b, b to a) } // Skapar kopplingar i båda riktningarna
        .groupBy({ it.first }, { it.second }) // Omvandlar till Map, där nyckeln = grottan och värdet = grannar
}

fun countPaths(graph: Map<String, List<String>>): Int {
    return smallCaveAllowed(graph, "start", mutableSetOf()) // Startar sökningen från start
}

fun smallCaveAllowed(graph: Map<String, List<String>>, current: String, visited: MutableSet<String>) : Int {
    if (current == "end") return 1 // Nås "end", returnera 1 för att indikera en giltig väg

    if (current.all { it.isLowerCase() }) visited.add(current) // Markerar små grottor som besökta

    val pathCount = graph[current]!!
        .filter { it !in visited || it.all { ch -> ch.isUpperCase() } } // Filtrerar så att vi inte besöker små grottor mer än en gång
        .sumOf { smallCaveAllowed(graph, it, HashSet(visited)) } // Anropa rekursivt och summera antalet hittade vägar

    return pathCount
}

fun main() {
    val file = File("src/main/resources/aoc/day12_input").readLines()
    val graph = buildGraph(file)
    val totalPaths = countPaths(graph)
    println("Antal unika vägar: $totalPaths")
}

/*
Lösningar:
https://www.reddit.com/r/adventofcode/comments/rehj2r/2021_day_12_solutions/
https://todd.ginsberg.com/post/advent-of-code/2021/day12/
 */