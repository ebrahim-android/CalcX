package com.playStore.calcx.controller

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CalculatorControllerTest {
    
    private lateinit var controller: CalculatorController

    @Before
    fun setUp() {
        controller = CalculatorController()
    }

    @Test
    fun `digit should be appended to the expression`(){ // to test if the digit is appended to the expression

        // Act
        controller.insert("5")

        // Assert
        assertEquals("5", controller.expression.text)
    }

    @Test
    fun `digit should be appended consecutively`(){ // to test if the digit is appended consecutively

        controller.insert("5")
        controller.insert("2")

        assertEquals("52", controller.expression.text)
    }

    @Test
    fun `digit should delete the last character`(){

        controller.insert("5")
        controller.insert("2")

        controller.delete()

        assertEquals("5", controller.expression.text)

    }

    @Test
    fun `decimal should appended when there is a digit`() {

        controller.insert("5")
        controller.insert(".")

        assertEquals("5.", controller.expression.text)

    }

    @Test
    fun `double decimal should not be appended`() { // we got a problem, I gotta fix it

        controller.insert("5")
        controller.insert(".")
        controller.insert(".")

        assertEquals("5.", controller.expression.text)

    }

    @Test
    fun `operator should be appended to the expression`(){

        controller.insert("5")

        controller.onOperatorPressed("+")

        assertEquals("5+", controller.expression.text)

    }

    @Test
    fun `parenthesis should not be appended to the expression`(){

        controller.insert("5")

        controller.onParenthesisPressed()

        assertEquals("5", controller.expression.text)

    }

    @Test
    fun `clear should clear the expression`(){

        controller.insert("5")

        controller.clear()

        assertEquals("", controller.expression.text)

    }

    @Test
    fun `equals should evaluate the expression`(){

        controller.insert("2")
        controller.onOperatorPressed("+")
        controller.insert("2")

        controller.onEqualsPressed()

        assertEquals("4", controller.expression.text)

    }

    @Test
    fun `factorial should return correct expression`(){

        controller.insert("5")
        controller.onFactorialPressed()

        assertEquals("5!", controller.expression.text)

    }

    @Test
    fun `euler should return correct expression`() {
        controller.onEulerPressed()
        controller.onEulerPressed()

        assertEquals("e^", controller.expression.text)
    }

    @Test
    fun `square root should return correct expression`() {
        controller.onSquareRootPressed()
        controller.insert("5")

        assertEquals("√5", controller.expression.text)
    }

    @Test
    fun `function should return correct expression`() {
        controller.insert("5")
        controller.onFunctionPressed("log")

        assertEquals("log(5)", controller.expression.text)

    }

    @Test
    fun `power should return correct expression`() {
        controller.insert("5")
        controller.onPowerPressed()
        controller.insert("2")

        assertEquals("5^2", controller.expression.text)
    }

    @Test
    fun `square should return correct expression`() {
        controller.insert("5")
        controller.onSquarePress()

        assertEquals("5x²", controller.expression.text)
    }

    @Test
    fun `negative should return correct expression`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.onNegativePressed()
        controller.insert("8")

        assertEquals("5+(-8", controller.expression.text)
    }

    @Test
    fun `empty display should handling the correct behavior`() {
        controller.onOperatorPressed("+")
        controller.onOperatorPressed("-")

        assertEquals("-", controller.expression.text)
    }

    @Test
    fun `MS should save the last result`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("5")
        controller.onEqualsPressed() // the result is 10

        controller.onMS() // save this result

        assertEquals("10", controller.expression.text)

    }

    @Test
    fun `MR should read the last saved result`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed() // the result is 9

        controller.onMS() // save this result

        controller.clear()

        controller.onMR() // read this result

        assertEquals("9", controller.expression.text)

    }

    @Test
    fun `MC should clear the memory`(){
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed() // the result is 9

        controller.onMS() // save this result

        controller.clear()

        controller.onMC() // clear the memory

        controller.onMR() // read this result

        assertEquals("", controller.expression.text)

    }

    @Test
    fun `M- should subtract the last result to the memory`() {
        controller.insert("5")
        controller.onOperatorPressed("+")
        controller.insert("4")
        controller.onEqualsPressed() // the result is 9

        controller.onMS() // save this result

        controller.clear()

        controller.insert("6")
        controller.onOperatorPressed("×")
        controller.insert("2")
        controller.onEqualsPressed() // the result is 12

        controller.onMMinus() // subtract this result from the memory

        controller.clear()

        controller.onMR() // read this result

        assertEquals("-3", controller.expression.text)

    }

}