package dev.appkr.elExercise

class Parser(expression: String) {
    private val tokens: List<String> = tokenize(expression)
    private var position = 0

    fun parse(): Expression {
        return parseExpression()
    }

    private fun parseExpression(): Expression {
        var left = parseTerm()
        while (position < tokens.size) {
            val operator = tokens[position]
            if (operator == "+" || operator == "-") {
                position++
                val right = parseTerm()
                left = if (operator == "+") {
                    AddExpression(left, right)
                } else {
                    SubtractExpression(left, right)
                }
            } else {
                break
            }
        }
        return left
    }

    private fun parseTerm(): Expression {
        var left = parseFactor()
        while (position < tokens.size) {
            val operator = tokens[position]
            if (operator == "*" || operator == "/") {
                position++
                val right = parseFactor()
                left = if (operator == "*") {
                    MultiplyExpression(left, right)
                } else {
                    DivideExpression(left, right)
                }
            } else {
                break
            }
        }
        return left
    }

    private fun parseFactor(): Expression {
        var left = parsePower()
        while (position < tokens.size) {
            val operator = tokens[position]
            if (operator == "^") {
                position++
                val right = parseFactor()
                left = PowerExpression(left, right)
            } else {
                break
            }
        }
        return left
    }

    private fun parsePower(): Expression {
        val token = tokens[position]
        return when {
            token == "(" -> {
                position++
                val expression = parseExpression()
                position++ // Skip ')'
                expression
            }
            token.matches(Regex("\\d+")) -> {
                position++
                NumberExpression(token.toDouble())
            }
            else -> throw IllegalArgumentException("Unexpected token: $token")
        }
    }

    private fun tokenize(expression: String): List<String> {
        val regex = Regex("\\d+|[-+*/^()]")
        return regex.findAll(expression)
            .map { it.value }
            .toList()
    }
}
