package com.playStore.calcx.controller

import com.playStore.calcx.model.CalculatorEngine
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test

@RunWith(Parameterized::class)
class CalculatorEngineScientificParameterizedTest(
    private val expression: String,
    private val expectedResult: Double
) {

    private val engine = CalculatorEngine()

    @Test
    fun `scientific expression should return expected result`() {
        val result = engine.evaluate(expression)
        Assert.assertEquals(expectedResult, result!!, 0.0001)
    }

    companion object{
        @JvmStatic
        @Parameterized.Parameters(name = "{0} = {1}")
        fun data() = listOf(
            arrayOf("cos(0)", 1.0),
            arrayOf("sin(0)", 0.0),
            arrayOf("tan(0)", 0.0),
            arrayOf("cos(pi)", -1.0),
            arrayOf("sin(pi/2)", 1.0),
            arrayOf("tan(pi/4)", 1.0),
            arrayOf("pi(1)", 3.141592653589793),
            arrayOf("sin(cos(8))", -0.14498719803267052),
            arrayOf("tan(sin(cos(1)))", 0.565114343133041),
            arrayOf("log(100)", 2.0),
            arrayOf("e^2", 7.38905609893065)
        )
    }
}