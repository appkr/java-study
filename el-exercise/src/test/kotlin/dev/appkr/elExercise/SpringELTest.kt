package dev.appkr.elExercise

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.expression.spel.standard.SpelExpressionParser

class SpringELTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val parser = SpelExpressionParser()
        val exp = parser.parseExpression(expression)

        assertThat(exp.value).isEqualTo(11)
    }
}
