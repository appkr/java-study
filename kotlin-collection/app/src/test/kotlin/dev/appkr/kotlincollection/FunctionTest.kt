package dev.appkr.kotlincollection

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class FunctionTest {
    @Test
    fun nullSafeToString() {
        fun <T> T?.nullSafeToString() = this?.let { it.toString() } ?: ""

        var actual = null.nullSafeToString()
        assertEquals("", actual)

        actual = Order(emptyList()).nullSafeToString() // Order(items=[])
        assertNotEquals(actual, "")
    }
}
