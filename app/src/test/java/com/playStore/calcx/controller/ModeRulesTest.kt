package com.playStore.calcx.controller

import com.playStore.calcx.domain.enums.ButtonCategory
import com.playStore.calcx.domain.enums.CalculatorMode
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ModeRulesTest {

    @Test
    fun `STANDARD mode enables only STANDARD buttons`() {
        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.STANDARD,
                category = ButtonCategory.STANDARD
            )
        )

        assertFalse(
            isButtonEnabled(
                mode = CalculatorMode.STANDARD,
                category = ButtonCategory.SCIENTIFIC
            )
        )
    }

    @Test
    fun `SCIENTIFIC mode enables STANDARD and SCIENTIFIC buttons`() {
        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.SCIENTIFIC,
                category = ButtonCategory.STANDARD
            )
        )

        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.SCIENTIFIC,
                category = ButtonCategory.SCIENTIFIC
            )
        )
    }

    @Test
    fun `PROGRAMMER mode enables all buttons`() {
        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.PROGRAMMER,
                category = ButtonCategory.STANDARD
            )
        )

        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.PROGRAMMER,
                category = ButtonCategory.SCIENTIFIC
            )
        )

        assertTrue(
            isButtonEnabled(
                mode = CalculatorMode.PROGRAMMER,
                category = ButtonCategory.PROGRAMMER
            )
        )
    }

    @Test
    fun `mode button is always enabled`() {
        CalculatorMode.values().forEach { mode ->
            assertTrue(
                isButtonEnabled(
                    mode = mode,
                    category = ButtonCategory.MODE
                )
            )
        }
    }
}


