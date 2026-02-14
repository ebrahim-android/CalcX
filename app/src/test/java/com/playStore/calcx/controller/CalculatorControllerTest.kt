package com.playStore.calcx.controller

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.playStore.calcx.domain.enums.CalculatorMode
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatorControllerTest {

    private lateinit var controller: CalculatorController

    @Before
    fun setUp() {
        controller = CalculatorController()
    }

    // --------------------
    // INSERT / DELETE
    // --------------------

    @Test
    fun `insert should append text at cursor`() {
        controller.insert("5")
        controller.insert("2")

        assertEquals("52", controller.expression.text)
    }

    @Test
    fun `delete should remove last character`() {
        controller.insert("5")
        controller.insert("2")
        controller.delete()

        assertEquals("5", controller.expression.text)
    }

    // --------------------
    // OPERATORS
    // --------------------

    @Test
    fun `operator should be appended after digit`() {
        controller.insert("5")
        controller.onOperatorPressed("+")

        assertEquals("5+", controller.expression.text)
    }

    @Test
    fun `operator should replace previous operator`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.onOperatorPressed("-")

        assertEquals("5-", controller.expression.text)
    }

    @Test
    fun `operator should allow minus as first character`() {
        controller.onOperatorPressed("-")

        assertEquals("-", controller.expression.text)
    }

    // --------------------
    // DECIMAL
    // --------------------

    @Test
    fun `decimal should append to number`() {
        controller.insert("5")
        controller.onDecimalPressed()

        assertEquals("5.", controller.expression.text)
    }

    @Test
    fun `decimal should not be duplicated`() { //problem here
        controller.insert("5")
        controller.onDecimalPressed()
        controller.onDecimalPressed()

        assertEquals("5.", controller.expression.text)
    }

    // --------------------
    // PARENTHESIS
    // --------------------

    @Test
    fun `parenthesis should open when expression is empty`() {
        controller.onParenthesisPressed(")")

        assertEquals("(", controller.expression.text)
    }

    @Test
    fun `parenthesis should close when there is an open one`() {
        controller.onParenthesisPressed(")")
        controller.insert("5")
        controller.onParenthesisPressed(")")

        assertEquals("(5)", controller.expression.text)
    }

    @Test
    fun `parenthesis should not close when there is no open one`() {
        controller.onParenthesisPressed(")")
        controller.onParenthesisPressed(")")
        controller.onParenthesisPressed(")")

        assertEquals("(((", controller.expression.text)
    }

    // --------------------
    // EQUALS
    // --------------------

    @Test
    fun `equals should evaluate expression and store lastExpression`() {
        controller.insert("2")
        controller.onOperatorPressed("+")
        controller.insert("2")

        controller.onEqualsPressed()

        assertEquals("4", controller.expression.text)
        assertEquals("2+2", controller.lastExpression)
        assertEquals("4", controller.result)
    }

    @Test
    fun `equals should do nothing if expression is empty`() {
        controller.onEqualsPressed()

        assertEquals("", controller.expression.text)
    }

    @Test
    fun `equals should not evaluate if expression ends with operator`() {
        controller.insert("5")
        controller.onOperatorPressed("+")

        controller.onEqualsPressed()

        assertEquals("5+", controller.expression.text)
    }

    // --------------------
    // RESET BEHAVIOR
    // --------------------

    @Test
    fun `insert after equals should start new expression`() { //problem here
        controller.insert("2")
        controller.onOperatorPressed("+")
        controller.insert("2")
        controller.onEqualsPressed()

        controller.insert("5")

        assertEquals("5", controller.expression.text)
    }

    // --------------------
    // FUNCTIONS
    // --------------------

    @Test
    fun `factorial should append exclamation`() {
        controller.insert("5")
        controller.onFactorialPressed()

        assertEquals("5!", controller.expression.text)
    }

    @Test
    fun `factorial should return error if too big`() {
        controller.insert("171")
        controller.onFactorialPressed()

        controller.onEqualsPressed()

        assertEquals("Factorial too big", controller.expression.text)

    }


    @Test
    fun `square root should prepend symbol`() {
        controller.onSquareRootPressed()
        controller.insert("9")

        assertEquals("√9", controller.expression.text)
    }

    @Test
    fun `power should append caret`() {
        controller.insert("5")
        controller.onPowerPressed()
        controller.insert("2")

        assertEquals("5^2", controller.expression.text)
    }

    @Test
    fun `square should append x²`() {
        controller.insert("5")
        controller.onSquarePress()

        assertEquals("5x²", controller.expression.text)
    }

    @Test
    fun `function should wrap expression`() {
        controller.insert("5")
        controller.onFunctionPressed("log")

        assertEquals("log(5)", controller.expression.text)
    }

    @Test
    fun `euler should not duplicate`() {
        controller.onEulerPressed()
        controller.onEulerPressed()

        assertEquals("e^", controller.expression.text)
    }

    // --------------------
    // NEGATIVE
    // --------------------

    @Test
    fun `negative should be inserted after operator`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.onNegativePressed()
        controller.insert("8")

        assertEquals("5+(-8", controller.expression.text)
    }

    // --------------------
    // MEMORY
    // --------------------

    @Test
    fun `MS should save result`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("5")
        controller.onEqualsPressed()

        controller.onMS()

        assertEquals("10", controller.result)
    }

    @Test
    fun `MR should insert memory value`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed()

        controller.onMS()
        controller.clear()
        controller.onMR()

        assertEquals("9", controller.expression.text)
    }

    @Test
    fun `MC should clear memory`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed()

        controller.onMS()
        controller.onMC()
        controller.clear()
        controller.onMR()

        assertEquals("", controller.expression.text)
    }

    @Test
    fun `MMinus should subtract from memory`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed() // 9

        controller.onMS()

        controller.clear()
        controller.insert("6")
        controller.onOperatorPressed("×")
        controller.insert("2")
        controller.onEqualsPressed() // 12

        controller.onMMinus()
        controller.clear()
        controller.onMR()

        assertEquals("-3", controller.expression.text)
    }

    // --------------------
    // ENG notation
    // --------------------

    @Test
    fun `eng should convert to scientific notation`() {
        controller.insert("111")
        controller.onFactorialPressed()
        controller.onEqualsPressed()

        controller.onEng()

        assertEquals("2.147483647E9", controller.result)

    }

    @Test
    fun `moveCursorLeft moves cursor one position to the left`() {
        controller.expression = TextFieldValue(
            "12345",
            selection = TextRange(3)
        )

        controller.moveCursorLeft()

        assertEquals(2, controller.expression.selection.end)
    }

    @Test
    fun `moveCursorRight moves cursor one position to the right`() {
        controller.expression = TextFieldValue(
            text = "12345",
            selection = TextRange(2)
        )

        controller.moveCursorRight()

        assertEquals(3, controller.expression.selection.end)
    }

     // --------------------
    // MODE
    // --------------------

    @Test
    fun `mode button should cycle through modes`() {
        assertEquals(CalculatorMode.STANDARD, controller.mode)

        controller.onModePressed()
        assertEquals(CalculatorMode.SCIENTIFIC, controller.mode)

        controller.onModePressed()
        assertEquals(CalculatorMode.PROGRAMMER, controller.mode)

        controller.onModePressed()
        assertEquals(CalculatorMode.STANDARD, controller.mode)
    }

    @Test
    fun `changing mode should not clear expression`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("3")

        controller.onModePressed() // STANDARD → SCIENTIFIC

        assertEquals("5+3", controller.expression.text)
    }

    @Test
    fun `changing mode should not clear result`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("3")

        controller.onEqualsPressed()

        controller.onModePressed() // STANDARD → SCIENTIFIC

        assertEquals("8", controller.result)
    }

    @Test
    fun `changing mode should not clear lastExpression`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("3")

        controller.onEqualsPressed()

        controller.onModePressed() // STANDARD → SCIENTIFIC

        assertEquals("5+3", controller.lastExpression)
    }
}
