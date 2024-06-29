package dev.appkr.elExercise

fun main() {
    println("Addition:       ${AddCommand(5.0, 3.0).execute()}")
    println("Subtraction:    ${SubtractCommand(5.0, 3.0).execute()}")
    println("Multiplication: ${MultiplyCommand(5.0, 3.0).execute()}")
    println("Division:       ${DivideCommand(5.0, 3.0).execute()}")
}

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
