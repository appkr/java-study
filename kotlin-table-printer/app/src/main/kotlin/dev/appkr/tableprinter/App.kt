package dev.appkr.tableprinter

import de.m3y.kformat.Table
import de.m3y.kformat.table

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val render = table {
        header("A", "B", "C", "Long_Header")

        row(10, "b...1", 2.1f, "foo")
        row(20, "b2", 1 / 3f, "bar")

        hints {
            alignment("A", Table.Hints.Alignment.LEFT)
            precision("C", 2)
            postfix("C", "%")
            borderStyle = Table.BorderStyle.SINGLE_LINE // or NONE
        }
    }.render(StringBuilder())

    println(render)
}
