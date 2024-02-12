package dev.appkr.kotlincollection

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class MapTest {
    @Test
    fun get() {
        val map = mapOf("key1" to 42)
        assertEquals(42, map.get("key1"))
        assertNull(map.get("key2"))
    }

    @Test
    fun getWithBracket() {
        val map = mapOf("key1" to 42)
        assertEquals(42, map["key1"])
        assertNull(map["key2"])
    }

    @Test
    fun getValue() {
        val map = mapOf("key1" to 42)
        assertEquals(42, map.getValue("key1"))
        assertThrows<NoSuchElementException> {
            map.getValue("key2")
        }
    }

    @Test
    fun withDefault() {
        val map = mapOf("key1" to 42).withDefault { 0 }
        assertEquals(0, map.getValue("key2"))
    }
}
