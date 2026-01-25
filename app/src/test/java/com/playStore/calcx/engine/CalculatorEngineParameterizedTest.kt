package com.playStore.calcx.engine

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



}