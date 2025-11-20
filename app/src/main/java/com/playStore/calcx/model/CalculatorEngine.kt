package com.playStore.calcx.model

import com.playStore.calcx.engine.CustomFunctions
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorEngine {

    fun evaluate(expression: String):Double{
        val exp = ExpressionBuilder(expression)
            .functions(CustomFunctions.all)
            .operator(CustomOperators.all)
            .build()

        return exp.evaluate()
    }
}