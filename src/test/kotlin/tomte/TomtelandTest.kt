package tomte

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TomtelandTest {

    private val tl = Tomteland()

    private val uTomten = listOf("Tröger", "Trötter", "Blyger", "Dammråttan", "Skumtomten", "Glader",
        "Butter", "Rådjuret", "Nyckelpigan", "Haren", "Räven", "Gråsuggan", "Myran", "Bladlusen")
    private val uGlader = listOf("Tröger", "Trötter", "Blyger", "Dammråttan", "Skumtomten")
    private val uButter = listOf("Rådjuret", "Nyckelpigan", "Haren", "Räven", "Gråsuggan", "Myran", "Bladlusen")
    private val uTrötter = listOf("Dammråttan", "Skumtomten")
    private val uSkumtomten = listOf("Dammråttan")
    private val uRäven = listOf("Gråsuggan", "Myran", "Bladlusen")
    private val uMyran = listOf("Bladlusen")

    @Test
    fun `test underlings of Bladlusen`() {
        val underlings = tl.getUnderlings("Bladlusen", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Tröger`() {
        val underlings = tl.getUnderlings("Tröger", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Blyger`() {
        val underlings = tl.getUnderlings("Blyger", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Dammråttan`() {
        val underlings = tl.getUnderlings("Dammråttan", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Gråsuggan`() {
        val underlings = tl.getUnderlings("Gråsuggan", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Haren`() {
        val underlings = tl.getUnderlings("Haren", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Nyckelpigan`() {
        val underlings = tl.getUnderlings("Nyckelpigan", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Rådjuret`() {
        val underlings = tl.getUnderlings("Rådjuret", mutableListOf())
        assertEquals(0, underlings.size)
    }

    @Test
    fun `test underlings of Myran`() {
        val underlings = tl.getUnderlings("Myran", mutableListOf())
        assertEquals(uMyran.size, underlings.size)
        assertTrue(underlings.containsAll(uMyran))
    }

    @Test
    fun `test underlings of Räven`() {
        val underlings = tl.getUnderlings("Räven", mutableListOf())
        assertEquals(uRäven.size, underlings.size)
        assertTrue(underlings.containsAll(uRäven))
    }

    @Test
    fun `test underlings of Skumtomten`() {
        val underlings = tl.getUnderlings("Skumtomten", mutableListOf())
        assertEquals(uSkumtomten.size, underlings.size)
        assertTrue(underlings.containsAll(uSkumtomten))
    }

    @Test
    fun `test underlings of Trötter`() {
        val underlings = tl.getUnderlings("Trötter", mutableListOf())
        assertEquals(uTrötter.size, underlings.size)
        assertTrue(underlings.containsAll(uTrötter))
    }

    @Test
    fun `test underlings of Butter`() {
        val underlings = tl.getUnderlings("Butter", mutableListOf())
        assertEquals(uButter.size, underlings.size)
        assertTrue(underlings.containsAll(uButter))
    }

    @Test
    fun `test underlings of Glader`() {
        val underlings = tl.getUnderlings("Glader", mutableListOf())
        assertEquals(uGlader.size, underlings.size)
        assertTrue(underlings.containsAll(uGlader))
    }

    @Test
    fun `test underlings of Tomten`() {
        val underlings = tl.getUnderlings("Tomten", mutableListOf())
        assertEquals(uTomten.size, underlings.size)
        assertTrue(underlings.containsAll(uTomten))
    }
}
