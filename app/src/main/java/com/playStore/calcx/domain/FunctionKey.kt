package com.playStore.calcx.domain

data class FunctionKey(
    val primary: String,
    val secondary: String,
    val requiresParenthesis: Boolean = true
)

object FunctionKeys {

    val SIN = FunctionKey("sin", "sinh")
    val COS = FunctionKey("cos", "cosh")
    val TAN = FunctionKey("tan", "tanh")

    val LOG = FunctionKey("log", "log")
    val LN = FunctionKey("ln", "ln")

    val ABS = FunctionKey("abs", "abs")
    val PI = FunctionKey("π", "π")
}
