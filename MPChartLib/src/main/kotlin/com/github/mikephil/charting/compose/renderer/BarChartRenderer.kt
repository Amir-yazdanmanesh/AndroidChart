package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.BarDataSet

/**
 * Renders bar chart data onto a Compose DrawScope.
 */
object BarChartRenderer {

    /**
     * Draws a complete bar dataset within the given viewport.
     *
     * @param dataSet The bar dataset to render
     * @param viewport The chart viewport for coordinate mapping
     * @param barWidth Relative width of bars (0-1)
     * @param groupIndex Index of this dataset among grouped datasets
     * @param groupCount Total number of datasets being grouped
     * @param animationPhase Animation progress (0-1)
     */
    fun DrawScope.drawBarDataSet(
        dataSet: BarDataSet,
        viewport: ChartViewport,
        barWidth: Float = 0.85f,
        groupIndex: Int = 0,
        groupCount: Int = 1,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return

        val barWidthPixels = barWidth / groupCount * viewport.scaleX
        val borderWidthPx = dataSet.barBorderWidth.toPx()

        for ((i, entry) in dataSet.entries.withIndex()) {
            if (entry.isStacked) {
                drawStackedBar(dataSet, i, viewport, barWidthPixels, groupIndex, groupCount, borderWidthPx, animationPhase)
            } else {
                drawSingleBar(dataSet, i, viewport, barWidthPixels, groupIndex, groupCount, borderWidthPx, animationPhase)
            }
        }
    }

    private fun DrawScope.drawSingleBar(
        dataSet: BarDataSet,
        index: Int,
        viewport: ChartViewport,
        barWidthPx: Float,
        groupIndex: Int,
        groupCount: Int,
        borderWidthPx: Float,
        phase: Float
    ) {
        val entry = dataSet.entries[index]
        val x = entry.x
        val y = entry.y * phase

        val barGroupWidth = barWidthPx * groupCount
        val barLeft = viewport.dataToPixelX(x) - barGroupWidth / 2f + groupIndex * barWidthPx
        val barRight = barLeft + barWidthPx

        val baseline = viewport.dataToPixelY(0f).coerceIn(viewport.contentRect.top, viewport.contentRect.bottom)
        val barTop = if (y >= 0) viewport.dataToPixelY(y) else baseline
        val barBottom = if (y >= 0) baseline else viewport.dataToPixelY(y)

        val color = dataSet.getColor(index)

        // Fill
        drawRect(
            color = color,
            topLeft = Offset(barLeft, barTop),
            size = Size(barRight - barLeft, barBottom - barTop)
        )

        // Border
        if (borderWidthPx > 0f) {
            drawRect(
                color = dataSet.barBorderColor,
                topLeft = Offset(barLeft, barTop),
                size = Size(barRight - barLeft, barBottom - barTop),
                style = Stroke(width = borderWidthPx)
            )
        }
    }

    private fun DrawScope.drawStackedBar(
        dataSet: BarDataSet,
        index: Int,
        viewport: ChartViewport,
        barWidthPx: Float,
        groupIndex: Int,
        groupCount: Int,
        borderWidthPx: Float,
        phase: Float
    ) {
        val entry = dataSet.entries[index]
        val values = entry.yValues ?: return

        val barGroupWidth = barWidthPx * groupCount
        val barLeft = viewport.dataToPixelX(entry.x) - barGroupWidth / 2f + groupIndex * barWidthPx
        val barRight = barLeft + barWidthPx

        var posY = 0f
        var negY = 0f

        for ((stackIndex, value) in values.withIndex()) {
            val animatedValue = value * phase
            val stackColor = dataSet.stackColors.getOrElse(stackIndex % dataSet.stackColors.size) {
                dataSet.getColor(index)
            }

            val top: Float
            val bottom: Float

            if (animatedValue >= 0f) {
                top = viewport.dataToPixelY(posY + animatedValue)
                bottom = viewport.dataToPixelY(posY)
                posY += animatedValue
            } else {
                top = viewport.dataToPixelY(negY)
                bottom = viewport.dataToPixelY(negY + animatedValue)
                negY += animatedValue
            }

            drawRect(
                color = stackColor,
                topLeft = Offset(barLeft, top),
                size = Size(barRight - barLeft, bottom - top)
            )

            if (borderWidthPx > 0f) {
                drawRect(
                    color = dataSet.barBorderColor,
                    topLeft = Offset(barLeft, top),
                    size = Size(barRight - barLeft, bottom - top),
                    style = Stroke(width = borderWidthPx)
                )
            }
        }
    }
}
