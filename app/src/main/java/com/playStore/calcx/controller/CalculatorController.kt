package com.playStore.calcx.controller

import androidx.collection.intSetOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.playStore.calcx.model.CalculatorEngine

class CalculatorController {
    //To show the principal display (0, 123, sin(1) so on...)
    private val _displayState = mutableStateOf("0")
    val displayState get() = _displayState

    var expression by mutableStateOf(
        TextFieldValue("", TextRange(0))
    )//added
//    var cursorPosition by mutableStateOf(0) // SO FAR

    var result by mutableStateOf("")

    // Controller internal status
    private var shouldReset = false
    private val engine = CalculatorEngine()

    //it's gonna be temporal, help us to migrate without
    // break the whole controller
    private fun updateExpression(
        newText: String,
        newCursor: Int = newText.length
    ) {
        expression = TextFieldValue(
            text = newText,
            selection = TextRange(newCursor)
        )
    }

    private fun currentText(): String {
        return expression.text
    }

    private fun cursor(): Int { //read the cursor position
        return expression.selection.end
    }

//     when the user presses a digit (1, 5 or 8)
//    fun onDigitPressed(digit: String) {
//        if (shouldReset) {//to reset the display is the user pressed "="
//            _displayState.value = digit
//            expression = digit
//            shouldReset = false
//            return
//        }
//
//        if (_displayState.value == "0") { //to replaced the "0" with the first digit pressed
//            _displayState.value = digit
//            expression = digit
//            return
//        }
//
//        //to add the digit to the display next to the previous digit (normal)
//        _displayState.value += digit
//        expression += digit
//    }


    // -----TESTE NEW FUNCTION------
    fun insert(text: String) {
        val oldText = expression.text
        val cursor = expression.selection.end

        val newText =
            oldText.substring(0, cursor) +
                    text +
                    oldText.substring(cursor)

        val newCursor = cursor + text.length

        expression = TextFieldValue(
            text = newText,
            selection = TextRange(newCursor)
        )
    }


    fun delete() { //to delete the last character
        val cursor = expression.selection.end
        val text = expression.text

        if (cursor == 0) return

        val before = charBeforeCursor()
        //case 1: eraser "()"
        val after = if(cursor < text.length) text[cursor] else null

        if(before == '(' && after == ')'){
            val newText =
                text.substring(0, cursor - 1) +
                        text.substring(cursor + 1)

            updateExpression(newText, cursor - 1)
            return
        }
        //case 2: eraser one carater -> normal behavior
        val newText =
            text.substring(0, cursor - 1) +
                    text.substring(cursor)

        updateExpression(newText, cursor - 1)
    }

    fun clear() { //to clear the display
        expression = TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
        result = ""
        shouldReset = false
    }


    fun moveCursorLeft() {
        val cursor = expression.selection.end
        if (cursor > 0) {
            expression = expression.copy(
                selection = TextRange(cursor - 1)
            )
        }
    }

    fun moveCursorRight() {
        val cursor = expression.selection.end
        if (cursor < expression.text.length) {
            expression = expression.copy(
                selection = TextRange(cursor + 1)
            )
        }
    }

    // this function just return the character before the cursor
    private fun charBeforeCursor(): Char? {
        val pos = cursor()
        return if (pos > 0) expression.text[pos - 1] else null
    }

    // it decide what meant the operator or character
    private fun isOperator(c: Char): Boolean {
        return c in "+-×÷*"
    }

    // handling the operator (+, -, *, /, ^), avoid ++, +×, etc.
    fun onOperatorPressed(op: String) {
        val mappedOp = mapOperator(op)
        val cursorPos = cursor()
        val text = expression.text

        if (text.isEmpty() && mappedOp != "-") return

        val before = charBeforeCursor()

        // cursor before an operator -> replace it
        if (before != null && isOperator(before)) {
            val newText =
                text.substring(0, cursorPos - 1) +
                        mappedOp +
                        text.substring(cursorPos)

            updateExpression(newText, cursorPos)
            return
        }
        // normal behavior
        insert(mappedOp)
    }

    // return the number before the cursor,
    // it's like see the whole expression
    fun currentNumberBeforeCursor(): String {
        val pos = cursor()
        val text = expression.text

        var i = pos - 1
        while (i >= 0 && (text[i].isDigit() || text[i] == '.')) {
            i--
        }
        return text.substring(i + 1, pos)
    }

    fun onDecimalPressed(){
        val before = charBeforeCursor()

        // case 1: cursor at the beginning or after an operator -> "0."
        if (before == null || isOperator(before) || before == '(') {
            insert("0.")
            return
        }

        val currentNumber = currentNumberBeforeCursor()

        // case 2: number already has a decimal -> return (do nothing)
        if (currentNumber.contains(".")) return

        // case 3: normal behavior
        insert(".")
    }

