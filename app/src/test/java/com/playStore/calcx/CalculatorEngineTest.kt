package com.playStore.calcx

import com.playStore.calcx.model.CalculatorEngine
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorEngineTest {

    private val engine = CalculatorEngine()

    @Test
    fun `adding two numbers returns correct result`(){ //sum
        val result = engine.evaluate("2+2")!!
        assertEquals(4.0, result, 0.0001)
    }

    @Test
    fun `subtracting two numbers returns correct result`(){ //rest
        val result = engine.evaluate("5-2")!!
        assertEquals(3.0, result, 0.0001)
    }

    @Test
    fun `division by zero should return null`(){ //division by zero
        val result = engine.evaluate("40/0")
        assert(result == null)
    }

    @Test
    fun `adding parenthesis return correct result`(){ //parenthesis
        val resul = engine.evaluate("2+(2+2)")!!
        assertEquals(6.0, resul, 0.0001)
    }

    @Test
    fun `factorial return correct result`(){ //factorial
        val result = engine.evaluate("5!")!!
        assertEquals(120.0, result, 0.0001)
    }

    @Test
    fun `multiplying two numbers returns correct result`(){ //multiplication
        val result = engine.evaluate("2*2")!!
        assertEquals(4.0, result, 0.0001)
    }


}

