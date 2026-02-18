package com.playStore.calcx.controller

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized



@RunWith(Parameterized::class)
class CalculatorControllerNegativeFunctionArgumentsParameterizedTest(
    val expression: String,
    val expectedResult: String
) {

    private lateinit var controller: CalculatorController

    @Before
    fun setUp() {
        controller = CalculatorController()
    }

    @Test
    fun `should evaluate negative function expressions correctly`() {
        controller.insert(expression)
        controller.onEqualsPressed()

        assertEquals(expectedResult, controller.result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} = {1}")
        fun data() = listOf(
            arrayOf("-sin(0)", "0"),
            arrayOf("-cos(0)", "-1"),
            arrayOf("-tan(0)", "0"),
//            arrayOf("-cos(pi)", "1"),
            arrayOf("-sin(pi/2)", "-1"),
//            arrayOf("-tan(pi/4)", "-1"),
            arrayOf("-log(100)", "-2"),
            arrayOf("-ln(10)", "-2.302585092994046"),
            arrayOf("-sqrt(16)", "-4"),
            arrayOf("-pi(1)", "-3.141592653589793"),
            arrayOf("-e(1)", "-2.718281828")
        )
    }
}