package com.playStore.calcx.domain

data class FunctionKey(
    val primary: String,
    val secondary: String,
    val requiresParenthesis: Boolean = true
)

val SIN_KEY = FunctionKey("sin", "sinh")
val COS_KEY = FunctionKey("cos", "cosh")
val TAN_KEY = FunctionKey("tan", "tanh")

val LOG_KEY = FunctionKey("log", "10^", requiresParenthesis = false)
val LN_KEY = FunctionKey("ln", "e^", requiresParenthesis = false)

val ABS_KEY = FunctionKey("abs", "abs") //so far
val PI_KEY = FunctionKey("π", "π", requiresParenthesis = false)

val key = FunctionKey(
    primary = "sin",
    secondary = "sinh",
    requiresParenthesis = true
)
