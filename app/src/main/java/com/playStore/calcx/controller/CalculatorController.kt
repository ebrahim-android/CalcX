package com.playStore.calcx.controller

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.playStore.calcx.model.CalculatorEngine

class CalculatorController {
    //To show the principal display (0, 123, sin(1) so on...)
    private val _displayState = mutableStateOf("0")
    val displayState get() = _displayState

    var expression by mutableStateOf("")
    var result by mutableStateOf("")

    // Controller internal status
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
            //if last result exists, apply function to result
            if(expression.isNotEmpty()){
                expression = "$function(${expression})"
                _displayState.value = expression
                shouldReset = false
                return
            }
        }

        //normal behavior
        expression = ""
        _displayState.value = "0"
        shouldReset = false

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
            _displayState.value = "Error" //I modified it
            expression = ""
            shouldReset = true
            return
        }

        // clean output: remove .0 if it's an integer
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

        if (expression.isEmpty()) return

        expression = expression.dropLast(1) // to eliminate the last character

        _displayState.value = if (expression.isEmpty()) "0" else expression
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

        // extract last number to prevent multiple decimals
        val lastNumber = expression.takeLastWhile { it.isDigit() || it == '.' }

        if(lastNumber.contains("."))return    // already has decimal

        if(expression.isEmpty() || !expression.last().isDigit()){
            expression += "0."
        }else{
            expression += "."                   // normal decimal
        }

        _displayState.value = expression
    }

    fun onPercentPressed() {
        if (expression.isEmpty()) return

        if (shouldReset) {
            // After "=" → percentage should apply to the result directly
            val num = expression.toDoubleOrNull() ?: return
            val result = num / 100
            expression = result.toString()
            _displayState.value = expression
            shouldReset = false
            return
        }

        // Extract last number (e.g. from "200+10" get "10")
        val lastNumber = expression.takeLastWhile { it.isDigit() || it == '.' }

        if (lastNumber.isEmpty()) return

        // Convert last number to double
        val value = lastNumber.toDoubleOrNull() ?: return

        // Remove the last number from the expression
        val baseExpression = expression.dropLast(lastNumber.length)

        // Case A: IF there is an operator before lastNumber → contextual percent
        // Example: "200 + 10%"
        val operatorIndex = baseExpression.indexOfLast { "+-*/^".contains(it) }

        val newPercentValue = if (operatorIndex != -1) {
            // We found an operator → contextual percentage
            // Example: in "200+10", numberBefore = 200, lastNumber = 10 → 10% of 200 = 20
            val numberBefore = baseExpression.takeLastWhile { it.isDigit() || it == '.' }
            val base = numberBefore.toDoubleOrNull() ?: 0.0
            (base * value) / 100.0
        } else {
            // Case B: direct percentage
            // Example: "10%" → 0.1
            value / 100.0
        }

        // Build final expression
        val finalExpression = baseExpression + newPercentValue.toString()

        expression = finalExpression
        _displayState.value = expression
    }

    // --------- NEW FUNCTION ------------

    //handling the square button
    fun onSquarePress(){
        if(expression.isEmpty()) return

        if(shouldReset) shouldReset = false

        // Wrap last number or complete expression in parentheses
        expression = "($expression)^2"
        _displayState.value = expression
    }

    //handling the square root button
    fun onSquareRootPressed(){
        if(expression.isEmpty()){
            expression = "sqrt("
            _displayState.value = expression
            return
        }

        if(shouldReset) shouldReset = false

        expression = "sqrt($expression)"
        _displayState.value = expression
    }

    fun onEulerPressed() {
        if (expression.isEmpty()) {
            expression = "e^("
            _displayState.value = expression
            return
        }

        if (shouldReset) shouldReset = false

        expression = "e^($expression)"
        _displayState.value = expression
    }

    fun onPowerPressed(){ //handling the power button (so far)
        if(expression.isEmpty()) return

        if(shouldReset) shouldReset = false

        // Wrap full expression: pow(expression,
        expression = "pow($expression, "
        _displayState.value = expression
    }

    fun onGeneralRootPressed(){
        if(expression.isEmpty()) return

        if(shouldReset) shouldReset = false

        // Convert root to pow(expression, 1/
        expression = "pow($expression, 1/"
        _displayState.value = expression
    }

    fun onFactorialPressed() { // factorial
        if (expression.isEmpty()) return

        if (shouldReset) shouldReset = false

        // just allow factorial if the last character is a digit or ')'
        var last = expression.last()
        if (last.isDigit() || last == ')') {
            expression += "!"
        } else {
            _displayState.value = expression
        }
    }

    fun onExpPressed(){
        if(shouldReset) shouldReset = false

        if(expression.isEmpty()){ // wrap empty expression: exp( (to friendly design)
            expression = "exp("
            _displayState.value = expression
            return
        }

        // Wrap full expression: exp(expression,
        expression = "exp($expression)"
        _displayState.value = expression
    }

    fun onTenPowerPressed(){ //handling the 10^x button
        if(expression.isEmpty()) return

        if(shouldReset) shouldReset = false

        if(expression.isEmpty()){
            expression = "10^("
            _displayState.value = expression
            return
        }

        expression = "10^($expression)"
        _displayState.value = expression
    }
}