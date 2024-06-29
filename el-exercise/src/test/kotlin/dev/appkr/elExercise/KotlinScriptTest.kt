package dev.appkr.elExercise

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.expression.spel.standard.SpelExpressionParser
import javax.script.ScriptEngineManager

class KotlinScriptTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")

        assertThat(scriptEngine.eval(expression)).isEqualTo(11)
    }
}
