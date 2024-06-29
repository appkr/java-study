package dev.appkr.elExercise

fun main() {
    // (5 + 3) * (2 - 1)
    val expression = MultiplyExpression(
        AddExpression(NumberExpression(5.0), NumberExpression(3.0)),
        SubtractExpression(NumberExpression(2.0), NumberExpression(1.0))
    )

    println("(5 + 3) * (2 - 1): ${expression.interpret()}")
}

interface Expression {
    fun interpret(): Double
}

class NumberExpression(
    private val number: Double,
) : Expression {
    override fun interpret(): Double = number
}

class AddExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(): Double = left.interpret() + right.interpret()
}

class SubtractExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(): Double = left.interpret() - right.interpret()
}

class MultiplyExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(): Double = left.interpret() * right.interpret()
}

class DivideExpression(
    private val left: Expression,
    private val right: Expression,
) : Expression {
    override fun interpret(): Double = left.interpret() / right.interpret()
}
