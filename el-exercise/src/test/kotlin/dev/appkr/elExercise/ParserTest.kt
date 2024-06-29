package dev.appkr.elExercise

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun testSimpleAddition() {
        val parser = Parser("3 + 4")
        val result = parser.parse().interpret()
        assertEquals(7.0, result)
    }

    @Test
    fun testSimpleSubtraction() {
        val parser = Parser("10 - 3")
        val result = parser.parse().interpret()
        assertEquals(7.0, result)
    }

    @Test
    fun testSimpleMultiplication() {
        val parser = Parser("2 * 5")
        val result = parser.parse().interpret()
        assertEquals(10.0, result)
    }

    @Test
    fun testSimpleDivision() {
        val parser = Parser("15 / 3")
        val result = parser.parse().interpret()
        assertEquals(5.0, result)
    }

    @Test
    fun testPowerOperation() {
        val parser = Parser("2 ^ 3")
        val result = parser.parse().interpret()
        assertEquals(8.0, result)
    }

    @Test
    fun testComplexExpression() {
        val parser = Parser("3 + 4 * 2 / ( 1 - 5 ) ^ 2")
        val result = parser.parse().interpret()
        assertEquals(3.5, result, 1e-10)
    }

    @Test
    fun testNestedParentheses() {
        val parser = Parser("(3 + 4) * (2 + 2)")
        val result = parser.parse().interpret()
        assertEquals(28.0, result)
    }

    @Test
    fun testMultipleOperations() {
        val parser = Parser("2 * 3 + 4 / 2")
        val result = parser.parse().interpret()
        assertEquals(8.0, result)
    }

    @Test
    fun testComplexNestedExpression() {
        val parser = Parser("2 * (3 + 4) / (2 ^ 2)")
        val result = parser.parse().interpret()
        assertEquals(3.5, result)
    }
}
