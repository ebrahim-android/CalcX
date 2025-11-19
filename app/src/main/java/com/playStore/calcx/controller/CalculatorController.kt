package com.playStore.calcx.controller

import androidx.compose.runtime.mutableStateOf

class CalculatorController {
    //To show the principal display (0, 123, sin(1) so on...)
    private val _displayState = mutableStateOf("0")
    val displayState get() = _displayState

    // Controller internal status
    private var expression = ""
    private var shouldReset = false

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
    }

    // when the user presses "="
    fun onEqualsPressed() {
    }

    // when the user presses "C" (Clear)
    fun onClearPressed() {
    }

    // when the user presses "eliminate symbol" (backspace)
    fun onDeleteLast() {
    }

    // when the user presses "(" o ")"
    fun onParenthesisPressed(parenthesis: String) {
    }

}