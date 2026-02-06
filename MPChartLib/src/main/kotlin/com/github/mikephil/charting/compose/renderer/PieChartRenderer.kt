package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.github.mikephil.charting.compose.data.PieDataSet
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val DEG2RAD = Math.PI.toFloat() / 180f

/**
 * Renders pie chart data onto a Compose DrawScope.
 */
object PieChartRenderer {

    /**
     * Draws a pie chart within the given bounds.
     *
     * @param dataSet The pie dataset to render
     * @param center Center of the pie
     * @param radius Outer radius of the pie
     * @param rotationAngle Starting rotation angle in degrees
     * @param holeRadius Inner hole radius (0 = no hole, creates donut effect)
     * @param animationPhase Animation progress (0-1)
     */
    fun DrawScope.drawPieDataSet(
        dataSet: PieDataSet,
        center: Offset,
        radius: Float,
        rotationAngle: Float = 270f,
        holeRadius: Float = 0f,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return

        val yValueSum = dataSet.yValueSum
        if (yValueSum == 0f) return

        val sliceSpace = dataSet.sliceSpace
        var angle = rotationAngle

        val ovalRect = Rect(
            left = center.x - radius,
            top = center.y - radius,
            right = center.x + radius,
            bottom = center.y + radius
        )

        for ((i, entry) in dataSet.entries.withIndex()) {
            val sliceAngle = (entry.value / yValueSum) * 360f * animationPhase
            val color = dataSet.getColor(i)

            if (sliceAngle > 0f) {
                drawArc(
                    color = color,
                    startAngle = angle + sliceSpace / 2f,
                    sweepAngle = sliceAngle - sliceSpace,
                    useCenter = true,
                    topLeft = ovalRect.topLeft,
                    size = Size(ovalRect.width, ovalRect.height),
                    style = Fill
                )
            }

            angle += sliceAngle
        }

        // Draw hole
        if (holeRadius > 0f) {
            drawCircle(
                color = androidx.compose.ui.graphics.Color.White,
                radius = holeRadius,
                center = center
            )
        }
    }

    /**
     * Draws slice highlight (selected slice shifted outward).
     */
    fun DrawScope.drawPieHighlight(
        dataSet: PieDataSet,
        highlightIndex: Int,
        center: Offset,
        radius: Float,
        rotationAngle: Float = 270f,
        shiftDistance: Float = 18f,
        animationPhase: Float = 1f
    ) {
        if (highlightIndex < 0 || highlightIndex >= dataSet.entries.size) return

        val yValueSum = dataSet.yValueSum
        if (yValueSum == 0f) return

        // Calculate angle for highlighted slice
        var angle = rotationAngle
        for (i in 0 until highlightIndex) {
            angle += (dataSet.entries[i].value / yValueSum) * 360f * animationPhase
        }
        val sliceAngle = (dataSet.entries[highlightIndex].value / yValueSum) * 360f * animationPhase
        val midAngle = angle + sliceAngle / 2f

        // Shift center outward
        val shiftX = cos(midAngle * DEG2RAD) * shiftDistance
        val shiftY = sin(midAngle * DEG2RAD) * shiftDistance
        val shiftedCenter = Offset(center.x + shiftX, center.y + shiftY)

        val color = dataSet.getColor(highlightIndex)

        val ovalRect = Rect(
            left = shiftedCenter.x - radius,
            top = shiftedCenter.y - radius,
            right = shiftedCenter.x + radius,
            bottom = shiftedCenter.y + radius
        )

        drawArc(
            color = color,
            startAngle = angle + dataSet.sliceSpace / 2f,
            sweepAngle = sliceAngle - dataSet.sliceSpace,
            useCenter = true,
            topLeft = ovalRect.topLeft,
            size = Size(ovalRect.width, ovalRect.height),
            style = Fill
        )
    }
}
