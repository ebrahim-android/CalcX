package com.playStore.calcx.domain

import com.playStore.calcx.domain.enums.ButtonCategory
import com.playStore.calcx.domain.enums.ButtonId

data class CalculatorButton (
    val id: ButtonId,
    val label: String,
    val category: ButtonCategory,
)