package com.playStore.calcx.domain.enums

enum class ButtonId(val label: String) {

    // Digits
    DIGIT_0("0"), DIGIT_1("1"), DIGIT_2("2"), DIGIT_3("3"), DIGIT_4("4"),
    DIGIT_5("5"), DIGIT_6("6"), DIGIT_7("7"), DIGIT_8("8"), DIGIT_9("9"),

    // Basic operators
    ADD("+"), SUBTRACT("−"), MULTIPLY("×"), DIVIDE("÷"), PERCENT("%"),

    // Expression control
    DECIMAL("."), OPEN_PAREN("("), CLOSE_PAREN(")"),
    EQUALS("="), DELETE("DEL"), CLEAR("AC"),

    // Scientific
    SIN("sin"), COS("cos"), TAN("tan"),
    LOG("log"), LN("ln"),
    POWER("x^"), SQUARE("x²"), SQRT("√x"), FACTORIAL("n!"),
    PI("π"), EULER("e"),
    ENG("eng"), RECALL("Rcl"),
    NEGATE("(-)"),
    TEN_POWER("10^x"), ABS("abs"),

    // Memory
    MEMORY_CLEAR("MC"), MEMORY_READ("MR"), MEMORY_SAVE("MS"),
    MEMORY_ADD("M+"), MEMORY_SUBTRACT("M-"),

    // Cursor
    CURSOR_LEFT("◀"), CURSOR_RIGHT("▶"),

    // Mode
    MODE_TOGGLE("Mode"),

    // Programmer (future)
    AND("AND"), OR("OR"), XOR("XOR"), NOT("NOT"),
    SHIFT_LEFT("<<"), SHIFT_RIGHT(">>"),
    BIN("BIN"), OCT("OCT"), DEC("DEC"), HEX("HEX")
}