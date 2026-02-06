package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.BubbleDataSet

/**
 * Renders bubble chart data onto a Compose DrawScope.
 */
object BubbleChartRenderer {

    /**
     * Draws a bubble dataset within the given viewport.
     *
     * @param dataSet The bubble dataset to render
     * @param viewport The chart viewport for coordinate mapping
     * @param maxBubbleSize Maximum bubble size in the dataset (for normalization)
     * @param maxBubbleRadiusPx Maximum bubble radius in pixels
     * @param animationPhase Animation progress (0-1)
     */
    fun DrawScope.drawBubbleDataSet(
        dataSet: BubbleDataSet,
        viewport: ChartViewport,
        maxBubbleSize: Float = dataSet.maxSize,
        maxBubbleRadiusPx: Float = viewport.contentWidth * 0.1f,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return
        if (maxBubbleSize == 0f) return

        for ((i, entry) in dataSet.entries.withIndex()) {
            val pixel = viewport.dataToPixel(entry.x, entry.y * animationPhase)

            if (!viewport.isInBoundsX(pixel.x)) continue

            val normalizedSize = if (dataSet.normalizeSizeEnabled) {
                entry.size / maxBubbleSize
            } else {
                entry.size
            }
            val radiusPx = normalizedSize * maxBubbleRadiusPx * animationPhase

            val color = dataSet.getColor(i)

            drawCircle(
                color = color,
                radius = radiusPx,
                center = pixel,
                alpha = 0.75f
            )
        }
    }

    /**
     * Draws highlight ring around a bubble.
     */
    fun DrawScope.drawBubbleHighlight(
        dataSet: BubbleDataSet,
        highlightIndex: Int,
        viewport: ChartViewport,
        maxBubbleSize: Float = dataSet.maxSize,
        maxBubbleRadiusPx: Float = viewport.contentWidth * 0.1f,
        highlightColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color(0xFFFFBB73),
        animationPhase: Float = 1f
    ) {
        if (highlightIndex < 0 || highlightIndex >= dataSet.entries.size) return

        val entry = dataSet.entries[highlightIndex]
        val pixel = viewport.dataToPixel(entry.x, entry.y * animationPhase)

        val normalizedSize = if (dataSet.normalizeSizeEnabled && maxBubbleSize > 0f) {
            entry.size / maxBubbleSize
        } else {
            entry.size
        }
        val radiusPx = normalizedSize * maxBubbleRadiusPx * animationPhase

        drawCircle(
            color = highlightColor,
            radius = radiusPx + dataSet.highlightCircleWidth,
            center = pixel,
            style = Stroke(width = dataSet.highlightCircleWidth)
        )
    }
}
