package com.playStore.calcx.config

import com.playStore.calcx.domain.CalculatorButton
import com.playStore.calcx.domain.enums.ButtonCategory
import com.playStore.calcx.domain.enums.ButtonId
import com.playStore.calcx.domain.enums.CalculatorMode

object ButtonsByMode {

    fun buttonsFor(mode: CalculatorMode): List<CalculatorButton> {
        return when (mode) {
            CalculatorMode.STANDARD -> standardButtons
            CalculatorMode.SCIENTIFIC -> standardButtons + scientificButtons
            CalculatorMode.PROGRAMMER -> standardButtons + scientificButtons + programmerButtons
        }
    }

    fun standardCompactButtons(): List<CalculatorButton> {
        return listOf(
            CalculatorButton(ButtonId.POWER, "xʸ", ButtonCategory.SCIENTIFIC),
            CalculatorButton(ButtonId.OPEN_PAREN, "(", ButtonCategory.STANDARD),
            CalculatorButton(ButtonId.CLOSE_PAREN, ")", ButtonCategory.STANDARD),
            CalculatorButton(ButtonId.MODE_TOGGLE, "Mode", ButtonCategory.MODE)
        )
    }

    private val standardButtons = listOf(
        CalculatorButton(ButtonId.DIGIT_0, "0", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_1, "1", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_2, "2", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_3, "3", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_4, "4", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_5, "5", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_6, "6", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_7, "7", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_8, "8", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIGIT_9, "9", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DECIMAL, ".", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.OPEN_PAREN, "(", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.CLOSE_PAREN, ")", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.PERCENT, "%", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.MULTIPLY, "×", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DIVIDE, "÷", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.SHIFT_LEFT, "<<", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.SHIFT_RIGHT, ">>", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.ADD, "+", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.SUBTRACT, "-", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.EQUALS, "=", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.DELETE, "DEL", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.CLEAR, "AC", ButtonCategory.STANDARD),
        CalculatorButton(ButtonId.MODE_TOGGLE, "Mode", ButtonCategory.MODE),
    )

    private val scientificButtons = listOf(
        CalculatorButton(ButtonId.SIN, "sin", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.COS, "cos", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.TAN, "tan", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.PI, "π", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.EULER, "e", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.LOG, "log", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.LN, "ln", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.SQUARE, "x²", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.POWER, "x^", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.SQRT, "√x", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.FACTORIAL, "n!", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.MEMORY_CLEAR, "MC", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.MEMORY_READ, "MR", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.MEMORY_SAVE, "MS", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.MEMORY_ADD, "M+", ButtonCategory.SCIENTIFIC),
        CalculatorButton(ButtonId.MEMORY_SUBTRACT, "M-", ButtonCategory.SCIENTIFIC),
    )

    private val programmerButtons = listOf(
        CalculatorButton(ButtonId.AND, "AND", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.OR, "OR", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.XOR, "XOR", ButtonCategory.PROGRAMMER),
        CalculatorButton(ButtonId.NOT, "NOT", ButtonCategory.PROGRAMMER),
    )
}