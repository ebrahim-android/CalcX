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
    fun `digit should delate the last character`(){

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

}