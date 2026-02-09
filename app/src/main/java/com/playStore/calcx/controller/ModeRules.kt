package com.playStore.calcx.controller

fun isButtonEnabled(
    mode: CalculatorMode,
    category: ButtonCategory
): Boolean {
    return when (mode) {
        CalculatorMode.STANDARD -> category == ButtonCategory.STANDARD

        CalculatorMode.SCIENTIFIC ->
            category == ButtonCategory.STANDARD ||
                    category == ButtonCategory.SCIENTIFIC

        CalculatorMode.PROGRAMMER ->
            true
    }
}