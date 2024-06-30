package dev.appkr.elExercise

fun main() {
    // (5 + 3) * (2 - 1)
    val expression = MultiplyExpression(
        AddExpression(NumberExpression(5.0), NumberExpression(3.0)),
        SubtractExpression(NumberExpression(2.0), NumberExpression(1.0))
    )

    println("(5 + 3) * (2 - 1): ${expression.interpret()}")
}
