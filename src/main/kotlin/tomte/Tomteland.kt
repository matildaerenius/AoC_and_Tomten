package tomte
/**
 * VG -uppgift
 * Funktionell programmering JAVA24
 */

class Tomteland {

    // TODO : Gör datastruktur, en map
    private val hierarchy: Map<String, List<String>> = mapOf(
        "Tomten" to listOf("Glader", "Butter"),
        "Glader" to listOf("Tröger", "Trötter", "Blyger"),
        "Butter" to listOf("Rådjuret", "Nyckelpigan", "Haren", "Räven"),
        "Trötter" to listOf("Skumtomten"),
        "Skumtomten" to listOf("Dammråttan"),
        "Räven" to listOf("Gråsuggan", "Myran"),
        "Myran" to listOf("Bladlusen")
    )

    // TODO : Gör rekursiv funktion för att hämta alla underordnade på samtliga nivåer
    fun getUnderlings(currentName: String, res: MutableList<String>): List<String> {
        hierarchy[currentName]?.forEach { subordinate ->
            res.add(subordinate)
            getUnderlings(subordinate, res)
        }
        return res
    }
}

    // TODO : Main metod + inmatning
    fun main() {
        val tomteland = Tomteland()

            while (true) {
                println("\nAnge en tomte att söka på ('exit' för att avsluta):")
                val input = readLine()?.trim()

                if (input.equals("exit", ignoreCase = true)) {
                    println("Avslutar programmet...")
                    break
                }

                if (input.isNullOrBlank()) {
                    println("Felaktig inmatning, försök igen.")
                    continue
                }

                val underlings = tomteland.getUnderlings(input, mutableListOf())

                if (underlings.isEmpty()) {
                    println("$input har inga underordnade eller finns inte i hierarkin.")
                } else {
                    println("Underordnade till $input: ${underlings.joinToString(", ")}")
                }
            }
        }


