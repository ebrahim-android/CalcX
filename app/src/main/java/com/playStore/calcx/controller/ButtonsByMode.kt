package com.playStore.calcx.controller

object ButtonsByMode {

    fun buttonsFor(mode: CalculatorMode): List<CalculatorButton> {
        return when (mode) {
            CalculatorMode.STANDARD -> standardButtons
            CalculatorMode.SCIENTIFIC -> standardButtons + scientificButtons
            CalculatorMode.PROGRAMMER -> standardButtons + scientificButtons + programmerButtons
        }
    }

    private val standardButtons = listOf(
        CalculatorButton(ButtonId.DIGIT_0, "0", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_1, "1", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.ADD, "+", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.SUBTRACT, "-", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.EQUALS, "=", ButtonCategory.STANDARD),
        // etc
    )

    private val scientificButtons = listOf(
        CalculatorButton(ButtonId.SIN, "sin", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.COS, "cos", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.TAN, "tan", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.PI, "π", ButtonCategory.SCIENTIFIC),
        // etc
    )

    private val programmerButtons = listOf(
        CalculatorButton(ButtonId.AND, "AND", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.OR, "OR", ButtonCategory.PROGRAMMER),
        // futuro
    )
}