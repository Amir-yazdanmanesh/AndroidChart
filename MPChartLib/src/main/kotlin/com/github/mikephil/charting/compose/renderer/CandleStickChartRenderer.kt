package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.CandleDataSet
import com.github.mikephil.charting.compose.data.CandlePaintStyle

/**
 * Renders candlestick chart data onto a Compose DrawScope.
 */
object CandleStickChartRenderer {

    /**
     * Draws a candlestick dataset within the given viewport.
     */
    fun DrawScope.drawCandleDataSet(
        dataSet: CandleDataSet,
        viewport: ChartViewport,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return

        val shadowWidthPx = dataSet.shadowWidth.toPx()
        val barSpacePx = dataSet.barSpace * viewport.scaleX

        for ((i, entry) in dataSet.entries.withIndex()) {
            val xPixel = viewport.dataToPixelX(entry.x)
            val highPixel = viewport.dataToPixelY(entry.high * animationPhase)
            val lowPixel = viewport.dataToPixelY(entry.low * animationPhase)
            val openPixel = viewport.dataToPixelY(entry.open * animationPhase)
            val closePixel = viewport.dataToPixelY(entry.close * animationPhase)

            if (!viewport.isInBoundsX(xPixel)) continue

            val bodyColor: Color
            val paintStyle: CandlePaintStyle

            when {
                entry.open > entry.close -> {
                    bodyColor = dataSet.decreasingColor
                    paintStyle = dataSet.decreasingPaintStyle
                }
                entry.open < entry.close -> {
                    bodyColor = dataSet.increasingColor
                    paintStyle = dataSet.increasingPaintStyle
                }
                else -> {
                    bodyColor = dataSet.neutralColor
                    paintStyle = CandlePaintStyle.FILL
                }
            }

            val shadowColor = if (dataSet.shadowColorSameAsCandle) bodyColor
            else dataSet.shadowColor ?: bodyColor

            // Draw shadow (wick)
            drawLine(
                color = shadowColor,
                start = Offset(xPixel, highPixel),
                end = Offset(xPixel, lowPixel),
                strokeWidth = shadowWidthPx
            )

            // Draw body
            if (dataSet.showCandleBar) {
                val halfBarWidth = (viewport.scaleX * (1f - dataSet.barSpace)) / 2f
                val bodyTop = minOf(openPixel, closePixel)
                val bodyBottom = maxOf(openPixel, closePixel)
                val bodyHeight = (bodyBottom - bodyTop).coerceAtLeast(1f)

                val style = when (paintStyle) {
                    CandlePaintStyle.FILL -> Fill
                    CandlePaintStyle.STROKE -> Stroke(width = shadowWidthPx)
                    CandlePaintStyle.FILL_AND_STROKE -> Fill
                }

                drawRect(
                    color = bodyColor,
                    topLeft = Offset(xPixel - halfBarWidth, bodyTop),
                    size = Size(halfBarWidth * 2, bodyHeight),
                    style = style
                )

                // Extra stroke for FILL_AND_STROKE
                if (paintStyle == CandlePaintStyle.FILL_AND_STROKE) {
                    drawRect(
                        color = bodyColor,
                        topLeft = Offset(xPixel - halfBarWidth, bodyTop),
                        size = Size(halfBarWidth * 2, bodyHeight),
                        style = Stroke(width = shadowWidthPx)
                    )
                }
            }
        }
    }
}
