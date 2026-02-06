package com.github.mikephil.charting.compose.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Position of the limit line label relative to the line.
 */
enum class LimitLabelPosition {
    LEFT_TOP,
    LEFT_BOTTOM,
    RIGHT_TOP,
    RIGHT_BOTTOM
}

/**
 * Configuration for a limit line displayed on an axis.
 * Limit lines mark a certain threshold/limit on the chart.
 *
 * @property value The position (value) on the axis where the line appears
 * @property label Text drawn next to the limit line
 * @property lineColor Color of the limit line
 * @property lineWidth Width of the limit line
 * @property pathEffect Dash effect for the line (null = solid)
 * @property labelPosition Position of the label relative to the line
 * @property labelColor Color of the label text
 * @property labelTextSize Size of the label text
 * @property labelFontFamily Font family for the label
 * @property enabled Whether this limit line is drawn
 */
data class LimitLineConfig(
    val value: Float,
    val label: String = "",
    val lineColor: Color = Color(0xFFED5B5B),
    val lineWidth: Dp = 2.dp,
    val pathEffect: PathEffect? = null,
    val labelPosition: LimitLabelPosition = LimitLabelPosition.RIGHT_TOP,
    val labelColor: Color = Color.Black,
    val labelTextSize: TextUnit = 10.sp,
    val labelFontFamily: FontFamily? = null,
    val enabled: Boolean = true
) {
    fun withDash(lineLength: Float, spaceLength: Float, phase: Float = 0f): LimitLineConfig =
        copy(pathEffect = PathEffect.dashPathEffect(floatArrayOf(lineLength, spaceLength), phase))
}
