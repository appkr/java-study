## Expression Language Exercise

### SpringEL
```kotlin
class SpringELTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val parser = SpelExpressionParser()
        val exp = parser.parseExpression(expression)

        assertThat(exp.value).isEqualTo(11)
    }
}
```

### Apache JEXL
```kotlin
class ApacheJEXLTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val jexl = JexlBuilder().create()
        val jexlExp = jexl.createExpression(expression)

        assertThat(jexlExp.evaluate(null)).isEqualTo(11)
    }
}
```

### Kotlin Script
```kotlin
class KotlinScriptTest {
    @Test
    fun run() {
        val expression = "3 + 4 * 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")

        assertThat(scriptEngine.eval(expression)).isEqualTo(11)
    }
}
```

### Home-made EL
- [Expression.kt](src/main/kotlin/dev/appkr/elExercise/Expression.kt)
- [Parser.kt](src/main/kotlin/dev/appkr/elExercise/Parser.kt)
- [ParserTest.kt](src/test/kotlin/dev/appkr/elExercise/ParserTest.kt)

```kotlin
class ParserTest {
    @Test
    fun testComplexExpression() {
        val expression = "3 + 4 * 2 / ( 1 - 5 ) ^ 2" // 외부에서 주어진 수식을 시뮬레이션한다

        val evaluatedExpression = Parser(expression).parse()

        assertThat(evaluatedExpression.interpret()).isEqualTo(3.5)
    }
}
```
