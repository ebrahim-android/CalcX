package com.playStore.calcx.engine

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
            arrayOf("tan(0)", 0.0)
        )
    }
}