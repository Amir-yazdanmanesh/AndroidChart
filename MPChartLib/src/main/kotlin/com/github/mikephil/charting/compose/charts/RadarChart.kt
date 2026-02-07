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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.compose.components.DescriptionConfig
import com.github.mikephil.charting.compose.components.LegendConfig
import com.github.mikephil.charting.compose.components.YAxisConfig
import com.github.mikephil.charting.compose.data.RadarData
import com.github.mikephil.charting.compose.renderer.LegendRenderer
import com.github.mikephil.charting.compose.renderer.RadarChartRenderer
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val DEG2RAD = Math.PI.toFloat() / 180f

/**
 * A Compose-native Radar Chart (spider/web chart).
 *
 * @param data The radar chart data containing one or more radar datasets
 * @param modifier Modifier for the chart layout
 * @param yAxis Configuration for the Y-axis (used for range/labels)
 * @param legend Configuration for the legend
 * @param description Configuration for the chart description
 * @param backgroundColor Background color of the chart
 * @param webEnabled Whether to draw the web/grid
 * @param webColor Color of the outer web lines
 * @param webColorInner Color of the inner web lines
 * @param webLineWidth Width of the outer web lines
 * @param webLineWidthInner Width of the inner web lines
 * @param webAlpha Transparency of the web (0-255)
 * @param webCount Number of concentric web rings
 * @param skipWebLineCount Skip every Nth web line
 * @param rotationAngle Starting rotation angle (270 = top)
 * @param drawLabels Whether to draw axis labels around the chart
 * @param labelColor Color of the axis labels
 * @param labelTextSize Size of the axis labels
 * @param minOffset Minimum offset around the radar
 * @param animationProgress Animation progress from 0f to 1f
 */
@Composable
fun RadarChart(
    data: RadarData,
    modifier: Modifier = Modifier,
    yAxis: YAxisConfig = YAxisConfig(axisConfig = com.github.mikephil.charting.compose.components.AxisConfig(enabled = false)),
    legend: LegendConfig = LegendConfig(),
    description: DescriptionConfig = DescriptionConfig(enabled = false),
    backgroundColor: Color = Color.White,
    webEnabled: Boolean = true,
    webColor: Color = Color(0xFF7A7A7A),
    webColorInner: Color = Color(0xFF7A7A7A),
    webLineWidth: Float = 1.5f,
    webLineWidthInner: Float = 0.75f,
    webAlpha: Int = 150,
    webCount: Int = 5,
    skipWebLineCount: Int = 0,
    rotationAngle: Float = 270f,
    drawLabels: Boolean = true,
    labelColor: Color = Color.Black,
    labelTextSize: TextUnit = 10.sp,
    minOffset: Dp = 15.dp,
    animationProgress: Float = 1f
) {
    if (data.isEmpty || data.axisCount == 0) return

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.fillMaxSize()) {
        val offsetPx = minOffset.toPx()

        drawRect(color = backgroundColor)

        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val center = Offset(centerX, centerY)
        val radius = min(size.width - offsetPx * 2, size.height - offsetPx * 2) / 2f - 20f
        val maxValue = data.yMax

        // Draw web
        if (webEnabled) {
            with(RadarChartRenderer) {
                drawRadarWeb(
                    center = center,
                    radius = radius,
                    axisCount = data.axisCount,
                    webCount = webCount,
                    webColor = webColor.copy(alpha = webAlpha / 255f),
                    webLineWidth = webLineWidth,
                    rotationAngle = rotationAngle
                )
            }
        }

        // Draw datasets
        with(RadarChartRenderer) {
            for (dataSet in data.dataSets) {
                drawRadarDataSet(
                    dataSet = dataSet,
                    center = center,
                    radius = radius,
                    maxValue = maxValue,
                    rotationAngle = rotationAngle,
                    animationPhase = animationProgress
                )
            }
        }

        // Draw axis labels
        if (drawLabels && data.labels.isNotEmpty()) {
            val labelStyle = TextStyle(
                color = labelColor,
                fontSize = labelTextSize
            )
            val sliceAngle = 360f / data.axisCount

            for (i in data.labels.indices) {
                if (i >= data.axisCount) break
                val angle = (rotationAngle + i * sliceAngle) * DEG2RAD
                val labelRadius = radius + 16f
                val x = centerX + labelRadius * cos(angle)
                val y = centerY + labelRadius * sin(angle)

                val measured = textMeasurer.measure(data.labels[i], labelStyle)
                drawText(
                    textLayoutResult = measured,
                    topLeft = Offset(x - measured.size.width / 2f, y - measured.size.height / 2f)
                )
            }
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
