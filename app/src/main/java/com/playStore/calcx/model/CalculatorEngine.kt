package com.playStore.calcx.model

import com.playStore.calcx.engine.CustomFunctions
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorEngine {

    fun evaluate(expression: String): Double? {
        return try {
            // 1) We normalize custom functions (log2 → log(x)/log(2), etc.)
            var expr = normalizeFunctions(expression)

            // 2) FACTORIAL: we process it BEFORE sanitize() and BEFORE exp4j
            if (expr.contains("!")) {

                val factorialRegex = Regex("(\\([^()]*\\)|\\d+(?:\\.\\d+)?)!") //to handling those kinda stuff to factorials operation

                expr = factorialRegex.replace(expr) { match ->

                    val inside = match.groupValues[1]

                    // 1. Evaluate the content first
                    val numeric = try {
                        ExpressionBuilder(inside).build().evaluate()
                    } catch (e: Exception) {
                        return@replace "null"
                    }

                    // 2. Compute factorial safely
                    val fact = safeFactorial(numeric)
                    fact?.toString() ?: "null"
                }
            }

            // 3) We sanitize the expression (insert implicit *, correct parentheses, etc.)
            val sanitized = sanitize(expr)

            // 4) We construct the Expression exp4j without the '!' (because we already solved it)
            val exp = ExpressionBuilder(sanitized)
                .functions(CustomFunctions.all)
                .build()

            // 5) We evaluate
            val result = exp.evaluate()

            // 6) Safe case: avoid returning NaN or infinity
            if (result.isNaN() || result.isInfinite()) null else result

        } catch (e: Exception) {
            null
        }
    }


    private fun safeFactorial(n: Double): Double? {
        if (n < 0) return null
        if(n % 1 != 0.0) return null  //not integer

        var r = 1.0
        for(i in 1..n.toInt()){
            r*= i
        }
        return r
    }

    private fun sanitize(expr: String): String { // turn virtual symbol into real ones to exp4j
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
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
