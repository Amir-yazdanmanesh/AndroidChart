package com.github.mikephil.charting.compose.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.compose.components.DescriptionConfig
import com.github.mikephil.charting.compose.components.LegendConfig
import com.github.mikephil.charting.compose.components.XAxisConfig
import com.github.mikephil.charting.compose.components.XAxisPosition
import com.github.mikephil.charting.compose.components.YAxisConfig
import com.github.mikephil.charting.compose.data.BarData
import com.github.mikephil.charting.compose.renderer.AxisRenderer
import com.github.mikephil.charting.compose.renderer.BarChartRenderer
import com.github.mikephil.charting.compose.renderer.ChartViewport
import com.github.mikephil.charting.compose.renderer.LegendRenderer

/**
 * A Compose-native Bar Chart.
 *
 * @param data The bar chart data containing one or more bar datasets
 * @param modifier Modifier for the chart layout
 * @param xAxis Configuration for the X-axis
 * @param leftAxis Configuration for the left Y-axis
 * @param rightAxis Configuration for the right Y-axis (null = disabled)
 * @param legend Configuration for the legend
 * @param description Configuration for the chart description
 * @param backgroundColor Background color of the chart
 * @param drawGridBackground Whether to draw the grid background
 * @param gridBackgroundColor Color of the grid background
 * @param drawBorders Whether to draw borders around the content area
 * @param borderColor Color of the border
 * @param borderWidth Width of the border
 * @param drawValueAboveBar Whether to draw values above bars
 * @param drawBarShadow Whether to draw shadows behind bars
 * @param fitBars Add half bar width padding to X-axis edges
 * @param minOffset Minimum offset/padding around the content area
 * @param animationProgress Animation progress from 0f to 1f
 */
@Composable
fun BarChart(
    data: BarData,
    modifier: Modifier = Modifier,
    xAxis: XAxisConfig = XAxisConfig(),
    leftAxis: YAxisConfig = YAxisConfig(),
    rightAxis: YAxisConfig? = null,
    legend: LegendConfig = LegendConfig(),
    description: DescriptionConfig = DescriptionConfig(enabled = false),
    backgroundColor: Color = Color.White,
    drawGridBackground: Boolean = false,
    gridBackgroundColor: Color = Color(0xFFF0F0F0),
    drawBorders: Boolean = false,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    drawValueAboveBar: Boolean = true,
    drawBarShadow: Boolean = false,
    fitBars: Boolean = false,
    minOffset: Dp = 15.dp,
    animationProgress: Float = 1f
) {
    if (data.isEmpty) return

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.fillMaxSize()) {
        val offsetPx = minOffset.toPx()

        val leftInset = if (leftAxis.enabled && leftAxis.drawLabels) offsetPx + 30f else offsetPx
        val rightInset = if (rightAxis?.enabled == true && rightAxis.drawLabels) offsetPx + 30f else offsetPx
        val topInset = if (xAxis.position == XAxisPosition.TOP || xAxis.position == XAxisPosition.BOTH_SIDED) offsetPx + 20f else offsetPx
        val bottomInset = if (xAxis.position == XAxisPosition.BOTTOM || xAxis.position == XAxisPosition.BOTH_SIDED) offsetPx + 20f else offsetPx

        drawRect(color = backgroundColor)

        // Adjust X range for fitBars
        val halfBarWidth = if (fitBars) data.barWidth / 2f else 0f
        val viewport = ChartViewport.create(
            canvasSize = size,
            leftInset = leftInset,
            topInset = topInset,
            rightInset = rightInset,
            bottomInset = bottomInset,
            dataXMin = data.xMin - halfBarWidth,
            dataXMax = data.xMax + halfBarWidth,
            dataYMin = data.yMin.coerceAtMost(0f),
            dataYMax = data.yMax
        )

        if (drawGridBackground) {
            drawRect(
                color = gridBackgroundColor,
                topLeft = viewport.contentRect.topLeft,
                size = viewport.contentRect.size
            )
        }

        with(AxisRenderer) {
            drawYAxis(config = leftAxis, viewport = viewport, textMeasurer = textMeasurer, isLeft = true)
            if (rightAxis != null) {
                drawYAxis(config = rightAxis, viewport = viewport, textMeasurer = textMeasurer, isLeft = false)
            }
            drawXAxis(config = xAxis, viewport = viewport, textMeasurer = textMeasurer)
        }

        clipRect(
            left = viewport.contentRect.left,
            top = viewport.contentRect.top,
            right = viewport.contentRect.right,
            bottom = viewport.contentRect.bottom
        ) {
            val groupCount = data.dataSetCount
            with(BarChartRenderer) {
                for ((index, dataSet) in data.dataSets.withIndex()) {
                    drawBarDataSet(
                        dataSet = dataSet,
                        viewport = viewport,
                        barWidth = data.barWidth,
                        groupIndex = index,
                        groupCount = groupCount,
                        animationPhase = animationProgress
                    )
                }
            }
        }

        if (drawBorders) {
            drawRect(
                color = borderColor,
                topLeft = viewport.contentRect.topLeft,
                size = viewport.contentRect.size,
                style = Stroke(width = borderWidth.toPx())
            )
        }

        val legendEntries = if (legend.isCustom) legend.entries + legend.extraEntries
            else LegendRenderer.buildEntries(data.dataSets) + legend.extraEntries
        with(LegendRenderer) {
            drawLegend(config = legend, entries = legendEntries, textMeasurer = textMeasurer, chartArea = Rect(0f, 0f, size.width, size.height))
        }

        if (description.enabled && description.text.isNotEmpty()) {
            val descStyle = TextStyle(color = description.textColor, fontSize = description.textSize, fontFamily = description.fontFamily)
            val measured = textMeasurer.measure(description.text, descStyle)
            val pos = description.position ?: Offset(size.width - measured.size.width - 8f, size.height - measured.size.height - 4f)
            drawText(textLayoutResult = measured, topLeft = pos)
        }
    }
}

