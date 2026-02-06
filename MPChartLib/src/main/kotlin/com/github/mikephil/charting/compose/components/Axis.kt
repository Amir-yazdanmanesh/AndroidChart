package com.github.mikephil.charting.compose.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Position of the X-axis labels relative to the chart.
 */
enum class XAxisPosition {
    TOP,
    BOTTOM,
    BOTH_SIDED,
    TOP_INSIDE,
    BOTTOM_INSIDE
}

/**
 * Position of the Y-axis labels relative to the chart.
 */
enum class YAxisLabelPosition {
    OUTSIDE_CHART,
    INSIDE_CHART
}

/**
 * Value formatter for axis labels.
 */
fun interface AxisValueFormatter {
    fun format(value: Float): String
}

/**
 * Base configuration shared by both X and Y axes.
 */
data class AxisConfig(
    val enabled: Boolean = true,
    val drawLabels: Boolean = true,
    val drawAxisLine: Boolean = true,
    val drawGridLines: Boolean = true,
    val textColor: Color = Color.Black,
    val textSize: TextUnit = 10.sp,
    val fontFamily: FontFamily? = null,
    val gridColor: Color = Color.Gray,
    val gridLineWidth: Dp = 1.dp,
    val gridPathEffect: PathEffect? = null,
    val axisLineColor: Color = Color.Gray,
    val axisLineWidth: Dp = 1.dp,
    val axisLinePathEffect: PathEffect? = null,
    val labelCount: Int = 6,
    val forceLabels: Boolean = false,
    val granularity: Float = 1f,
    val granularityEnabled: Boolean = false,
    val axisMinimum: Float? = null,
    val axisMaximum: Float? = null,
    val spaceMin: Float = 0f,
    val spaceMax: Float = 0f,
    val centerAxisLabels: Boolean = false,
    val drawLimitLinesBehindData: Boolean = false,
    val drawGridLinesBehindData: Boolean = true,
    val limitLines: List<LimitLineConfig> = emptyList(),
    val valueFormatter: AxisValueFormatter? = null
)

/**
 * Configuration for the X-axis.
 *
 * @property axisConfig Base axis configuration
 * @property position Position of labels relative to the chart
 * @property labelRotationAngle Rotation angle for labels (in degrees)
 * @property avoidFirstLastClipping Avoid clipping first/last labels at chart edges
 */
data class XAxisConfig(
    val axisConfig: AxisConfig = AxisConfig(),
    val position: XAxisPosition = XAxisPosition.TOP,
    val labelRotationAngle: Float = 0f,
    val avoidFirstLastClipping: Boolean = false
) {
    // Delegate common properties for convenience
    val enabled get() = axisConfig.enabled
    val drawLabels get() = axisConfig.drawLabels
    val drawAxisLine get() = axisConfig.drawAxisLine
    val drawGridLines get() = axisConfig.drawGridLines
    val textColor get() = axisConfig.textColor
    val textSize get() = axisConfig.textSize
    val limitLines get() = axisConfig.limitLines

    fun withLimitLine(limitLine: LimitLineConfig): XAxisConfig =
        copy(axisConfig = axisConfig.copy(limitLines = axisConfig.limitLines + limitLine))

    fun withFormatter(formatter: AxisValueFormatter): XAxisConfig =
        copy(axisConfig = axisConfig.copy(valueFormatter = formatter))
}

/**
 * Configuration for the Y-axis.
 *
 * @property axisConfig Base axis configuration
 * @property labelPosition Position of labels (inside or outside chart)
 * @property inverted Whether the axis is inverted (high at bottom)
 * @property drawZeroLine Whether to draw a line at zero
 * @property zeroLineColor Color of the zero line
 * @property zeroLineWidth Width of the zero line
 * @property spaceTop Extra space at top in percent of full range
 * @property spaceBottom Extra space at bottom in percent of full range
 * @property drawTopLabel Whether to draw the top label
 * @property drawBottomLabel Whether to draw the bottom label
 * @property minWidth Minimum width the axis should take
 * @property maxWidth Maximum width the axis can take
 */
data class YAxisConfig(
    val axisConfig: AxisConfig = AxisConfig(),
    val labelPosition: YAxisLabelPosition = YAxisLabelPosition.OUTSIDE_CHART,
    val inverted: Boolean = false,
    val drawZeroLine: Boolean = false,
    val zeroLineColor: Color = Color.Gray,
    val zeroLineWidth: Dp = 1.dp,
    val spaceTop: Float = 10f,
    val spaceBottom: Float = 10f,
    val drawTopLabel: Boolean = true,
    val drawBottomLabel: Boolean = true,
    val minWidth: Dp = 0.dp,
    val maxWidth: Dp = Dp.Infinity
) {
    // Delegate common properties for convenience
    val enabled get() = axisConfig.enabled
    val drawLabels get() = axisConfig.drawLabels
    val drawAxisLine get() = axisConfig.drawAxisLine
    val drawGridLines get() = axisConfig.drawGridLines
    val textColor get() = axisConfig.textColor
    val textSize get() = axisConfig.textSize
    val limitLines get() = axisConfig.limitLines

    fun withLimitLine(limitLine: LimitLineConfig): YAxisConfig =
        copy(axisConfig = axisConfig.copy(limitLines = axisConfig.limitLines + limitLine))

    fun withFormatter(formatter: AxisValueFormatter): YAxisConfig =
        copy(axisConfig = axisConfig.copy(valueFormatter = formatter))

    fun withRange(min: Float, max: Float): YAxisConfig =
        copy(axisConfig = axisConfig.copy(axisMinimum = min, axisMaximum = max))
}