    private fun openParenthesisCount():Int{
        //to count how many open parenthesis are in the expression
        return expression.text.count { it == '(' } -
                expression.text.count { it == ')' }

    }

    fun onParenthesisPressed() {
        val before = charBeforeCursor()
        val open = openParenthesisCount()

        //case 1: cursor at the beginning or after an operator -> "("
        if(before == null || isOperator(before) || before == '('){
            insert("(")
            return
        }
        //case 2: cursor at the end of an expression -> ")"
        if(open > 0 && (before?.isDigit() == true || before == ')')){
            insert(")")
            return
        }

    }

    fun onEqualsPressed() {
        val expr = expression.text
        if(expr.isBlank()) return //if the expression is empty, do nothing

        //if the last character is an operator, do nothing
        val before = charBeforeCursor()
        if(before != null && isOperator(before)) return
        if(openParenthesisCount() != 0) return //if there are open parenthesis, do nothing

        val resultValue = engine.evaluate(expr) ?: run {
            expression = TextFieldValue("Error", TextRange(5))
            shouldReset = true
            return
        }

        val clean = if (resultValue % 1 == 0.0){ // clean the result: remove .0 if it's an integer
            resultValue.toInt().toString()
        } else {
            resultValue.toString()
        }

        //update the display with the result
        expression = TextFieldValue(clean, TextRange(clean.length))
        result = expr
        shouldReset = true

    }

    fun onFactorialPressed() { // to handle the factorial button
        val text = currentText()

        if(text.isEmpty()) return
        if(text.last() == '!') return

        insert("!")

    }

    fun onSquareRootPressed() { // to handle the square root button
        val text = currentText()

        if (text.isNotEmpty()) {
            val last = text.last()
            if (last.isDigit() || last == '!' || last == ')') return
            if (last == '√') return
        }

        insert("√")
    }


    fun onPowerPressed(){ // to handle the power button
        val text = currentText()

        if(text.isEmpty()) return
        if(text.last() == '^') return

        insert("^")
    }

    // -----NORMAL FUNCTION-----
    fun mapOperator(op: String): String { // convert UI symbols into real math operators for the engine
        return when (op) {
            "×" -> "×"
            "÷" -> "÷"
            "−" -> "-"
            else -> op
        }
    }
}

// when the user presses an operator (+, -, *, /, ^)
//    fun onOperatorPressed(operator: String) {
//        val op = mapOperator(operator)
//
//        if (shouldReset) shouldReset = false
//
//        if (expression.isEmpty()) return
//
//        val last = expression.last()
//
//        // If last char is operator, replace it (avoid ++, +×, etc.)
//        if ("+-*/^".contains(last)) {
//            expression = expression.dropLast(1) + op
//        } else {
//            expression += op
//        }
//
//        _displayState.value = expression
//    }
//
//    // when the user presses a function button (sin, cos, ln, etc.)
//    fun onFunctionPressed(function: String) {
//        if (shouldReset) {
//            //if last result exists, apply function to result
//            if (expression.isNotEmpty()) {
//                expression = "$function(${expression})"
//                _displayState.value = expression
//                shouldReset = false
//                return
//            }
//        }
//
//        //normal behavior
//        expression = ""
//        _displayState.value = "0"
//        shouldReset = false
//
//        if (expression.isNotEmpty() && (expression.last().isDigit() || expression.last() == ')')) {
//            expression += "*$function("
//        } else {
//            expression += function + "("
//        }
//
//        _displayState.value = expression
//    }
//
//    // when the user presses "="
//    fun equalsPressed() {
//        if (expression.isBlank()) return
//
//        val eval = engine.evaluate(expression)
//
//        //Error case
//        if (eval == null) {
//            result = expression //small text above
//            expression = "Error" // big text below
//            _displayState.value = "Error"
//            shouldReset = true
//            return
//        }
//
//        // clean result: remove .0 if it's an integer
//        val clean = if (eval % 1 == 0.0) {
//            eval.toInt().toString()
//        } else {
//            eval.toString()
//        }
//
//        result = expression // keep formula in "result" (this goes small, above)
//        expression = clean // Replace expression with evaluated result
//        _displayState.value = clean // Update display
//        shouldReset = true // Prepare next input to reset
//    }
//
//    // when the user presses "C" (Clear)
//    fun onClearPressed() {
//        expression = ""
//        result = "" //to clean the result
//        _displayState.value = "0"
//        shouldReset = false
//    }

