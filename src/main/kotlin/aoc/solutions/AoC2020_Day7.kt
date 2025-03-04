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
    val file = File("src/main/resources/aoc/2020_day7_input").readLines().toMutableList()
    val bags = mutableListOf("shiny gold")

    var foundNewBag = true

    while (foundNewBag) {
        foundNewBag = false

        val toRemove = mutableListOf<String>()

        for (line in file) {
            val (outerBag, contents) = line.split(" contain ").map { it.replace("bags", "").trim() }
            if (bags.any { contents.contains(it) }) { // Finns det någon väska i bags som finns i contents?
                bags.add(outerBag)
                toRemove.add(line)
                foundNewBag = true
            }
        }

        file.removeAll(toRemove) // Tar bort alla hittade rader på en gång
    }

    println(bags.size - 1)
}

/*
--Andra approachen--
Efter titt på kotlin lösningar på nedan hemsidor, samt chatGPT rekommendation
https://todd.ginsberg.com/post/advent-of-code/2020/day7/
https://www.reddit.com/r/adventofcode/comments/k8a31f/2020_day_07_solutions/
använder sig majoriteten av datastrukturen graf(map) för att kunna söka på väskor och sedan algoritm rekursiv DFS
för sökning. Insåg nu i efter hand att istället för rekursiv dfs kan vi använda en stack, ungår stack overflow
 */
fun solutionDFS(): Int {
    val input = File("src/main/resources/aoc/2020_day7_input").readLines()
    val containsMap = mutableMapOf<String, MutableList<String>>() // Väskfärg || lista över väskor som kan innehålla den färg

    for (line in input) {
        val parts = line.split(" bags contain ")
        val outerBag = parts[0]
        val innerBags = parts[1].split(", ")

    for (innerBag in innerBags) {
        val match = Regex("(\\d+) (.+?) bag").find(innerBag) // Tar ut antal och färgnamn
        if (match != null) {
            val (_, innerBagColor) = match.destructured
            containsMap.computeIfAbsent(innerBagColor) { mutableListOf() }.add(outerBag) // Lägger till outerbag som kan innehålla en innerbagcolor
        }
    }
}

val visited = mutableSetOf<String>() // Håller reda på väskor vi kollat

fun dfs(bag: String) {
    containsMap[bag]?.forEach { outerBag -> // Hämtar alla väskor som kan innehålla bag
        if (visited.add(outerBag)) {
            dfs(outerBag)
        }
    }
}

dfs("shiny gold") // Startar från "shiny gold"

return visited.size
}

/*
--Tredje approachen--
Efter att ha läst på om vanliga datastrukturer och algoritmer,
bör man även kunna lösa denna med graf(map), queue och BFS, jag hittade inga kotlin
lösningar på detta sätt, lär ju definitivt finnas men men nedan är mitt försök på en sådan lösning
 */
fun solutionBFS(): Int {
    val input = File("src/main/resources/aoc/2020_day7_input").readLines()

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
    val visited = mutableSetOf<String>() // Håller reda på vilka väskor vi redan har besökt för att undvika dubbletter

    queue.add("shiny gold") // Startar bfs från "shiny gold"

    while (queue.isNotEmpty()) {
        val currentBag = queue.poll() // Hämtar och tar bort första i queue, dvs fifo
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
dvs en immutable graf(map) + rekursiv + fold + HOF
nedan är basically min tillsammans med chatGPT minnes variation på ish hur koden såg ut, hittar ej repot :(
 */
fun solutionfunc(): Int {
    val file = File("src/main/resources/aoc/2020_day7_input").readLines()
        val containsMap = file
            .flatMap { line -> // Kör igenom varje kodrad och drar ut vilka väskor som kan innehålla andra väskor
                val (outerBag, innerBags) = line.split(" bags contain ")
                innerBags.split(", ").mapNotNull { innerBag ->
                    Regex("(\\d+) (.+?) bag").find(innerBag)?.groupValues?.let { it[2] to outerBag } // Lista av par, inreväskan/yttreväskan
                }
            }
            .groupBy({ it.first }, { it.second }) // Grupperar i en map, väskfärg och listan av väskor som kan innehålla färgen

        // Rekursiv funk (liknar DFS), hittar alla väskor som kan innehålla en "shiny bag"
        fun findOuterBags(bag: String, visited: Set<String> = emptySet()): Set<String> =
            containsMap[bag]?.filterNot { it in visited } // Hämtar väskor som kan innehålla bag
                ?.fold(visited) { acc, outerBag -> acc + findOuterBags(outerBag, acc + outerBag) } // Bygger ny set av besökta väskor -> lägger till rekursivt
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