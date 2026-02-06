package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.ScatterDataSet
import com.github.mikephil.charting.compose.data.ScatterShape
import kotlin.math.sqrt

/**
 * Renders scatter chart data onto a Compose DrawScope.
 */
object ScatterChartRenderer {

    /**
     * Draws a scatter dataset within the given viewport.
     */
    fun DrawScope.drawScatterDataSet(
        dataSet: ScatterDataSet,
        viewport: ChartViewport,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return

        val shapeSizePx = dataSet.shapeSize.toPx()
        val holeRadiusPx = dataSet.shapeHoleRadius.toPx()

        for ((i, entry) in dataSet.entries.withIndex()) {
            val pixel = viewport.dataToPixel(entry.x, entry.y * animationPhase)

            if (!viewport.isInBoundsX(pixel.x)) continue

            val color = dataSet.getColor(i)

            drawShape(dataSet.shape, pixel, shapeSizePx, holeRadiusPx, color, dataSet.shapeHoleColor)
        }
    }

    private fun DrawScope.drawShape(
        shape: ScatterShape,
        center: Offset,
        size: Float,
        holeRadius: Float,
        color: androidx.compose.ui.graphics.Color,
        holeColor: androidx.compose.ui.graphics.Color
    ) {
        val half = size / 2f

        when (shape) {
            ScatterShape.CIRCLE -> {
                drawCircle(color = color, radius = half, center = center)
                if (holeRadius > 0f) {
                    drawCircle(color = holeColor, radius = holeRadius, center = center)
                }
            }
            ScatterShape.SQUARE -> {
                drawRect(
                    color = color,
                    topLeft = Offset(center.x - half, center.y - half),
                    size = Size(size, size)
                )
                if (holeRadius > 0f) {
                    drawRect(
                        color = holeColor,
                        topLeft = Offset(center.x - holeRadius, center.y - holeRadius),
                        size = Size(holeRadius * 2, holeRadius * 2)
                    )
                }
            }
            ScatterShape.TRIANGLE -> {
                val path = Path().apply {
                    moveTo(center.x, center.y - half)
                    lineTo(center.x + half, center.y + half)
                    lineTo(center.x - half, center.y + half)
                    close()
                }
                drawPath(path = path, color = color, style = Fill)
            }
            ScatterShape.CROSS -> {
                val strokeW = size / 4f
                drawLine(color, Offset(center.x - half, center.y), Offset(center.x + half, center.y), strokeWidth = strokeW)
                drawLine(color, Offset(center.x, center.y - half), Offset(center.x, center.y + half), strokeWidth = strokeW)
            }
            ScatterShape.X -> {
                val strokeW = size / 4f
                drawLine(color, Offset(center.x - half, center.y - half), Offset(center.x + half, center.y + half), strokeWidth = strokeW)
                drawLine(color, Offset(center.x + half, center.y - half), Offset(center.x - half, center.y + half), strokeWidth = strokeW)
            }
            ScatterShape.CHEVRON_UP -> {
                val path = Path().apply {
                    moveTo(center.x, center.y - half)
                    lineTo(center.x + half, center.y)
                    moveTo(center.x, center.y - half)
                    lineTo(center.x - half, center.y)
                }
                drawPath(path = path, color = color, style = Stroke(width = size / 4f))
            }
            ScatterShape.CHEVRON_DOWN -> {
                val path = Path().apply {
                    moveTo(center.x, center.y + half)
                    lineTo(center.x + half, center.y)
                    moveTo(center.x, center.y + half)
                    lineTo(center.x - half, center.y)
                }
                drawPath(path = path, color = color, style = Stroke(width = size / 4f))
            }
        }
    }
}
