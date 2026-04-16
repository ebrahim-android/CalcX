package com.playStore.calcx.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.playStore.calcx.controller.isButtonEnabled
import com.playStore.calcx.domain.enums.ButtonCategory
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.playStore.calcx.domain.enums.ButtonId
import com.playStore.calcx.config.ButtonsByMode
import com.playStore.calcx.controller.CalculatorController
import com.playStore.calcx.domain.CalculatorButton
import com.playStore.calcx.domain.FunctionKeys
import com.playStore.calcx.domain.enums.CalculatorMode
import com.playStore.calcx.domain.enums.FunctionMode
import kotlinx.coroutines.delay

@Preview(showBackground = true, backgroundColor = 0xFF202020)
@Composable
// Main composable that structures the entire calculator UI responsively using ConstraintLayout.
fun CalculatorScreen() {

    val controller = remember { CalculatorController() } // Controller instance
    val mode = controller.mode

    var showMenu by remember { mutableStateOf(false) } //to menu

    if (showMenu) {
        AlertDialog(
            onDismissRequest = { showMenu = false },
            confirmButton = {
                TextButton(onClick = { showMenu = false }) {
                    Text("OK")
                }
            },
            title = {
                Text("Próximas actualizaciones")
            },
            text = {
                Text(
                    "• Sistema de fracciones\n" +
                            "• Historial de cálculos\n" +
                            "• Mejoras en funciones científicas\n" +
                            "• Más modos y conversiones"
                )
            }
        )
    }

    val displayHeight by animateDpAsState(
        targetValue = when (mode) {
            CalculatorMode.STANDARD -> 120.dp
            CalculatorMode.SCIENTIFIC -> 160.dp
            CalculatorMode.PROGRAMMER -> 140.dp
        },
        label = "displayHeight"
    )

    val scientificHeight by animateDpAsState(
        targetValue = when (mode) {
            CalculatorMode.STANDARD -> 72.dp      // collapsed
            CalculatorMode.SCIENTIFIC -> 290.dp  // expanded
            CalculatorMode.PROGRAMMER -> 290.dp
        },
        label = "scientificHeight"
    )

    val localExpression = remember {
        mutableStateOf(TextFieldValue(""))
    }

    // === Layout Colors ===
    val DarkTop = Color(0xFF202020)
    val DarkMiddle = Color(0xFF2A2A2A)
    val DarkBottom = Color(0xFF333333)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkTop)
            .windowInsetsPadding(
                WindowInsets.safeDrawing
            )
    ) {
        // Create references for each main UI section (TopBar, Display, Navigation, Grids).
        val (topBarRef, displayRef, navControlsRef, scientificRef, numberPadRef) = remember {
            createRefs()
        }

        val numberPadHeight = 280.dp   // size of the number pad

        // Define Guidelines to divide vertical space based on percentages.
        // Total sum: 8% + 17% + 8% + 37% + 30% = 100%
        val topGuide = createGuidelineFromTop(0f)
        val endTopBar = createGuidelineFromTop(0.08f)   // TopBar (8%)

        // 2. TopBar Area (0% -> 8%)
        Box(
            modifier = Modifier
                .constrainAs(topBarRef) {
                    top.linkTo(topGuide)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(endTopBar)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(DarkTop)
        ) {
            TopBar(
                mode = controller.mode,
                onMenuClick = { showMenu = true },
            )
        }

        // 3. Display Area (8% -> 25%)
        Box(
            modifier = Modifier
                .constrainAs(displayRef) {
                    top.linkTo(topBarRef.bottom)
                    bottom.linkTo(navControlsRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    height = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .height(displayHeight)
                .background(DarkTop)
                .padding(8.dp)
        ) {
            Display(
                expression = controller.expression,
                lastExpression = controller.lastExpression,
                onExpressionChange = { controller.expression = it } // added this
            )
        }

        // 4. NavControls Area (25% -> 33%)
        Box(
            modifier = Modifier
                .constrainAs(navControlsRef) {
                    bottom.linkTo(scientificRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(64.dp)
//                .padding(horizontal = 8.dp, vertical = 10.dp)
                .background(DarkMiddle)
        ) {
            NavControlsRow(
                onLeftClick = { controller.moveCursorLeft() },
                onRightClick = { controller.moveCursorRight() },
            )
        }

        // 5. Scientific Buttons Area (33% -> 70%)
        Box(
            modifier = Modifier
                .constrainAs(scientificRef) {
                    bottom.linkTo(numberPadRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(scientificHeight)
                .background(DarkBottom)
        ) {
            ScientificButtonsGrid(
                mode = controller.mode,
                controller = controller,
                onButtonPress = { id ->
                    controller.onButtonPressed(id)
                })
        }

        // 6. Number Pad Area (70% -> 100%)
        Box(
            modifier = Modifier
                .constrainAs(numberPadRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(numberPadHeight)
                .background(DarkBottom)
        ) {
            NumberPadGrid(onButtonClick = { id ->
                controller.onButtonPressed(id)
            })
        }
    }
}

// ------------------- TopBar -------------------

@Composable
// Renders the top menu icon and the current calculator mode text.
fun TopBar(
    mode: CalculatorMode,
    onMenuClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Menu button
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White
            )
        }

        // Mode text
        Text(
            text = when (mode) {
                CalculatorMode.STANDARD -> "Standard"
                CalculatorMode.SCIENTIFIC -> "Scientific"
                CalculatorMode.PROGRAMMER -> "Programmer"
            },
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
// Displays the formula and the result in a modern card-style container.
fun Display(
    expression: TextFieldValue,
    lastExpression: String,
    onExpressionChange: (TextFieldValue) -> Unit
) {

    // Request focus when the screen is opened
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // Track text layout for custom cursor drawing
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    // Blinking cursor animation
    var cursorVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            cursorVisible = !cursorVisible
            delay(500)
        }
    }

    // Card-style container for display
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF2A2A2A))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {

            // Previous expression (small text)
            Text(
                text = lastExpression,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF9E9E9E),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Main expression / result (large text)
            BasicTextField(
                value = expression,
                onValueChange = onExpressionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .drawBehind {
                        val layout = textLayoutResult ?: return@drawBehind

                        val cursorIndex = expression.selection.start
                        val cursorRect = layout.getCursorRect(cursorIndex)

                        if (cursorVisible) {
                            drawLine(
                                color = Color.White,
                                start = cursorRect.topLeft,
                                end = cursorRect.bottomRight,
                                strokeWidth = 2f
                            )
                        }
                    },
                singleLine = true,
                readOnly = true, // Prevent manual typing
                interactionSource = remember { MutableInteractionSource() },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 42.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold
                ),
                onTextLayout = { layoutResult ->
                    textLayoutResult = layoutResult
                },
                decorationBox = { innerTextField ->
                    if (expression.text.isEmpty()) {
                        Text(
                            text = "0",
                            color = Color.Gray,
                            fontSize = 42.sp,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
// Renders the row of navigation (arrow) buttons for input editing.
fun NavControlsRow(
    modifier: Modifier = Modifier,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    onUpClick: () -> Unit = {},
    onDownClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavCircleButton(icon = Icons.Default.KeyboardArrowLeft, onClick = onLeftClick)
        NavCircleButton(icon = Icons.Default.KeyboardArrowUp, onClick = onUpClick)
        NavCircleButton(icon = Icons.Default.KeyboardArrowDown, onClick = onDownClick)
        NavCircleButton(icon = Icons.Default.KeyboardArrowRight, onClick = onRightClick)
    }
}

@Composable
fun NavCircleButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0x22FFFFFF), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

// ------------------- Scientific Buttons -------------------

@Composable
// Scientific button with unified styling.
fun ScientificButton(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = Color(0xFF3A3A3A)

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (enabled) Color.White else Color.White.copy(alpha = 0.4f),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
// Lays out the 5x grid of scientific buttons, ensuring each button takes up equal, responsive space.
fun ScientificButtonsGrid(
    controller: CalculatorController,
    mode: CalculatorMode,
    onButtonPress: (ButtonId) -> Unit,
) {

    val isScientific = mode == CalculatorMode.SCIENTIFIC || mode == CalculatorMode.PROGRAMMER
    val isShift = controller.functionMode == FunctionMode.SECONDARY

    val CalculatorButtonShape = RoundedCornerShape(14.dp)

    val buttons = when (mode) {
        CalculatorMode.STANDARD -> ButtonsByMode.standardCompactButtons()
        else -> ButtonsByMode.buttonsFor(mode)
    }


    val buttonMap = buttons.associateBy { it.label }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val items = when (mode) {

            CalculatorMode.STANDARD -> listOf(
                listOf("xʸ", "(", ")", "Mode")
            )

            else -> listOf(
                listOf("XOR", "AND", "OR", "NOT", "SH", "Mode"),
                listOf("÷", "√x", "log", "eng", "π", "ln"),
                listOf("Rcl", "x²", "x^", "sin", "cos", "tan"),
                listOf("(-)", "e^x", "10^x", "n!", "MC", "abs"),
                listOf("MS", "(", ")", "M+", "M-", "MR")
            )
        }


        // 1. Create references for each row
        val rowRefs = items.map { createRef() }.toTypedArray()

        // 2. Create Guidelines to divide the height into 5 equal parts (20% each).
        val rowCount = items.size
        val horizontalGuides = (0..rowCount).map {
            createGuidelineFromTop(it * (1f / rowCount))
        }

        // Vertical spacing between rows
        val rowSpacing = 8.dp

        // 3. Position and size each row using constraints to the guidelines.
        items.forEachIndexed { rowIndex, rowItems ->
            val topGuide = horizontalGuides[rowIndex]
            val bottomGuide = horizontalGuides[rowIndex + 1]
            val rowRef = rowRefs[rowIndex]

            Row(
                modifier = Modifier.constrainAs(rowRef) {
                    top.linkTo(topGuide, margin = if (rowIndex == 0) 0.dp else rowSpacing / 2)
                    bottom.linkTo(
                        bottomGuide,
                        margin = if (rowIndex == items.size - 1) 0.dp else rowSpacing / 2
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints // Height is constrained by guidelines
                },
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { label ->

                    if (label == "SH") {

                        Button(
                            onClick = { controller.onShiftPressed() },
                            enabled = isScientific,
                            modifier = Modifier.weight(1f)
                                .fillMaxHeight(),
                            shape = CalculatorButtonShape,
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isShift) Color(0xFFFFC107) else Color(0xFF5A5A5A)
                            )
                        ) {
                            Text(
                                text = label,
                                fontSize = 16.sp,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }

                    } else {

                        val button = buttonMap[label]

                        if (button != null) {

                            val dynamicLabel = when (button.id) {
                                ButtonId.SIN -> controller.getFunctionLabel(FunctionKeys.SIN)
                                ButtonId.COS -> controller.getFunctionLabel(FunctionKeys.COS)
                                ButtonId.TAN -> controller.getFunctionLabel(FunctionKeys.TAN)
                                ButtonId.LOG -> controller.getFunctionLabel(FunctionKeys.LOG)
                                ButtonId.LN -> controller.getFunctionLabel(FunctionKeys.LN)
                                else -> button.label
                            }

                            val enabled = isButtonEnabled(mode, button.category)

                            ScientificButton(
                                label = dynamicLabel,
                                enabled = enabled,
                                onClick = { onButtonPress(button.id) },
                                modifier = Modifier.weight(1f)
                            )

                        } else {

                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

fun categoryFor(label: String): ButtonCategory =
    //categoryFor allow us to know which category the button belongs to
    when (label) {
        "Mode" -> ButtonCategory.MODE

        "sin", "cos", "tan",
        "log", "ln", "√x",
        "x²", "x^", "e^x",
        "10^x", "n!" ->
            ButtonCategory.SCIENTIFIC

        "XOR", "AND", "OR", "NOT" ->
            ButtonCategory.PROGRAMMER

        else -> ButtonCategory.STANDARD
    }


// ------------------- Number Pad -------------------

@Composable
// Base button used across the calculator for consistent styling.
fun CalculatorButtonView(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false,
    isDanger: Boolean = false
) {

    val background = when {
        isPrimary -> Color(0xFF00AC4C) // primary action (=)
        isDanger -> Color(0xFFD32F2F)  // destructive (DEL)
        else -> Color(0xFF3A3A3A)      // default
    }

    val textColor = Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp)) // smoother corners
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
// Lays out the 4x5 number pad grid, ensuring compact and equally sized buttons.
fun NumberPadGrid(
    onButtonClick: (ButtonId) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        // Grid layout (4 rows, 5 columns)
        val items = listOf(
            listOf(
                ButtonId.DIGIT_7,
                ButtonId.DIGIT_8,
                ButtonId.DIGIT_9,
                ButtonId.DELETE,
                ButtonId.CLEAR
            ),
            listOf(
                ButtonId.DIGIT_4,
                ButtonId.DIGIT_5,
                ButtonId.DIGIT_6,
                ButtonId.MULTIPLY,
                ButtonId.DIVIDE
            ),
            listOf(
                ButtonId.DIGIT_1,
                ButtonId.DIGIT_2,
                ButtonId.DIGIT_3,
                ButtonId.ADD,
                ButtonId.SUBTRACT
            ),
            listOf(
                ButtonId.DIGIT_0,
                ButtonId.DIGIT_DOUBLE_0,
                ButtonId.DECIMAL,
                ButtonId.PERCENT,
                ButtonId.EQUALS
            )
        )

        // 1. Create references for each row
        val rowRefs = items.map { createRef() }.toTypedArray()

        // 2. Create Guidelines to divide the height into 4 equal parts (25% each).
        val horizontalGuides =
            (0..4).map { createGuidelineFromTop(it * 0.25f) }

        // Vertical and horizontal spacing
        val spacing = 8.dp

        // 3. Position and size each row using constraints to the guidelines.
        items.forEachIndexed { rowIndex, rowItems ->
            val topGuide = horizontalGuides[rowIndex]
            val bottomGuide = horizontalGuides[rowIndex + 1]
            val rowRef = rowRefs[rowIndex]

            Row(
                modifier = Modifier.constrainAs(rowRef) {
                    top.linkTo(topGuide, margin = if (rowIndex == 0) 0.dp else spacing / 2)
                    bottom.linkTo(
                        bottomGuide,
                        margin = if (rowIndex == items.size - 1) 0.dp else spacing / 2
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints // Height is constrained by guidelines
                },
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                rowItems.forEach { id ->
                    CalculatorButtonView(
                        label = id.label,
                        onClick = { onButtonClick(id) },
                        modifier = Modifier.weight(1f),
                        isPrimary = id == ButtonId.EQUALS,
                        isDanger = id == ButtonId.DELETE
                    )
                }
            }
        }
    }
}

