package com.playStore.calcx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable // Necesario para la función Preview
import androidx.compose.ui.tooling.preview.Preview // Necesario para la función Preview

// 1. IMPORTAR la función CalculatorScreen
import com.playStore.calcx.view.CalculatorScreen
import com.playStore.calcx.ui.theme.CalcXTheme // Asegúrate de importar tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 2. LLAMAR la función CalculatorScreen dentro de tu tema
            CalcXTheme {
                CalculatorScreen()
            }
        }
    }
}

// ---------------------------------------------

// 3. Crear la función Preview
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalcXTheme {
        // Llama a la misma función aquí para verla en el panel de diseño de Android Studio
        CalculatorScreen()

    }
}