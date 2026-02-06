package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.github.mikephil.charting.compose.components.AxisConfig
import com.github.mikephil.charting.compose.components.LimitLineConfig
import com.github.mikephil.charting.compose.components.XAxisConfig
import com.github.mikephil.charting.compose.components.XAxisPosition
import com.github.mikephil.charting.compose.components.YAxisConfig

/**
 * Renders chart axes onto a Compose DrawScope.
 */
object AxisRenderer {

    /**
     * Draws the X-axis with labels, grid lines, and axis line.
     */
    fun DrawScope.drawXAxis(
        config: XAxisConfig,
        viewport: ChartViewport,
        textMeasurer: TextMeasurer,
        labelValues: List<Float> = computeAxisValues(viewport.dataXMin, viewport.dataXMax, config.axisConfig.labelCount)
    ) {
        if (!config.enabled) return

        val axis = config.axisConfig

        // Grid lines
        if (axis.drawGridLines) {
            for (value in labelValues) {
                val x = viewport.dataToPixelX(value)
                if (!viewport.isInBoundsX(x)) continue

                drawLine(
                    color = axis.gridColor,
                    start = Offset(x, viewport.contentRect.top),
                    end = Offset(x, viewport.contentRect.bottom),
                    strokeWidth = axis.gridLineWidth.toPx(),
                    pathEffect = axis.gridPathEffect
                )
            }
        }

        // Axis line
        if (axis.drawAxisLine) {
            val y = when (config.position) {
                XAxisPosition.TOP, XAxisPosition.TOP_INSIDE -> viewport.contentRect.top
                else -> viewport.contentRect.bottom
            }
            drawLine(
                color = axis.axisLineColor,
                start = Offset(viewport.contentRect.left, y),
                end = Offset(viewport.contentRect.right, y),
                strokeWidth = axis.axisLineWidth.toPx(),
                pathEffect = axis.axisLinePathEffect
            )
        }

        // Labels
        if (axis.drawLabels) {
            val formatter = axis.valueFormatter
            val textStyle = TextStyle(
                color = axis.textColor,
                fontSize = axis.textSize,
                fontFamily = axis.fontFamily
            )

            for (value in labelValues) {
                val x = viewport.dataToPixelX(value)
                if (!viewport.isInBoundsX(x)) continue

                val label = formatter?.format(value) ?: formatDefault(value)
                val measuredText = textMeasurer.measure(label, textStyle)

                val labelY = when (config.position) {
                    XAxisPosition.TOP, XAxisPosition.TOP_INSIDE ->
                        viewport.contentRect.top - measuredText.size.height - 4f
                    else ->
                        viewport.contentRect.bottom + 4f
                }

                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(x - measuredText.size.width / 2f, labelY)
                )
            }
        }

