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
    }
