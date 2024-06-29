package dev.appkr.elExercise

fun main() {
    val parseAddCommand = ParseAddCommand(NumberExpression(5.0), NumberExpression(3.0))
    val parseSubtractCommand = ParseSubtractCommand(NumberExpression(2.0), NumberExpression(1.0))

    val expression = MultiplyExpression(parseAddCommand.execute(), parseSubtractCommand.execute())

    println("(5 + 3) * (2 - 1): ${expression.interpret()}")
}

interface MyCommand {
    fun execute(): Expression
}

class ParseAddCommand(
    private val left: Expression,
    private val right: Expression,
) : MyCommand {
    override fun execute(): Expression = AddExpression(left, right)
}

class ParseSubtractCommand(
    private val left: Expression,
    private val right: Expression,
) : MyCommand {
    override fun execute(): Expression = SubtractExpression(left, right)
}

class ParseMultiplyCommand(
    private val left: Expression,
    private val right: Expression,
) : MyCommand {
    override fun execute(): Expression = MultiplyExpression(left, right)
}

class ParseDivideCommand(
    private val left: Expression,
    private val right: Expression,
) : MyCommand {
    override fun execute(): Expression = DivideExpression(left, right)
}
