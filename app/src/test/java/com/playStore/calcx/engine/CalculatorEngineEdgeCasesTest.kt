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
            arrayOf("2/"),
            arrayOf("2^"),
            arrayOf("2√"),
            arrayOf("2!"),
            arrayOf("2sin"),
            arrayOf("2cos"),
            arrayOf("2tan"),
            arrayOf("2log"),
            arrayOf("2ln"),
            arrayOf("2π"),
            arrayOf("2e"),
            arrayOf("2AND"),
            arrayOf("2OR"),
            arrayOf("2XOR"),
            arrayOf("2NOT"),
            arrayOf("2MS"),
            arrayOf("2MR"),
            arrayOf("2MC"),
            arrayOf("2M-"),
            arrayOf("2M+"),
            arrayOf("2eng"),
            arrayOf("2Rcl"),
            arrayOf("2(-)"),
            arrayOf("210^"),
            arrayOf("2abs")
        )
    }
}