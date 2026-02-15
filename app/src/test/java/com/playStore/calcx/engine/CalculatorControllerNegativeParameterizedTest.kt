package com.playStore.calcx.engine

import com.playStore.calcx.controller.CalculatorController
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class CalculatorControllerNegativeParameterizedTest(
    val expression: String,
    val expectedResult: String
) {

    private lateinit var controller: CalculatorController

    @Before
    fun setUp() {
        controller = CalculatorController()
    }

    @Test
    fun `should evaluate negative expressions correctly`(){
        controller.insert(expression)
        controller.onEqualsPressed()

        assertEquals(expectedResult, controller.result)

    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0} = {1}")
        fun data() = listOf(
            arrayOf("-2+2", "0"),
            arrayOf("-5-2", "-7"),
            arrayOf("-2*3", "-6"),
            arrayOf("-10/2", "-5"),
            arrayOf("-2^3", "-8"),
            arrayOf("-√9", "-3"),
            arrayOf("-5!", "-120"),
            arrayOf("-2+3*4", "10"),
            arrayOf("-10%200", "-20")
        )
    }

}