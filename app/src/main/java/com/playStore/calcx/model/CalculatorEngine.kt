package com.playStore.calcx.model

import com.playStore.calcx.engine.CustomFunctions
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorEngine {

    fun evaluate(expression: String): Double? {
        return try {
            // evaluate the expression and return the result (double)
            val normalized = normalizeFunctions(expression)
            val sanitized = sanitize(normalized)

            val exp = ExpressionBuilder(sanitized)
                // add custom functions and operators
                .functions(CustomFunctions.all)
                .operator(CustomOperators.all)
                .build()

            val result = exp.evaluate()

            if (result.isNaN() || result.isInfinite()) null else result
        } catch (e: Exception) {
            null
        }
    }


    private fun sanitize(expr: String): String { // turn virtual symbol into real ones to exp4j
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("√", "sqrt")
//        .replace("%", "/100")   // so far
            .trim()
    }
}

private fun normalizeFunctions(expr: String): String {
    return expr
        // powers
        .replace("x²", "^(2)")
        .replace("x^", "^")

        // Roots
        .replace("√x", "sqrt")
        .replace("√", "sqrt")

        // Logarithms
        .replace("log", "log10")
        .replace("ln", "log")

        // Factorial
        .replace("n!", "fact")

        // Constants
        .replace("π", "pi")

        // absolute value
        .replace("abs", "abs")
}