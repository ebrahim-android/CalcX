package com.playStore.calcx.engine

import com.playStore.calcx.model.CalculatorEngine
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test


@RunWith(Parameterized::class)
class CalculatorEngineEdgeCasesTest (
    private val input: String
){

    val engine = CalculatorEngine()

    @Test
    fun `invalid expressions should return null`() {
        val result = engine.evaluate("")
        assert(result == null)
    }

    companion object{
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(""),
            arrayOf("2+"),
            arrayOf("+2"),
            arrayOf("2++2"),
            arrayOf("2--2"),
            arrayOf("2*"),
        )
    }
}