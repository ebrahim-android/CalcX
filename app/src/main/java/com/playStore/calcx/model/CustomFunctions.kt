package com.playStore.calcx.engine

import net.objecthunter.exp4j.function.Function
import kotlin.math.*

object CustomFunctions { // Custom functions for the calculator (we're expending exp4j library, so we need to add our own functions)

    val log2 = object : Function("log2", 1) {
        override fun apply(vararg args: Double): Double {
            return ln(args[0]) / ln(2.0)
        }
    }

    val cot = object : Function("cot", 1) {
        override fun apply(vararg args: Double): Double {
            return 1.0 / tan(args[0])
        }
    }

    val sec = object : Function("sec", 1) {
        override fun apply(vararg args: Double): Double {
            return 1.0 / cos(args[0])
        }
    }

    val csc = object : Function("csc", 1) {
        override fun apply(vararg args: Double): Double {
            return 1.0 / sin(args[0])
        }
    }

    val all = listOf(log2, cot, sec, csc)
}
