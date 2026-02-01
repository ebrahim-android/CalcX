package com.playStore.calcx.engine

import com.playStore.calcx.model.CalculatorEngine
import org.junit.Assert
import org.junit.Test

class CalculatorEngineTest {

    private val engine = CalculatorEngine()

    @Test
    fun `adding two numbers returns correct result`(){ //sum
        val result = engine.evaluate("2+2")!!
        Assert.assertEquals(4.0, result, 0.0001)
    }

    @Test
    fun `subtracting two numbers returns correct result`(){ //rest
        val result = engine.evaluate("5-2")!!
        Assert.assertEquals(3.0, result, 0.0001)
    }

    @Test
    fun `division by zero should return null`(){ //division by zero
        val result = engine.evaluate("40/0")
        assert(result == null)
    }

    @Test
    fun `adding parenthesis return correct result`(){ //parenthesis
        val resul = engine.evaluate("2+(2+2)")!!
        Assert.assertEquals(6.0, resul, 0.0001)
    }

    @Test
    fun `factorial return correct result`(){ //factorial
        val result = engine.evaluate("5!")!!
        Assert.assertEquals(120.0, result, 0.0001)
    }

    @Test
    fun `multiplying two numbers returns correct result`(){ //multiplication
        val result = engine.evaluate("2*2")!!
        Assert.assertEquals(4.0, result, 0.0001)
    }

    @Test
    fun `square root return correct result plural`(){ //square root
        val result = engine.evaluate("√9")!!
        Assert.assertEquals(3.0, result, 0.0001)
    }

    @Test
    fun `pi return correct result`() { //pi
        val result = engine.evaluate("pi")!!
        Assert.assertEquals(Math.PI, result, 0.0001)
    }

    @Test
    fun `power should return correct result`() { //power
        val result = engine.evaluate("2^3")!!
        Assert.assertEquals(8.0, result, 0.0001)
    }

    @Test
    fun `power of 10 should return correct result`() { //power of 10
        val result = engine.evaluate("10^2")!!
        Assert.assertEquals(100.0, result, 0.0001)
    }

    @Test
    fun `cos should return correct result`() { //cos
        val result = engine.evaluate("cos(0)")!!
        Assert.assertEquals(1.0, result, 0.0001)
    }

    @Test
    fun `sin should return correct result`() { //sin
        val result = engine.evaluate("sin(0)")!!
        Assert.assertEquals(0.0, result, 0.0001)
    }

    @Test
    fun `tan should return correct result`() { //tan
        val result = engine.evaluate("tan(0)")!!
        Assert.assertEquals(0.0, result, 0.0001)
    }

    @Test
    fun `cos(pi) should return -1`() { // cos(pi)
        val result = engine.evaluate("cos(pi)")!!
        Assert.assertEquals(-1.0, result, 0.000)
    }

    @Test
    fun `sin(pi division (pi medium) 2) should return 1`() { // sin(pi/2)
        val result = engine.evaluate("sin(pi/2)")!!
        Assert.assertEquals(1.0, result, 0.000)
    }

    @Test
    fun `euler should return correct result`() { //euler
        val resul = engine.evaluate("e^2")!!
        Assert.assertEquals(7.38905609893065, resul, 0.0001)
    }

    @Test
    fun `log should return correct result`() { //log
        val result = engine.evaluate("log(100)")!!
        Assert.assertEquals(2.0, result, 0.0001)
    }

    @Test
    fun `percentage should return correct result`() { //porcentage
        val result = engine.evaluate("10%200")!!
        Assert.assertEquals(20.0, result, 0.0001)
    }

    @Test
    fun `square should return correct result`() {
        val result = engine.evaluate("5x²")!!
        Assert.assertEquals(25.0, result, 0.0001)
    }

    @Test
    fun `negative numbers should return correct result`() {
        val result = engine.evaluate("-5+2")!!
        Assert.assertEquals(-3.0, result, 0.0001)
    }

    @Test
    fun `mixed operations should return correct result`() {
        val result = engine.evaluate("2+3*4")!!
        Assert.assertEquals(14.0, result, 0.0001)
    }

    @Test
    fun `invalid expression should return null`() {
        val result = engine.evaluate("2+x")
        Assert.assertEquals(null, result)
    }

    @Test
    fun `square root of negative number should return null`() {
        val result = engine.evaluate("√-9")
        Assert.assertEquals(null, result)
    }
}
