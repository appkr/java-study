package dev.appkr.elExercise

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ParserTest {
    @Test
    fun testSimpleAddition() {
        val expression = "3 + 4" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(7.0)
    }

    @Test
    fun testSimpleSubtraction() {
        val expression = "10 - 3" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(7.0)
    }

    @Test
    fun testSimpleMultiplication() {
        val expression = "2 * 5" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(10.0)
    }

    @Test
    fun testSimpleDivision() {
        val expression = "15 / 3" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(5.0)
    }

    @Test
    fun testPowerOperation() {
        val expression = "2 ^ 3" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(8.0)
    }

    @Test
    fun testComplexExpression() {
        val expression = "3 + 4 * 2 / ( 1 - 5 ) ^ 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(3.5)
    }

    @Test
    fun testNestedParentheses() {
        val expression = "(3 + 4) * (2 + 2)" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(28.0)
    }

    @Test
    fun testMultipleOperations() {
        val expression = "2 * 3 + 4 / 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(8.0)
    }

    @Test
    fun testComplexNestedExpression() {
        val expression = "2 * (3 + 4) / (2 ^ 2)" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(3.5)
    }
}
