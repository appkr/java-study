package dev.appkr.elExercise

interface Command {
    fun execute(): Double
}

class AddCommand(
    private val a: Double,
    private val b: Double,
) : Command {
    override fun execute(): Double = a + b
}

class SubtractCommand(
    private val a: Double,
    private val b: Double,
) : Command {
    override fun execute(): Double = a - b
}

class MultiplyCommand(
    private val a: Double,
    private val b: Double,
) : Command {
    override fun execute(): Double = a * b
}

class DivideCommand(
    private val a: Double,
    private val b: Double,
) : Command {
    override fun execute(): Double = a / b
}
