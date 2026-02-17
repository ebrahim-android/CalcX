package com.playStore.calcx.controller

import com.playStore.calcx.model.CalculatorEngine
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CalculatorEngineParameterizedTest(
    private val expression: String,
    private val expectedResult: Double
) {

    private val engine = CalculatorEngine()

    @Test
    fun `expression should return expected result`() {
        val result = engine.evaluate(expression)
        Assert.assertEquals(expectedResult, result!!, 0.0001)
    }

    companion object{
        @JvmStatic
        @Parameterized.Parameters(name = "{0} = {1}")
        fun data() = listOf(
            arrayOf("2+2", 4.0),
            arrayOf("5-2", 3.0),
            arrayOf("2*3", 6.0),
            arrayOf("10/2", 5.0),
            arrayOf("2^3", 8.0),
            arrayOf("√9", 3.0),
            arrayOf("5!", 120.0),
            arrayOf("2+3*4", 14.0),
            arrayOf("10%200", 20.0)
        )
    }
}