package dev.appkr.elExercise

fun main() {
    println("Addition:       ${AddCommand(5.0, 3.0).execute()}")
    println("Subtraction:    ${SubtractCommand(5.0, 3.0).execute()}")
    println("Multiplication: ${MultiplyCommand(5.0, 3.0).execute()}")
    println("Division:       ${DivideCommand(5.0, 3.0).execute()}")
}
