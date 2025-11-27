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
    var e = expr

    // --- TRIG INVERSE FUNCTIONS ---
    e = e.replace("arcsin", "asin")
        .replace("arccos", "acos")
        .replace("arctan", "atan")

    // --- POWERS (x², x³, x^n…) ---
    e = e.replace("x²", "^(2)")
        .replace("x^", "^")

    // it is important to ensure that ^3 → ^(3)
    e = e.replace(Regex("\\^(\\d+)")) { match ->
        "^(${match.groupValues[1]})"
    }

    // --- ROOTS ---
    e = e.replace("√x", "sqrt")
        .replace("√", "sqrt")

    // --- LOGARITHMS ---
    e = e.replace("log", "log10")
        .replace("ln", "log")

    // --- FACTORIAL ---
    e = e.replace("n!", "fact")

    // --- CONSTANTS ---
    e = e.replace("π", "pi")

    // standalone e (avoid replacing inside "exp")
    e = e.replace(Regex("\\be\\b"), "2.718281828")

    // --- ABSOLUTE VALUE ---
    e = e.replace("abs", "abs")

    // --- PERCENT (50% → (50/100)) ---
    e = e.replace(Regex("(\\d+(?:\\.\\d+)?)%")) {
        val num = it.groupValues[1]
        "($num/100)"
    }

    return e
}