/**
 * A Compose-native Horizontal Bar Chart.
 *
 * Renders bars horizontally. The X-axis represents values and Y-axis represents categories.
 *
 * @param data The bar chart data
 * @param modifier Modifier for the chart layout
 * @param xAxis Configuration for the X-axis (displayed as value axis on bottom)
 * @param leftAxis Configuration for the left Y-axis (displayed as category axis)
 * @param rightAxis Configuration for the right Y-axis (null = disabled)
 * @param legend Configuration for the legend
 * @param description Configuration for the chart description
 * @param backgroundColor Background color of the chart
 * @param drawGridBackground Whether to draw the grid background
 * @param gridBackgroundColor Color of the grid background
 * @param drawBorders Whether to draw borders around the content area
 * @param borderColor Color of the border
 * @param borderWidth Width of the border
 * @param minOffset Minimum offset/padding around the content area
 * @param animationProgress Animation progress from 0f to 1f
 */
@Composable
fun HorizontalBarChart(
    data: BarData,
    modifier: Modifier = Modifier,
    xAxis: XAxisConfig = XAxisConfig(),
    leftAxis: YAxisConfig = YAxisConfig(),
    rightAxis: YAxisConfig? = null,
    legend: LegendConfig = LegendConfig(),
    description: DescriptionConfig = DescriptionConfig(enabled = false),
    backgroundColor: Color = Color.White,
    drawGridBackground: Boolean = false,
    gridBackgroundColor: Color = Color(0xFFF0F0F0),
    drawBorders: Boolean = false,
    borderColor: Color = Color.Black,
    borderWidth: Dp = 1.dp,
    minOffset: Dp = 15.dp,
    animationProgress: Float = 1f
) {
    if (data.isEmpty) return

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier.fillMaxSize()) {
        val offsetPx = minOffset.toPx()

        val leftInset = if (leftAxis.enabled && leftAxis.drawLabels) offsetPx + 30f else offsetPx
        val rightInset = if (rightAxis?.enabled == true && rightAxis.drawLabels) offsetPx + 30f else offsetPx
        val topInset = offsetPx
        val bottomInset = if (xAxis.enabled) offsetPx + 20f else offsetPx

        drawRect(color = backgroundColor)

        // For horizontal bar chart, swap axes: Y data maps to X screen, X data maps to Y screen
        val viewport = ChartViewport.create(
            canvasSize = size,
            leftInset = leftInset,
            topInset = topInset,
            rightInset = rightInset,
            bottomInset = bottomInset,
            dataXMin = data.yMin.coerceAtMost(0f),
            dataXMax = data.yMax,
            dataYMin = data.xMin - data.barWidth / 2f,
            dataYMax = data.xMax + data.barWidth / 2f
        )

        if (drawGridBackground) {
            drawRect(
                color = gridBackgroundColor,
                topLeft = viewport.contentRect.topLeft,
                size = viewport.contentRect.size
            )
        }

        with(AxisRenderer) {
            drawXAxis(config = xAxis, viewport = viewport, textMeasurer = textMeasurer)
            drawYAxis(config = leftAxis, viewport = viewport, textMeasurer = textMeasurer, isLeft = true)
            if (rightAxis != null) {
                drawYAxis(config = rightAxis, viewport = viewport, textMeasurer = textMeasurer, isLeft = false)
            }
        }

        clipRect(
            left = viewport.contentRect.left,
            top = viewport.contentRect.top,
            right = viewport.contentRect.right,
            bottom = viewport.contentRect.bottom
        ) {
            // Draw horizontal bars by rendering bar rectangles with swapped coordinates
            val barWidthPixels = viewport.contentHeight / ((data.xMax - data.xMin).let { if (it == 0f) 1f else it }) * data.barWidth
            val groupCount = data.dataSetCount

            for ((dsIndex, dataSet) in data.dataSets.withIndex()) {
                if (!dataSet.visible) continue
                for ((entryIndex, entry) in dataSet.entries.withIndex()) {
                    val color = dataSet.getColor(entryIndex)
                    val barOffset = if (groupCount > 1) {
                        (dsIndex - groupCount / 2f + 0.5f) * (barWidthPixels / groupCount)
                    } else 0f

                    val centerY = viewport.dataToPixelY(entry.x) + barOffset
                    val left = viewport.dataToPixelX(0f.coerceAtMost(entry.y * animationProgress))
                    val right = viewport.dataToPixelX(0f.coerceAtLeast(entry.y * animationProgress))

                    drawRect(
                        color = color,
                        topLeft = Offset(left, centerY - barWidthPixels / (2f * groupCount)),
                        size = androidx.compose.ui.geometry.Size(right - left, barWidthPixels / groupCount)
                    )
                }
            }
        }

        if (drawBorders) {
            drawRect(
                color = borderColor,
                topLeft = viewport.contentRect.topLeft,
                size = viewport.contentRect.size,
                style = Stroke(width = borderWidth.toPx())
            )
        }

        val legendEntries = if (legend.isCustom) legend.entries + legend.extraEntries
            else LegendRenderer.buildEntries(data.dataSets) + legend.extraEntries
        with(LegendRenderer) {
            drawLegend(config = legend, entries = legendEntries, textMeasurer = textMeasurer, chartArea = Rect(0f, 0f, size.width, size.height))
        }

        if (description.enabled && description.text.isNotEmpty()) {
            val descStyle = TextStyle(color = description.textColor, fontSize = description.textSize, fontFamily = description.fontFamily)
            val measured = textMeasurer.measure(description.text, descStyle)
            val pos = description.position ?: Offset(size.width - measured.size.width - 8f, size.height - measured.size.height - 4f)
            drawText(textLayoutResult = measured, topLeft = pos)
        }
    }
}
