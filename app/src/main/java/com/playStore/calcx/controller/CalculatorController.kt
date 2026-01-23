package com.playStore.calcx.controller

import android.util.Log
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

    var lastExpression by mutableStateOf("")


    // Controller internal status
    private var shouldReset = false
    private val engine = CalculatorEngine()

    private var memory: Double? = null // to save some kinda date

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
        val after = if (cursor < text.length) text[cursor] else null

        if (before == '(' && after == ')') {
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
        lastExpression = ""
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
//    private fun isOperator(c: Char): Boolean {
//        return c in "+-×÷*"
//    }

    private fun Char.isOperator(): Boolean {
        return this == '+' ||
                this == '-' ||
                this == '×' ||
                this == '÷' ||
//                this == '^' ||
                this == '.'
    }

    // handling the operator (+, -, *, /, ^), avoid ++, +×, etc.
    fun onOperatorPressed(op: String) {
        val mappedOp = mapOperator(op)
        val cursorPos = cursor()
        val text = expression.text

        if (text.isEmpty() && mappedOp != "-") return

        val before = charBeforeCursor()

        // cursor before an operator -> replace it
        if (before != null && before.isOperator()) {
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

    fun onDecimalPressed() {
        val before = charBeforeCursor()

        // case 1: cursor at the beginning or after an operator -> "0."
        if (before == null || before.isOperator() || before == '(') {
            insert("0.")
            return
        }

        val currentNumber = currentNumberBeforeCursor()

        // case 2: number already has a decimal -> return (do nothing)
        if (currentNumber.contains(".")) return

        // case 3: normal behavior
        insert(".")
    }

    private fun openParenthesisCount(): Int {
        //to count how many open parenthesis are in the expression
        return expression.text.count { it == '(' } -
                expression.text.count { it == ')' }

    }

    fun onParenthesisPressed() {
        val before = charBeforeCursor()
        val open = openParenthesisCount()

        //allow "(" after ^
        if (
            before == null ||
            before.isOperator() ||
            before == '(' ||
            before == '^'
        ) {
            insert("(")
            return
        }

        if (open > 0 && (before?.isDigit() == true || before == ')')) {
            insert(")")
            return
        }
    }

    fun onEqualsPressed() {
        val expr = expression.text
        if (expr.isBlank()) return //if the expression is empty, do nothing

        //if the last character is an operator, do nothing
        val before = charBeforeCursor()
        if (before != null && before.isOperator()) return
        if (openParenthesisCount() != 0) return //if there are open parenthesis, do nothing

        val resultValue = engine.evaluate(expr) ?: run {
            expression = TextFieldValue("Error", TextRange(5))
            shouldReset = true
            return
        }

//        val evaluated  = engine.evaluate(expression.text)
//        result = resultValue.toString()


        val clean = if (resultValue % 1 == 0.0) { // clean the result: remove .0 if it's an integer
            resultValue.toInt().toString()
        } else {
            resultValue.toString()
        }

        //update the display with the result
        lastExpression = expr
        expression = TextFieldValue(clean, TextRange(clean.length))
        result = clean
        shouldReset = true

    }

    fun onFactorialPressed() { // to handle the factorial button
        val text = currentText()

        if (text.isEmpty()) return
        if (text.last() == '!') return

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


    fun onPowerPressed() { // to handle the power button
        val text = currentText()

        if (text.isEmpty()) return
        if (text.last() == '^') return

        insert("^")
    }

    fun onTenPowerPressed() { // to handle the 10^x button
        val text = currentText()

        if (text.isNotEmpty()) {
            val last = text.last()
            if (last.isDigit() || last == '!' || last == ')') return
            if (last == '^') return
        }

        insert("10^")
    }

    fun endsWithSameFunction(
        text: String,
        function: String
    ): Boolean { // helper function to avoid adding the same function twice
        val pattern = "$function("
        return text.endsWith(pattern)
    }

    fun onFunctionPressed(function: String) { //function: cos, sin, tan, pi etc..
        val text = expression.text

        if (endsWithSameFunction(text, function)) {
            return //to avoid adding the same function twice
        }

        val newText = when {
            text.isEmpty() -> {
                "$function("
            }

            text.last().isOperator() || text.last() == '(' -> {
                text + "$function("
            }

            else -> {
                "$function($text)"
            }
        }

        expression = TextFieldValue(
            newText, TextRange(newText.length)
        )
    }

    private fun currentSegment(): String {
        val text = expression.text
        val pos = cursor()

        var i = pos - 1
        while (i >= 0 && !"+-×÷".contains(text[i]) && text[i] != '(') {
            i--
        }
        return text.substring(i + 1, pos)
    }

    private fun hasOpenEuler(segment: String): Boolean {
        return segment.endsWith("e^")
    }

    fun onEulerPressed() {
        val text = expression.text
        val before = charBeforeCursor()

        // case 1: empty expression -> "e^"
        if (text.isEmpty()) {
            insert("e^")
            return
        }

        // it just allow e^ after an operator or '('
        if (before != null && !before.isOperator() && before != '(') return

        val segment = currentSegment()

        // block e^e^e^
        if (hasOpenEuler(segment)) return

        insert("e^")
    }

    fun onSquarePress() { // to handle the square button
        val text = expression.text

        if (
            text.isEmpty() ||
            text.last().isOperator() ||
            text.last() == '(' ||
            text.last() == '²'
        ) return

        insert("x²")
    }

    fun onNegativePressed() {
        val text = expression.text
        val before = charBeforeCursor()

        // Block obvious invalid cases
        if (before == '-' || before == '−') return

        // Allow negative at start
        if (text.isEmpty()) {
            insert("(-")
            return
        }

        // Allow after operators or '('
        if (before != null && (before.isOperator() || before == '(')) {
            insert("(-")
            return
        }

        // Allow after numbers or ')'
        if (before != null && (before.isDigit() || before == ')')) {
            insert("(-")
        }
    }
    //-------bottoms MS, MC, MR, M+ and M--------

    // MS: save the last result
    fun onMS(){
        val value = result.toDoubleOrNull() ?: return
        memory = value
    }

    // MR: read the last saved result to print it
    fun onMR(){
        val value = memory?: return // if memory is null, return
        insert(value.toInt().toString())
    }

    // MC: Clear the memory
    fun onMC(){
        memory = null
    }


    // M+: sum the last result to the memory with the current result
    fun onMPlus() {
        val value = result.toDoubleOrNull() ?: return
        memory = (memory ?: 0.0) + value
    }

    // M-: subtract the last result to the memory with the current result
    fun onMMinus() {
        val value = result.toDoubleOrNull() ?: return
        memory = (memory ?: 0.0) - value
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