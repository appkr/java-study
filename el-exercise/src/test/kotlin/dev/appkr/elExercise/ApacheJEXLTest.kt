package dev.appkr.elExercise

import org.apache.commons.jexl3.JexlBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ApacheJEXLTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val jexl = JexlBuilder().create()
        val jexlExp = jexl.createExpression(expression)

        assertThat(jexlExp.evaluate(null)).isEqualTo(11)
    }
}
