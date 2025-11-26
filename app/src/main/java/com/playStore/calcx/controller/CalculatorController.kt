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

    fun mapOperator(op: String): String { // convert UI symbols into real math operators for the engine
        return when(op){
            "×" -> "*"
            "÷" -> "/"
            "−" -> "-"
            else -> op
        }
    }

    // when the user presses an operator (+, -, *, /, ^)
    fun onOperatorPressed(operator: String) {
        val op = mapOperator(operator)

        if (shouldReset) shouldReset = false

        if (expression.isEmpty()) return

        val last = expression.last()

        // If last char is operator, replace it (avoid ++, +×, etc.)
        if("+-*/^".contains(last)){
            expression = expression.dropLast(1) + op
        }else{
            expression += op
        }

        _displayState.value = expression
    }


    // when the user presses a function button (sin, cos, ln, etc.)
    fun onFunctionPressed(function: String) {
        if (shouldReset) {
            expression = ""
            _displayState.value = "0"
            shouldReset = false
        }

        if(expression.isNotEmpty() && (expression.last().isDigit() || expression.last() == ')')){
            expression += "*$function("
        }else{
            expression += function + "("
        }

        _displayState.value = expression
    }


    // when the user presses "="
    fun equalsPressed() {
        val result = engine.evaluate(expression)

        if (result == null) {
            _displayState.value = "Error"
            expression = ""
            shouldReset = true
            return
        }

        // si el resultado es válido
        val clean = if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }

        _displayState.value = clean
        expression = clean
        shouldReset = true
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

//
//    // ----------------------------------------------------------------------------
//    // PARENTHESIS
//    // ----------------------------------------------------------------------------
//    fun onParenthesisPressed(parenthesis: String) {
//        if (shouldReset) {
//            expression = ""
//            shouldReset = false
//        }
//
//        expression += parenthesis
//        _displayState.value = expression
//    }
//
//
//
//    // ----------------------------------------------------------------------------
//    // DECIMAL POINT
//    // ----------------------------------------------------------------------------
//    fun onDecimalPointPressed() {
//        if (shouldReset) {
//            expression = "0."
//            _displayState.value = expression
//            shouldReset = false
//            return
//        }
//
//        // extract last number to prevent multiple decimals
//        val lastNumber = expression.takeLastWhile { it.isDigit() || it == '.' }
//
//        if (lastNumber.contains(".")) return    // already has decimal
//
//        if (expression.isEmpty() || !expression.last().isDigit()) {
//            expression += "0."                  // start a new number with "0."
//        } else {
//            expression += "."                   // normal decimal
//        }
//
//        _displayState.value = expression
//    }
//
//
//
//    // ----------------------------------------------------------------------------
//    // DELETE (backspace)
//    // ----------------------------------------------------------------------------
//    fun onDeleteLast() {
//        if (shouldReset) {
//            expression = ""
//            _displayState.value = "0"
//            shouldReset = false
//            return
//        }
//
//        if (expression.isEmpty()) return
//
//        expression = expression.dropLast(1)
//
//        _displayState.value = if (expression.isEmpty()) "0" else expression
//    }
//
//
//
//    // ----------------------------------------------------------------------------
//    // CLEAR (AC)
//    // ----------------------------------------------------------------------------
//    fun onClearPressed() {
//        expression = ""
//        _displayState.value = "0"
//        shouldReset = false
//    }
//
//
//
//    // ----------------------------------------------------------------------------
//    // EQUALS (=)
//    // ----------------------------------------------------------------------------
//    fun equalsPressed() {
//        val result = engine.evaluate(expression)
//
//        if (result == null) {                    // error or invalid expression
//            _displayState.value = "Error"
//            expression = ""
//            shouldReset = true
//            return
//        }
//
//        // clean output: remove .0 if it's an integer
//        val clean = if (result % 1 == 0.0) {
//            result.toInt().toString()
//        } else {
//            result.toString()
//        }
//
//        _displayState.value = clean
//        expression = clean
//        shouldReset = true                       // next input starts fresh
//    }
//}
