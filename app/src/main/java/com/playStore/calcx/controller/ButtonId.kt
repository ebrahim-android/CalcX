package com.playStore.calcx.controller

enum class ButtonId {

    // Digits
    DIGIT_0, DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_4,
    DIGIT_5, DIGIT_6, DIGIT_7, DIGIT_8, DIGIT_9,

    // Basic operators
    ADD, SUBTRACT, MULTIPLY, DIVIDE, PERCENT,

    // Expression control
    DECIMAL, OPEN_PAREN, CLOSE_PAREN,
    EQUALS, DELETE, CLEAR,

    // Scientific
    SIN, COS, TAN,
    LOG, LN,
    POWER, SQUARE, SQRT, FACTORIAL,
    PI, EULER,

    // Memory
    MEMORY_CLEAR, MEMORY_READ, MEMORY_SAVE,
    MEMORY_ADD, MEMORY_SUBTRACT,

    // Cursor
    CURSOR_LEFT, CURSOR_RIGHT,

    // Mode
    MODE_TOGGLE,

    // Programmer (future)
    AND, OR, XOR, NOT,
    SHIFT_LEFT, SHIFT_RIGHT,
    BIN, OCT, DEC, HEX
}
