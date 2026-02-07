package com.github.mikephil.charting.compose.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.compose.components.DescriptionConfig
import com.github.mikephil.charting.compose.components.LegendConfig
import com.github.mikephil.charting.compose.data.PieData
import com.github.mikephil.charting.compose.renderer.LegendRenderer
import com.github.mikephil.charting.compose.renderer.PieChartRenderer
import kotlin.math.min

/**
 * A Compose-native Pie Chart.
 *
 * @param data The pie chart data
 * @param modifier Modifier for the chart layout
 * @param legend Configuration for the legend
 * @param description Configuration for the chart description
 * @param backgroundColor Background color of the chart
 * @param holeEnabled Whether to draw a center hole (donut chart)
 * @param holeRadiusPercent Hole size as percentage of radius (0-100)
 * @param holeColor Color of the center hole
 * @param transparentCircleEnabled Whether to draw a transparent circle around the hole
 * @param transparentCircleRadiusPercent Transparent circle radius as percentage of radius
 * @param transparentCircleAlpha Alpha for the transparent circle (0-255)
 * @param centerText Text to display in the center
 * @param centerTextColor Color of the center text
 * @param centerTextSize Size of the center text
 * @param drawEntryLabels Whether to draw labels on slices
 * @param entryLabelColor Color of entry labels
 * @param entryLabelTextSize Size of entry labels
 * @param rotationAngle Starting rotation angle in degrees (270 = top)
 * @param maxAngle Maximum arc angle (360 = full pie, 180 = half pie)
 * @param drawRoundedSlices Whether to round slice ends
 * @param minOffset Minimum offset around the pie
 * @param highlightedIndex Index of highlighted slice (-1 = none)
 * @param highlightShift Distance to shift highlighted slice outward
 * @param animationProgress Animation progress from 0f to 1f
 */
@Composable
fun PieChart(
    data: PieData,
    modifier: Modifier = Modifier,
    legend: LegendConfig = LegendConfig(),
    description: DescriptionConfig = DescriptionConfig(enabled = false),
    backgroundColor: Color = Color.White,
    holeEnabled: Boolean = true,
    holeRadiusPercent: Float = 50f,
    holeColor: Color = Color.White,
    transparentCircleEnabled: Boolean = true,
    transparentCircleRadiusPercent: Float = 55f,
    transparentCircleAlpha: Int = 100,
    centerText: String = "",
    centerTextColor: Color = Color.Black,
    centerTextSize: TextUnit = 14.sp,
    drawEntryLabels: Boolean = true,
    entryLabelColor: Color = Color.White,
    entryLabelTextSize: TextUnit = 12.sp,
    rotationAngle: Float = 270f,
    maxAngle: Float = 360f,
    drawRoundedSlices: Boolean = false,
    minOffset: Dp = 15.dp,
    highlightedIndex: Int = -1,
    highlightShift: Float = 18f,
    animationProgress: Float = 1f
) {
    val dataSet = data.dataSet ?: return
    if (data.isEmpty) return

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.fillMaxSize()) {
        val offsetPx = minOffset.toPx()

        drawRect(color = backgroundColor)

        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val center = Offset(centerX, centerY)
        val radius = min(size.width - offsetPx * 2, size.height - offsetPx * 2) / 2f
        val holeRadius = if (holeEnabled) radius * holeRadiusPercent / 100f else 0f

        // Draw pie slices
        with(PieChartRenderer) {
            drawPieDataSet(
                dataSet = dataSet,
                center = center,
                radius = radius,
                rotationAngle = rotationAngle,
                holeRadius = holeRadius,
                animationPhase = animationProgress
            )

            // Draw highlight
            if (highlightedIndex >= 0 && highlightedIndex < dataSet.entryCount) {
                drawPieHighlight(
                    dataSet = dataSet,
                    highlightIndex = highlightedIndex,
                    center = center,
                    radius = radius,
                    rotationAngle = rotationAngle,
                    shiftDistance = highlightShift,
                    animationPhase = animationProgress
                )
            }
        }

        // Draw hole
        if (holeEnabled && holeRadius > 0) {
            drawCircle(color = holeColor, radius = holeRadius, center = center)

            // Transparent circle
            if (transparentCircleEnabled) {
                val tcRadius = radius * transparentCircleRadiusPercent / 100f
                if (tcRadius > holeRadius) {
                    drawCircle(
                        color = holeColor.copy(alpha = transparentCircleAlpha / 255f),
                        radius = tcRadius,
                        center = center
                    )
                }
            }
        }

        // Center text
        if (centerText.isNotEmpty() && holeEnabled) {
            val ctStyle = TextStyle(
                color = centerTextColor,
                fontSize = centerTextSize,
                textAlign = TextAlign.Center
            )
            val measured = textMeasurer.measure(centerText, ctStyle)
            drawText(
                textLayoutResult = measured,
                topLeft = Offset(
                    centerX - measured.size.width / 2f,
                    centerY - measured.size.height / 2f
                )
            )
        }

        // Legend
        val legendEntries = if (legend.isCustom) legend.entries + legend.extraEntries
            else LegendRenderer.buildEntries(data.dataSets) + legend.extraEntries
        with(LegendRenderer) {
            drawLegend(
                config = legend,
                entries = legendEntries,
                textMeasurer = textMeasurer,
                chartArea = Rect(0f, 0f, size.width, size.height)
            )
        }

        // Description
        if (description.enabled && description.text.isNotEmpty()) {
            val descStyle = TextStyle(color = description.textColor, fontSize = description.textSize, fontFamily = description.fontFamily)
            val measured = textMeasurer.measure(description.text, descStyle)
            val pos = description.position ?: Offset(size.width - measured.size.width - 8f, size.height - measured.size.height - 4f)
            drawText(textLayoutResult = measured, topLeft = pos)
        }
    }
}