// when the user presses "eliminate symbol" (backspace)
//    fun onDeleteLast() {
//        if (shouldReset) {
//            expression = ""
//            result = "" //to clean the result
//            _displayState.value = "0"
//            shouldReset = false
//            return
//        }
//
//        if (expression.isEmpty()) return
//        expression = expression.dropLast(1) // to eliminate the last character
//        _displayState.value = if (expression.isEmpty()) "0" else expression
//    }

// when the user presses "(" o ")"
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
//            expression += "0."
//        } else {
//            expression += "."                   // normal decimal
//        }
//
//        _displayState.value = expression
//    }
//
//    fun onPercentPressed() {
//        if (expression.isEmpty()) return
//
//        if (shouldReset) {
//            // After "=" → percentage should apply to the result directly
//            val num = expression.toDoubleOrNull() ?: return
//            val result = num / 100
//            expression = result.toString()
//            _displayState.value = expression
//            shouldReset = false
//            return
//        }
//
//        // Extract last number (e.g. from "200+10" get "10")
//        val lastNumber = expression.takeLastWhile { it.isDigit() || it == '.' }
//
//        if (lastNumber.isEmpty()) return
//
//        // Convert last number to double
//        val value = lastNumber.toDoubleOrNull() ?: return
//
//        // Remove the last number from the expression
//        val baseExpression = expression.dropLast(lastNumber.length)
//
//        // Case A: IF there is an operator before lastNumber → contextual percent
//        // Example: "200 + 10%"
//        val operatorIndex = baseExpression.indexOfLast { "+-*/^".contains(it) }
//
//        val newPercentValue = if (operatorIndex != -1) {
//            // We found an operator → contextual percentage
//            // Example: in "200+10", numberBefore = 200, lastNumber = 10 → 10% of 200 = 20
//            val numberBefore = baseExpression.takeLastWhile { it.isDigit() || it == '.' }
//            val base = numberBefore.toDoubleOrNull() ?: 0.0
//            (base * value) / 100.0
//        } else {
//            // Case B: direct percentage
//            // Example: "10%" → 0.1
//            value / 100.0
//        }
//
//        // Build final expression
//        val finalExpression = baseExpression + newPercentValue.toString()
//
//        expression = finalExpression
//        _displayState.value = expression
//    }
//
//    // --------- NEW FUNCTION ------------
//
//    //handling the square button
//    fun onSquarePress() {
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        // Wrap last number or complete expression in parentheses
//        expression = "($expression)^2"
//        _displayState.value = expression
//    }
//
//    fun onGeneralPowerPress() { //handling the general power button
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        expression = "($expression)^("
//        _displayState.value = expression
//    }
//
//    //handling the square root button
//    fun onSquareRootPressed() {
//        if (expression.isEmpty()) {
//            expression = "sqrt("
//            _displayState.value = expression
//            return
//        }
//
//        if (shouldReset) shouldReset = false
//
//        expression = "sqrt($expression)"
//        _displayState.value = expression
//    }
//
//    fun onEulerPressed() {
//        if (expression.isEmpty()) {
//            expression = "e^("
//            _displayState.value = expression
//            return
//        }
//
//        if (shouldReset) shouldReset = false
//
//        expression = "e^($expression)"
//        _displayState.value = expression
//    }
//
//    fun onPowerPressed() { //handling the power button (so far)
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        // Wrap full expression: pow(expression,
//        expression = "pow($expression, "
//        _displayState.value = expression
//    }
//
//    fun onGeneralRootPressed() {
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        // Convert root to pow(expression, 1/
//        expression = "pow($expression, 1/"
//        _displayState.value = expression
//    }
//
//    fun onFactorialPressed() { // factorial
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        // just allow factorial if the last character is a digit or ')'
//        var last = expression.last()
//        if (last.isDigit() || last == ')') {
//            expression += "!"
//        } else {
//            _displayState.value = expression
//        }
//    }
//
//    fun onExpPressed() {
//        if (shouldReset) shouldReset = false
//
//        if (expression.isEmpty()) { // wrap empty expression: exp( (to friendly design)
//            expression = "exp("
//            _displayState.value = expression
//            return
//        }
//
//        // Wrap full expression: exp(expression,
//        expression = "exp($expression)"
//        _displayState.value = expression
//    }
//
//    fun onTenPowerPressed() { //handling the 10^x button
//        if (expression.isEmpty()) return
//
//        if (shouldReset) shouldReset = false
//
//        if (expression.isEmpty()) {
//            expression = "10^("
//            _displayState.value = expression
//            return
//        }
//
//        expression = "10^($expression)"
//        _displayState.value = expression
//    }
//}