        // Limit lines
        for (limitLine in axis.limitLines) {
            if (!limitLine.enabled) continue
            drawVerticalLimitLine(limitLine, viewport, textMeasurer)
        }
    }

    /**
     * Draws the Y-axis with labels, grid lines, and axis line.
     */
    fun DrawScope.drawYAxis(
        config: YAxisConfig,
        viewport: ChartViewport,
        textMeasurer: TextMeasurer,
        isLeft: Boolean = true,
        labelValues: List<Float> = computeAxisValues(viewport.dataYMin, viewport.dataYMax, config.axisConfig.labelCount)
    ) {
        if (!config.enabled) return

        val axis = config.axisConfig

        // Grid lines
        if (axis.drawGridLines) {
            for (value in labelValues) {
                val y = viewport.dataToPixelY(value)
                if (!viewport.isInBoundsY(y)) continue

                drawLine(
                    color = axis.gridColor,
                    start = Offset(viewport.contentRect.left, y),
                    end = Offset(viewport.contentRect.right, y),
                    strokeWidth = axis.gridLineWidth.toPx(),
                    pathEffect = axis.gridPathEffect
                )
            }
        }

        // Zero line
        if (config.drawZeroLine) {
            val zeroY = viewport.dataToPixelY(0f)
            if (viewport.isInBoundsY(zeroY)) {
                drawLine(
                    color = config.zeroLineColor,
                    start = Offset(viewport.contentRect.left, zeroY),
                    end = Offset(viewport.contentRect.right, zeroY),
                    strokeWidth = config.zeroLineWidth.toPx()
                )
            }
        }

        // Axis line
        if (axis.drawAxisLine) {
            val x = if (isLeft) viewport.contentRect.left else viewport.contentRect.right
            drawLine(
                color = axis.axisLineColor,
                start = Offset(x, viewport.contentRect.top),
                end = Offset(x, viewport.contentRect.bottom),
                strokeWidth = axis.axisLineWidth.toPx(),
                pathEffect = axis.axisLinePathEffect
            )
        }

        // Labels
        if (axis.drawLabels) {
            val formatter = axis.valueFormatter
            val textStyle = TextStyle(
                color = axis.textColor,
                fontSize = axis.textSize,
                fontFamily = axis.fontFamily
            )

            for (value in labelValues) {
                val y = viewport.dataToPixelY(value)
                if (!viewport.isInBoundsY(y)) continue

                val label = formatter?.format(value) ?: formatDefault(value)
                val measuredText = textMeasurer.measure(label, textStyle)

                val labelX = if (isLeft) {
                    viewport.contentRect.left - measuredText.size.width - 8f
                } else {
                    viewport.contentRect.right + 8f
                }

                drawText(
                    textLayoutResult = measuredText,
                    topLeft = Offset(labelX, y - measuredText.size.height / 2f)
                )
            }
        }

        // Limit lines
        for (limitLine in axis.limitLines) {
            if (!limitLine.enabled) continue
            drawHorizontalLimitLine(limitLine, viewport, textMeasurer)
        }
    }

    private fun DrawScope.drawHorizontalLimitLine(
        config: LimitLineConfig,
        viewport: ChartViewport,
        textMeasurer: TextMeasurer
    ) {
        val y = viewport.dataToPixelY(config.value)
        if (!viewport.isInBoundsY(y)) return

        drawLine(
            color = config.lineColor,
            start = Offset(viewport.contentRect.left, y),
            end = Offset(viewport.contentRect.right, y),
            strokeWidth = config.lineWidth.toPx(),
            pathEffect = config.pathEffect
        )

        if (config.label.isNotEmpty()) {
            val textStyle = TextStyle(
                color = config.labelColor,
                fontSize = config.labelTextSize,
                fontFamily = config.labelFontFamily
            )
            val measuredText = textMeasurer.measure(config.label, textStyle)

            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(viewport.contentRect.right - measuredText.size.width - 4f, y - measuredText.size.height - 2f)
            )
        }
    }

    private fun DrawScope.drawVerticalLimitLine(
        config: LimitLineConfig,
        viewport: ChartViewport,
        textMeasurer: TextMeasurer
    ) {
        val x = viewport.dataToPixelX(config.value)
        if (!viewport.isInBoundsX(x)) return

        drawLine(
            color = config.lineColor,
            start = Offset(x, viewport.contentRect.top),
            end = Offset(x, viewport.contentRect.bottom),
            strokeWidth = config.lineWidth.toPx(),
            pathEffect = config.pathEffect
        )

        if (config.label.isNotEmpty()) {
            val textStyle = TextStyle(
                color = config.labelColor,
                fontSize = config.labelTextSize,
                fontFamily = config.labelFontFamily
            )
            val measuredText = textMeasurer.measure(config.label, textStyle)

            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(x + 4f, viewport.contentRect.top + 2f)
            )
        }
    }

    /**
     * Computes evenly distributed axis label values.
     */
    fun computeAxisValues(min: Float, max: Float, count: Int): List<Float> {
        if (count < 2 || min >= max) return listOf(min, max)

        val range = max - min
        val interval = range / (count - 1)

        return (0 until count).map { i ->
            min + i * interval
        }
    }

    private fun formatDefault(value: Float): String {
        return if (value == value.toLong().toFloat()) {
            value.toLong().toString()
        } else {
            String.format("%.1f", value)
        }
    }
}
