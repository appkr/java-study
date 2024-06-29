package dev.appkr.elExercise

import kotlin.math.pow

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

class PowerExpression(
    private val base: Expression,
    private val exponent: Expression,
) : Expression {
    override fun interpret(): Double = base.interpret().pow(exponent.interpret())
}
