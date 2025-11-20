package com.playStore.calcx.controller

import androidx.compose.runtime.mutableStateOf
import com.playStore.calcx.model.CalculatorEngine

class CalculatorController {
    //To show the principal display (0, 123, sin(1) so on...)
    private val _displayState = mutableStateOf("0")
    val displayState get() = _displayState

    // Controller internal status
    private var expression = ""
    private var shouldReset = false
    private val engine = CalculatorEngine()

    // when the user presses a digit (1, 5 or 8)
    fun onDigitPressed(digit: String) {
        if (shouldReset) {//to reset the display is the user pressed "="
            _displayState.value = digit
            expression = digit
            shouldReset = false
            return
        }

        if (_displayState.value == "0") { //to replaced the "0" with the first digit pressed
            _displayState.value = digit
            expression = digit
            return
        }

        //to add the digit to the display next to the previous digit (normal)
        _displayState.value += digit
        expression += digit
    }

    // when the user presses an operator (+, -, *, /, ^)
    fun onOperatorPressed(operator: String) {
        if (shouldReset) {
            shouldReset = false
        }

        if (expression.isEmpty()) return //is the expression is empty

        //to check if the last character is an operator
        val lastChar = expression.last()
        if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/' || lastChar == '^') {
            expression = expression.dropLast(1) + operator
            return
        }
        expression += operator //normal case (just add)

        _displayState.value = expression

    }


    // when the user presses a function button (sin, cos, ln, etc.)
    fun onFunctionPressed(function: String) {
        if (shouldReset) {
            expression = ""
            _displayState.value = "0"
            shouldReset = false
        }

        expression += function + "("
        _displayState.value = expression
    }


    // when the user presses "="
    fun equalsPressed() {
        try {
            val result = engine.evaluate(expression)
            _displayState.value = result.toString()
            expression = result.toString()
            shouldReset = true

        } catch (e: Exception) {
            _displayState.value = "Error"
            expression = ""
            shouldReset = true
        }
    }

    // when the user presses "C" (Clear)
    fun onClearPressed() {
        expression = ""
        _displayState.value = "0"
        shouldReset = false
    }

    // when the user presses "eliminate symbol" (backspace)
    fun onDeleteLast() {
        if (shouldReset) {
            expression = ""
            _displayState.value = "0"
            shouldReset = false
            return
        }

        if (expression.isNotEmpty()) { //if the expression is not empty, it will remove the last character
            expression = expression.dropLast(1)
        }

        if (expression.isEmpty()) { //if the expression is empty, it will show "0"
            expression = "0"
        } else {
            _displayState.value = expression //if the expression is not empty, it will show the expression
        }
    }

    // when the user presses "(" o ")"
    fun onParenthesisPressed(parenthesis: String) {
        if (shouldReset) {
            expression = ""
            shouldReset = false
        }

        expression += parenthesis
        _displayState.value = expression
    }

    fun onDecimalPointPressed() {
        if (shouldReset) {
            expression = "0."
            _displayState.value = expression
            shouldReset = false
            return
        }

        if (expression.isEmpty()) {
            expression = "0."
            _displayState.value = expression
            return
        }

        val lastNumber = expression.takeLastWhile { it.isDigit() || it == '.' }

        if (lastNumber.contains(".")) {
            return
        }

        val lastChar = expression.last()
        if (!lastChar.isDigit() && lastChar != '.') {
            expression += "0."
            _displayState.value = expression
            return
        }

        expression += "."
        _displayState.value = expression
    }

}