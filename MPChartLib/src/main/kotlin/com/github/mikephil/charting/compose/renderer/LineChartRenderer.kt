package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.compose.data.LineDataSet
import com.github.mikephil.charting.compose.data.LineMode

/**
 * Renders line chart data onto a Compose DrawScope.
 */
object LineChartRenderer {

    /**
     * Draws a complete line dataset within the given viewport.
     */
    fun DrawScope.drawLineDataSet(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        animationPhase: Float = 1f
    ) {
        if (!dataSet.visible || dataSet.entries.isEmpty()) return

        when (dataSet.mode) {
            LineMode.LINEAR -> drawLinear(dataSet, viewport, animationPhase)
            LineMode.STEPPED -> drawStepped(dataSet, viewport, animationPhase)
            LineMode.CUBIC_BEZIER -> drawCubicBezier(dataSet, viewport, animationPhase)
            LineMode.HORIZONTAL_BEZIER -> drawHorizontalBezier(dataSet, viewport, animationPhase)
        }

        // Draw fill under line
        if (dataSet.fill.enabled) {
            drawLineFill(dataSet, viewport, animationPhase)
        }

        // Draw circles
        if (dataSet.circle.enabled) {
            drawCircles(dataSet, viewport, animationPhase)
        }
    }

    private fun DrawScope.drawLinear(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val entries = dataSet.entries
        if (entries.size < 2) return

        val lineWidthPx = dataSet.lineWidth.toPx()
        val color = dataSet.colors.first()

        for (i in 0 until entries.size - 1) {
            val e1 = entries[i]
            val e2 = entries[i + 1]

            val start = viewport.dataToPixel(e1.x, e1.y * phase)
            val end = viewport.dataToPixel(e2.x, e2.y * phase)

            val segmentColor = dataSet.getColor(i)

            drawLine(
                color = segmentColor,
                start = start,
                end = end,
                strokeWidth = lineWidthPx,
                cap = dataSet.lineCap,
                pathEffect = dataSet.pathEffect
            )
        }
    }

    private fun DrawScope.drawStepped(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val entries = dataSet.entries
        if (entries.size < 2) return

        val lineWidthPx = dataSet.lineWidth.toPx()
        val color = dataSet.colors.first()

        for (i in 0 until entries.size - 1) {
            val e1 = entries[i]
            val e2 = entries[i + 1]

            val p1 = viewport.dataToPixel(e1.x, e1.y * phase)
            val mid = viewport.dataToPixel(e2.x, e1.y * phase)
            val p2 = viewport.dataToPixel(e2.x, e2.y * phase)

            val segmentColor = dataSet.getColor(i)

            // Horizontal step
            drawLine(
                color = segmentColor,
                start = p1,
                end = mid,
                strokeWidth = lineWidthPx,
                cap = dataSet.lineCap,
                pathEffect = dataSet.pathEffect
            )
            // Vertical step
            drawLine(
                color = segmentColor,
                start = mid,
                end = p2,
                strokeWidth = lineWidthPx,
                cap = dataSet.lineCap,
                pathEffect = dataSet.pathEffect
            )
        }
    }

    private fun DrawScope.drawCubicBezier(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val entries = dataSet.entries
        if (entries.size < 2) return

        val intensity = dataSet.cubicIntensity
        val lineWidthPx = dataSet.lineWidth.toPx()
        val color = dataSet.colors.first()

        val path = Path()
        val first = viewport.dataToPixel(entries[0].x, entries[0].y * phase)
        path.moveTo(first.x, first.y)

        for (i in 1 until entries.size) {
            val prevPrev = entries[(i - 2).coerceAtLeast(0)]
            val prev = entries[i - 1]
            val cur = entries[i]
            val next = entries[(i + 1).coerceAtMost(entries.size - 1)]

            val prevP = viewport.dataToPixel(prev.x, prev.y * phase)
            val curP = viewport.dataToPixel(cur.x, cur.y * phase)
            val prevPrevP = viewport.dataToPixel(prevPrev.x, prevPrev.y * phase)
            val nextP = viewport.dataToPixel(next.x, next.y * phase)

            val cpx1 = prevP.x + (curP.x - prevPrevP.x) * intensity
            val cpy1 = prevP.y + (curP.y - prevPrevP.y) * intensity
            val cpx2 = curP.x - (nextP.x - prevP.x) * intensity
            val cpy2 = curP.y - (nextP.y - prevP.y) * intensity

            path.cubicTo(cpx1, cpy1, cpx2, cpy2, curP.x, curP.y)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = lineWidthPx,
                cap = dataSet.lineCap,
                pathEffect = dataSet.pathEffect
            )
        )
    }

    private fun DrawScope.drawHorizontalBezier(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val entries = dataSet.entries
        if (entries.size < 2) return

        val lineWidthPx = dataSet.lineWidth.toPx()
        val color = dataSet.colors.first()

        val path = Path()
        val first = viewport.dataToPixel(entries[0].x, entries[0].y * phase)
        path.moveTo(first.x, first.y)

        for (i in 1 until entries.size) {
            val prev = entries[i - 1]
            val cur = entries[i]

            val prevP = viewport.dataToPixel(prev.x, prev.y * phase)
            val curP = viewport.dataToPixel(cur.x, cur.y * phase)

            val cpx = (prevP.x + curP.x) / 2f

            path.cubicTo(cpx, prevP.y, cpx, curP.y, curP.x, curP.y)
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = lineWidthPx,
                cap = dataSet.lineCap,
                pathEffect = dataSet.pathEffect
            )
        )
    }

    private fun DrawScope.drawLineFill(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val entries = dataSet.entries
        if (entries.size < 2) return

        val fillPath = Path()
        val firstPixel = viewport.dataToPixel(entries[0].x, entries[0].y * phase)
        val baseline = viewport.dataToPixelY(0f).coerceIn(viewport.contentRect.top, viewport.contentRect.bottom)

        fillPath.moveTo(firstPixel.x, baseline)
        fillPath.lineTo(firstPixel.x, firstPixel.y)

        for (i in 1 until entries.size) {
            val p = viewport.dataToPixel(entries[i].x, entries[i].y * phase)
            fillPath.lineTo(p.x, p.y)
        }

        val lastPixel = viewport.dataToPixel(entries.last().x, entries.last().y * phase)
        fillPath.lineTo(lastPixel.x, baseline)
        fillPath.close()

        drawPath(
            path = fillPath,
            color = dataSet.fill.color,
            alpha = dataSet.fill.alpha,
            style = Fill
        )
    }

    private fun DrawScope.drawCircles(
        dataSet: LineDataSet,
        viewport: ChartViewport,
        phase: Float
    ) {
        val circle = dataSet.circle
        val radiusPx = circle.radius.toPx()
        val holeRadiusPx = circle.holeRadius.toPx()

        for ((i, entry) in dataSet.entries.withIndex()) {
            val pixel = viewport.dataToPixel(entry.x, entry.y * phase)

            if (!viewport.isInBounds(pixel)) continue

            val circleColor = circle.getColor(i)

            // Outer circle
            drawCircle(
                color = circleColor,
                radius = radiusPx,
                center = pixel
            )

            // Hole
            if (circle.holeEnabled && holeRadiusPx < radiusPx) {
                drawCircle(
                    color = circle.holeColor,
                    radius = holeRadiusPx,
                    center = pixel
                )
            }
        }
    }
}
