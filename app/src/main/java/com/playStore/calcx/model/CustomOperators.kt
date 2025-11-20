package com.playStore.calcx.model

import net.objecthunter.exp4j.operator.Operator

object CustomOperators {

    // factorial (Ej: 5! = 120)
    val factorial = object : Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
        override fun apply(vararg args: Double): Double {
            val n = args[0].toInt()
            var result = 1
            for (i in 1..n) {
                result *= i
            }
            return result.toDouble()
        }
    }

    val all = listOf(factorial)

}