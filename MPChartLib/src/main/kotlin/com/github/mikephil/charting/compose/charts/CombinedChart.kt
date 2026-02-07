package com.github.mikephil.charting.compose.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.compose.components.DescriptionConfig
import com.github.mikephil.charting.compose.components.LegendConfig
import com.github.mikephil.charting.compose.components.LegendEntryConfig
import com.github.mikephil.charting.compose.components.XAxisConfig
import com.github.mikephil.charting.compose.components.XAxisPosition
import com.github.mikephil.charting.compose.components.YAxisConfig
import com.github.mikephil.charting.compose.data.CombinedData
import com.github.mikephil.charting.compose.renderer.AxisRenderer
import com.github.mikephil.charting.compose.renderer.BarChartRenderer
import com.github.mikephil.charting.compose.renderer.BubbleChartRenderer
import com.github.mikephil.charting.compose.renderer.CandleStickChartRenderer
import com.github.mikephil.charting.compose.renderer.ChartViewport
import com.github.mikephil.charting.compose.renderer.LegendRenderer
import com.github.mikephil.charting.compose.renderer.LineChartRenderer
import com.github.mikephil.charting.compose.renderer.ScatterChartRenderer

/**
 * Draw order for combined chart layers.
 */
enum class DrawOrder {
    BAR, BUBBLE, LINE, CANDLE, SCATTER
}

/**
 * A Compose-native Combined Chart that can display multiple chart types simultaneously.
 *
 * @param data The combined chart data containing optional line, bar, scatter, candle, and bubble data
 * @param modifier Modifier for the chart layout
 * @param drawOrder Order in which to draw the different chart types
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
 * @param minOffset Minimum offset/padding around the content area
 * @param animationProgress Animation progress from 0f to 1f
 */
@Composable
fun CombinedChart(
    data: CombinedData,
    modifier: Modifier = Modifier,
    drawOrder: List<DrawOrder> = listOf(DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER),
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
        val topInset = if (xAxis.position == XAxisPosition.TOP || xAxis.position == XAxisPosition.BOTH_SIDED) offsetPx + 20f else offsetPx
        val bottomInset = if (xAxis.position == XAxisPosition.BOTTOM || xAxis.position == XAxisPosition.BOTH_SIDED) offsetPx + 20f else offsetPx

        drawRect(color = backgroundColor)

        val viewport = ChartViewport.create(
            canvasSize = size,
            leftInset = leftInset,
            topInset = topInset,
            rightInset = rightInset,
            bottomInset = bottomInset,
            dataXMin = data.xMin,
            dataXMax = data.xMax,
            dataYMin = data.yMin,
            dataYMax = data.yMax
        )

        if (drawGridBackground) {
            drawRect(color = gridBackgroundColor, topLeft = viewport.contentRect.topLeft, size = viewport.contentRect.size)
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
            for (order in drawOrder) {
                when (order) {
                    DrawOrder.BAR -> drawBarLayer(data, viewport, animationProgress)
                    DrawOrder.LINE -> drawLineLayer(data, viewport, animationProgress)
                    DrawOrder.SCATTER -> drawScatterLayer(data, viewport, animationProgress)
                    DrawOrder.CANDLE -> drawCandleLayer(data, viewport, animationProgress)
                    DrawOrder.BUBBLE -> drawBubbleLayer(data, viewport, animationProgress)
                }
            }
        }

        if (drawBorders) {
            drawRect(color = borderColor, topLeft = viewport.contentRect.topLeft, size = viewport.contentRect.size, style = Stroke(width = borderWidth.toPx()))
        }

        // Collect legend entries from all data types
        val legendEntries = if (legend.isCustom) {
            legend.entries + legend.extraEntries
        } else {
            val entries = mutableListOf<LegendEntryConfig>()
            data.lineData?.let { entries.addAll(LegendRenderer.buildEntries(it.dataSets)) }
            data.barData?.let { entries.addAll(LegendRenderer.buildEntries(it.dataSets)) }
            data.scatterData?.let { entries.addAll(LegendRenderer.buildEntries(it.dataSets)) }
            data.candleData?.let { entries.addAll(LegendRenderer.buildEntries(it.dataSets)) }
            data.bubbleData?.let { entries.addAll(LegendRenderer.buildEntries(it.dataSets)) }
            entries + legend.extraEntries
        }
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

private fun DrawScope.drawBarLayer(data: CombinedData, viewport: ChartViewport, phase: Float) {
    val barData = data.barData ?: return
    val groupCount = barData.dataSetCount
    with(BarChartRenderer) {
        for ((index, dataSet) in barData.dataSets.withIndex()) {
            drawBarDataSet(
                dataSet = dataSet,
                viewport = viewport,
                barWidth = barData.barWidth,
                groupIndex = index,
                groupCount = groupCount,
                animationPhase = phase
            )
        }
    }
}

private fun DrawScope.drawLineLayer(data: CombinedData, viewport: ChartViewport, phase: Float) {
    val lineData = data.lineData ?: return
    with(LineChartRenderer) {
        for (dataSet in lineData.dataSets) {
            drawLineDataSet(dataSet = dataSet, viewport = viewport, animationPhase = phase)
        }
    }
}

private fun DrawScope.drawScatterLayer(data: CombinedData, viewport: ChartViewport, phase: Float) {
    val scatterData = data.scatterData ?: return
    with(ScatterChartRenderer) {
        for (dataSet in scatterData.dataSets) {
            drawScatterDataSet(dataSet = dataSet, viewport = viewport, animationPhase = phase)
        }
    }
}

private fun DrawScope.drawCandleLayer(data: CombinedData, viewport: ChartViewport, phase: Float) {
    val candleData = data.candleData ?: return
    with(CandleStickChartRenderer) {
        for (dataSet in candleData.dataSets) {
            drawCandleDataSet(dataSet = dataSet, viewport = viewport, animationPhase = phase)
        }
    }
}

private fun DrawScope.drawBubbleLayer(data: CombinedData, viewport: ChartViewport, phase: Float) {
    val bubbleData = data.bubbleData ?: return
    with(BubbleChartRenderer) {
        for (dataSet in bubbleData.dataSets) {
            drawBubbleDataSet(
                dataSet = dataSet,
                viewport = viewport,
                maxBubbleSize = bubbleData.maxBubbleSize,
                animationPhase = phase
            )
        }
    }
}
