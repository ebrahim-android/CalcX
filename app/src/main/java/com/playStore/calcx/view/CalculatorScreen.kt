package com.playStore.calcx.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.playStore.calcx.controller.CalculatorController

@Preview(showBackground = true, backgroundColor = 0xFF202020)
@Composable
// Main composable that structures the entire calculator UI responsively using ConstraintLayout.
fun CalculatorScreen() {

    val controller = remember { CalculatorController() } // Controller instance

    fun handleButtonClick(label: String) {
        when (label) {

            // ---- DIGITS ----
            "0", "00", "1", "2", "3", "4", "5", "6", "7", "8", "9" ->
                controller.onDigitPressed(label)

            // ---- OPERATORS ----
            "+", "−", "×", "÷", "%" ->
                controller.onOperatorPressed(label)

            // ---- PARENTHESES ----
            "(", ")" ->
                controller.onParenthesisPressed(label)

            // ---- DECIMAL ----
            "." -> controller.onDecimalPointPressed()

            // ---- CLEAR ----
            "AC" -> controller.onClearPressed()

            // ---- DELETE ----
            "DEL" -> controller.onDeleteLast()

            // ---- EQUALS ----
            "=" -> controller.equalsPressed()

            // ---- NEW FUNCTIONS ----

            // ----- FACTORIAL -----
            "n!" -> controller.onFactorialPressed()

            // ----- EXPONENTIAL -----
            "exp" -> controller.onExpPressed()

            // ----- TENPOWER -----
            "10^x" -> controller.onTenPowerPressed()

//            "log" -> controller.onFunctionPressed("log")

            // ----- EULER -----
            "e^x" -> controller.onEulerPressed()

            "pow" -> controller.onPowerPressed()

            // ----- GENERAL ROOT -----
            "√" -> controller.onGeneralRootPressed()

            // ----- SQUARE ROOT -----
            "sqrt" -> controller.onSquareRootPressed()

            // ----- SQUARE -----
            "x²" -> controller.onSquarePress()

            // ----- GENERAL POWER -----
            "x^" -> controller.onGeneralPowerPress()

            // ---- SCIENTIFIC FUNCTIONS ----
            else -> controller.onFunctionPressed(label)
        }
    }

    // === Layout Colors ===
    val DarkTop = Color(0xFF202020)
    val DarkMiddle = Color(0xFF2A2A2A)
    val DarkBottom = Color(0xFF333333)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkTop)
    ) {
        // Create references for each main UI section (TopBar, Display, Navigation, Grids).
        val (topBarRef, displayRef, navControlsRef, scientificRef, numberPadRef) = remember {
            createRefs()
        }

        // Define Guidelines to divide vertical space based on percentages.
        // Total sum: 8% + 17% + 8% + 37% + 30% = 100%
        val topGuide = createGuidelineFromTop(0f)
        val endTopBar = createGuidelineFromTop(0.08f)   // TopBar (8%)
        val endDisplay = createGuidelineFromTop(0.28f)  // Display (17%)
        val endNavControls = createGuidelineFromTop(0.35f) // NavControls (8%)
        val endScientific = createGuidelineFromTop(0.70f) // Scientific Grid (37%)
        val endNumberPad = createGuidelineFromBottom(0f) // Number Pad (30%)

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
            TopBar()
        }

        // 3. Display Area (8% -> 25%)
        Box(
            modifier = Modifier
                .constrainAs(displayRef) {
                    top.linkTo(endTopBar)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(endDisplay)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(DarkTop)
                .padding(8.dp)
        ) {
            Display(
                expression = controller.expression,
                result = controller.result
            )
        }

        // 4. NavControls Area (25% -> 33%)
        Box(
            modifier = Modifier
                .constrainAs(navControlsRef) {
                    top.linkTo(endDisplay)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(endNavControls)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(DarkMiddle)
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            NavControlsRow()
        }

        // 5. Scientific Buttons Area (33% -> 70%)
        Box(
            modifier = Modifier
                .constrainAs(scientificRef) {
                    top.linkTo(endNavControls)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(endScientific)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(DarkBottom)
        ) {
            ScientificButtonsGrid(onButtonClick = { label ->
                handleButtonClick(label)
            })
        }

        // 6. Number Pad Area (70% -> 100%)
        Box(
            modifier = Modifier
                .constrainAs(numberPadRef) {
                    top.linkTo(endScientific)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(endNumberPad)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(DarkBottom)
        ) {
            NumberPadGrid(onButtonClick = { label ->
                handleButtonClick(label)
            })
        }
    }
}

// ------------------- TopBar -------------------

@Composable
// Renders the top menu icon and the current calculator mode text.
fun TopBar(
    modeText: String = "Standard",
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
            text = modeText,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
// Displays the formula and the result in the display area.
fun Display(
    expression: String = "",
    result: String = ""
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        // Formula (small text)
        Text(
            text = result,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFB0B0B0)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Auto-resizing result text
        AutoResizeText(
            text = if(expression.isBlank()) "0" else expression,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
            maxFontSize = 52.sp,
            minFontSize = 20.sp,
            color = Color.White
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
// Dynamically adjusts the font size of the text to fit within its container width.
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxFontSize: TextUnit = 48.sp,
    minFontSize: TextUnit = 16.sp,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.End,
    fontWeight: FontWeight = FontWeight.Bold
) {
    var textStyle by remember { mutableStateOf(TextStyle(fontSize = maxFontSize)) }
    var readyToDraw by remember { mutableStateOf(false) }

    BoxWithConstraints(modifier = modifier) {

        Text(
            text = text,
            color = color,
            textAlign = textAlign,
            fontWeight = fontWeight,
            maxLines = 1,
            softWrap = false,
            style = textStyle,

            onTextLayout = { result ->
                if (!readyToDraw && result.didOverflowWidth) {
                    val newFontSize = textStyle.fontSize * 0.9f
                    if (newFontSize >= minFontSize) {
                        textStyle = textStyle.copy(fontSize = newFontSize)
                    } else {
                        readyToDraw = true
                    }
                } else {
                    readyToDraw = true
                }
            }
        )
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
// Renders a single button for scientific functions.
fun ScientificButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonBackgroundColor = Color(0xFF4A4A4A)
    val cornerRadius = 8.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(cornerRadius))
            .background(buttonBackgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
// Lays out the 5x grid of scientific buttons, ensuring each button takes up equal, responsive space.
fun ScientificButtonsGrid(
    onButtonClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        val items = listOf(
            listOf("hyp", "And", "Or", "int", "Mode"),
            listOf("÷", "√x", "log", "eng", "π", "ln"),
            listOf("Rcl", "x²", "x^", "sin", "cos", "tan"),
            listOf("(-)", "e^x", "10^x", "n!", "MC", "abs"),
            listOf("MS", "(", ")", "M+", "M-", "MR")
        )

        // 1. Create references for each row
        val rowRefs = items.map { createRef() }.toTypedArray()

        // 2. Create Guidelines to divide the height into 5 equal parts (20% each).
        val horizontalGuides =
            (0..5).map { createGuidelineFromTop(it * 0.20f) }

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
                    ScientificButton(
                        label = label,
                        onClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f) // Buttons share horizontal space equally
                    )
                }
            }
        }
    }
}

// ------------------- Number Pad -------------------

@Composable
// Renders a single number or primary operator button, with special colors for AC/DEL/=.
fun NumberButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = when (label) {
        "DEL" -> Color(0xFFD32F2F) // Dark Red
        "AC" -> Color(0xFF00AC4C) // Green
        "=" -> Color(0xFF00AC4C) // Green
        else -> Color(0xFF5A5A5A)
    }

    val cornerRadius = 12.dp

    Box(
        modifier = modifier
            .fillMaxSize() // Fills the space provided by the ConstraintLayout row cell.
            .clip(RoundedCornerShape(cornerRadius))
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
// Lays out the 4x5 number pad grid, ensuring compact and equally sized buttons.
fun NumberPadGrid(
    onButtonClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        // Grid layout (4 rows, 5 columns)
        val items = listOf(
            listOf("7", "8", "9", "DEL", "AC"),
            listOf("4", "5", "6", "×", "÷"),
            listOf("1", "2", "3", "+", "−"),
            listOf("0", "00", ".", "%", "=")
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
                rowItems.forEach { label ->
                    NumberButton(
                        label = label,
                        onClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}