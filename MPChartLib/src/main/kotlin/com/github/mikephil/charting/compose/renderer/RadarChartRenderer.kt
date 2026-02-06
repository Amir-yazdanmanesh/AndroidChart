package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.RadarDataSet
import kotlin.math.cos
import kotlin.math.sin

private const val DEG2RAD = Math.PI.toFloat() / 180f

/**
 * Renders radar chart data onto a Compose DrawScope.
 */
object RadarChartRenderer {

    /**
     * Draws a radar dataset as a polygon on the chart.
     *
     * @param dataSet The radar dataset to render
     * @param center Center point of the radar
     * @param radius Maximum radius of the radar
     * @param maxValue Maximum data value (for normalization)
     * @param rotationAngle Starting rotation angle
     * @param animationPhase Animation progress (0-1)
     */
    fun DrawScope.drawRadarDataSet(
        dataSet: RadarDataSet,
        center: Offset,
        radius: Float,
        maxValue: Float,
        rotationAngle: Float = 270f,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty() || maxValue == 0f) return

        val entryCount = dataSet.entries.size
        val sliceAngle = 360f / entryCount
        val lineWidthPx = dataSet.lineWidth.toPx()
        val color = dataSet.colors.first()

        val path = Path()

        for ((i, entry) in dataSet.entries.withIndex()) {
            val normalizedValue = (entry.value * animationPhase) / maxValue
            val r = normalizedValue * radius
            val angle = (rotationAngle + i * sliceAngle) * DEG2RAD

            val x = center.x + r * cos(angle)
            val y = center.y + r * sin(angle)

            if (i == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        path.close()

        // Draw filled area
        if (dataSet.drawFilled) {
            drawPath(
                path = path,
                color = dataSet.fillColor,
                alpha = dataSet.fillAlpha,
                style = Fill
            )
        }

        // Draw polygon outline
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = lineWidthPx)
        )
    }

    /**
     * Draws the radar web (background grid lines).
     *
     * @param center Center point
     * @param radius Maximum radius
     * @param axisCount Number of axes
     * @param webCount Number of concentric web rings
     * @param webColor Color of the web lines
     * @param webLineWidth Width of the web lines
     * @param rotationAngle Starting rotation
     */
    fun DrawScope.drawRadarWeb(
        center: Offset,
        radius: Float,
        axisCount: Int,
        webCount: Int = 5,
        webColor: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.LightGray,
        webLineWidth: Float = 1f,
        rotationAngle: Float = 270f
    ) {
        if (axisCount < 3) return

        val sliceAngle = 360f / axisCount

        // Draw axis lines from center
        for (i in 0 until axisCount) {
            val angle = (rotationAngle + i * sliceAngle) * DEG2RAD
            val endX = center.x + radius * cos(angle)
            val endY = center.y + radius * sin(angle)

            drawLine(
                color = webColor,
                start = center,
                end = Offset(endX, endY),
                strokeWidth = webLineWidth
            )
        }

        // Draw concentric web rings
        for (ring in 1..webCount) {
            val ringRadius = radius * ring / webCount
            val ringPath = Path()

            for (i in 0 until axisCount) {
                val angle = (rotationAngle + i * sliceAngle) * DEG2RAD
                val x = center.x + ringRadius * cos(angle)
                val y = center.y + ringRadius * sin(angle)

                if (i == 0) {
                    ringPath.moveTo(x, y)
                } else {
                    ringPath.lineTo(x, y)
                }
            }
            ringPath.close()

            drawPath(
                path = ringPath,
                color = webColor,
                style = Stroke(width = webLineWidth)
            )
        }
    }
}
