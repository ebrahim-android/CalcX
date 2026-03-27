//package com.playStore.calcx.controller
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//
//enum class FunctionMode {
//    PRIMARY,
//    SECONDARY
//}
//
//var functionMode by mutableStateOf(FunctionMode.PRIMARY)
//    private set
//
//fun onShiftPressed() {
//    functionMode = if (functionMode == FunctionMode.PRIMARY) {
//        FunctionMode.SECONDARY
//    } else {
//        FunctionMode.PRIMARY
//    }
//}