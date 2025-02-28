package aoc.solutions

import java.io.File
import java.util.*

/**
 * Advent of code 2020
 * Day 7
 * Handy haversacks
 * OBS : Enbart part 1
 */

// Part 1

/*
--Första approachen--
minimal/ingen internet hjälp.
Använde en while-loop som söker efter väskor som kan innehålla "shiny gold",
kontrollerar innehållet med any, och vid match lägger till väskan i bags
 */
fun solutionWhileLoop() {
    val file = File("src/main/resources/aoc/day7_input").readLines().toMutableList()
    val bags = mutableListOf("shiny gold")

    var foundNewBag = true

    while (foundNewBag) {
        foundNewBag = false

        val toRemove = mutableListOf<String>()

        for (line in file) {
            val (bag, contents) = line.split(" contain ").map { it.replace("bags", "").trim() }
            if (bags.any { contents.contains(it) }) {
                bags.add(bag)
                toRemove.add(line)
                foundNewBag = true
            }
        }

        file.removeAll(toRemove) // Tar bort alla funna rader på en gång
    }

    println(bags.size - 1)
}

/*
--Andra approachen--
Efter titt på kotlin lösningar på nedan hemsidor, samt chatGPT rekommendation
https://todd.ginsberg.com/post/advent-of-code/2020/day7/
https://www.reddit.com/r/adventofcode/comments/k8a31f/2020_day_07_solutions/
använder sig majoriteten av datastrukturen graf(map) för att kunna söka på väskor och sedan algoritm rekursiv DFS
 */
fun solutionDFS(): Int {
    val input = File("src/main/resources/aoc/day7_input").readLines()
    val containsMap = mutableMapOf<String, MutableList<String>>()

    for (line in input) {
        val parts = line.split(" bags contain ")
        val outerBag = parts[0]
        val innerBags = parts[1].split(", ")

    for (innerBag in innerBags) {
        val match = Regex("(\\d+) (.+?) bag").find(innerBag)
        if (match != null) {
            val (_, innerBagColor) = match.destructured
            containsMap.computeIfAbsent(innerBagColor) { mutableListOf() }.add(outerBag)
        }
    }
}

val visited = mutableSetOf<String>()

fun dfs(bag: String) {
    containsMap[bag]?.forEach { outerBag ->
        if (visited.add(outerBag)) {
            dfs(outerBag)
        }
    }
}

dfs("shiny gold")

return visited.size
}

/*
--Tredje approachen--
Efter att ha läst på om vanliga datastrukturer och algoritmer,
bör man även kunna lösa denna med graf(map), queue och BFS, jag hittade inga kotlin
lösningar på detta sätt, lär ju definitivt finnas men men
nedan är mitt försök på en sådan lösning
 */
fun solutionBFS(): Int {
    val input = File("src/main/resources/aoc/day7_input").readLines()

    val containsMap = mutableMapOf<String, MutableList<String>>()

    for (line in input) {
        val parts = line.split(" bags contain ")
        val outerBag = parts[0]
        val innerBags = parts[1].split(", ")

        for (innerBag in innerBags) {
            val match = Regex("(\\d+) (.+?) bag").find(innerBag)
            if (match != null) {
                val (_, innerBagColor) = match.destructured
                containsMap.computeIfAbsent(innerBagColor) { mutableListOf() }.add(outerBag)
            }
        }
    }

    val queue: Queue<String> = LinkedList()
    val visited = mutableSetOf<String>()

    queue.add("shiny gold")

    while (queue.isNotEmpty()) {
        val currentBag = queue.poll()
        containsMap[currentBag]?.forEach { outerBag ->
            if (visited.add(outerBag)) {
                queue.add(outerBag)
            }
        }
    }

    return visited.size
}

/*/
--Fjärde approachen--
Efter genomgång av diverse github repon, fann jag en lösning gjord på "ren" funktionell programmering
dvs en immutable graf(map) + rekursiv + fold
nedan är basically min tillsammans med chatGPT minnes variation på hur koden såg ut, hittar ej repot :(
 */
fun solutionfunc(): Int {
    val file = File("src/main/resources/aoc/day7_input").readLines()
        val containsMap = file
            .flatMap { line ->
                val (outerBag, innerBags) = line.split(" bags contain ")
                innerBags.split(", ").mapNotNull { innerBag ->
                    Regex("(\\d+) (.+?) bag").find(innerBag)?.groupValues?.let { it[2] to outerBag }
                }
            }
            .groupBy({ it.first }, { it.second })

        fun findOuterBags(bag: String, visited: Set<String> = emptySet()): Set<String> =
            containsMap[bag]?.filterNot { it in visited }
                ?.fold(visited) { acc, outerBag -> acc + findOuterBags(outerBag, acc + outerBag) }
                ?: visited

        return findOuterBags("shiny gold").size
    }


fun main() {
    println("===== Lösning 1: While-loop =====")
    solutionWhileLoop()

    println("\n===== Lösning 2: Rekursiv DFS =====")
    println(solutionDFS())

    println("\n===== Lösning 3: BFS med Queue =====")
    println(solutionBFS())

    println("\n===== Lösning 4: Ren funktionell programmering =====")
    println(solutionfunc())
}

/*
--Jämförelse av de olika lösningarna--

Lösning 1 :
pros- Enligt mig lättast att förstå, använder ingen rekursiv vilket undgår stack overflow
cons- ineffektivt då den går igenom listan flera gånger, mutable

Lösning 2 :
pros - effektiv, renare kod
cons - rekursiv, mutable

Lösning 3 :
pros - minnes effektivt till skillnad mot dfs, undgår djup rekursiv
cons - mutable

Lösning 4 :
pros - helt immutable, inga mutable
cons - rekursiv, enligt mig svårast att läsa
 */