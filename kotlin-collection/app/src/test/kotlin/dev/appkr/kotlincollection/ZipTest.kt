package dev.appkr.kotlincollection

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ZipTest {
    @Test
    fun zip() {
        val a = listOf("a", "b", "c")
        val b = listOf(1, 2, 3, 4)
        val expected = listOf("a" to 1, "b" to 2, "c" to 3)
        assertEquals(expected, a zip b)
    }

    @Test
    fun zipWithTransform() {
        val a = listOf("a", "b", "c")
        val b = listOf(1, 2, 3, 4)
        val actual = a.zip(b) { a, b -> "$a$b" }
        assertEquals(listOf("a1", "b2", "c3"), actual)
    }
}
