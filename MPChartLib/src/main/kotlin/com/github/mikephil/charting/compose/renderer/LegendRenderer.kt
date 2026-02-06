package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.github.mikephil.charting.compose.components.LegendConfig
import com.github.mikephil.charting.compose.components.LegendEntryConfig
import com.github.mikephil.charting.compose.components.LegendForm
import com.github.mikephil.charting.compose.components.LegendHorizontalAlignment
import com.github.mikephil.charting.compose.components.LegendOrientation
import com.github.mikephil.charting.compose.components.LegendVerticalAlignment
import com.github.mikephil.charting.compose.data.ChartDataSet

/**
 * Renders the chart legend onto a Compose DrawScope.
 */
object LegendRenderer {

    /**
     * Draws the legend with auto-generated entries from datasets.
     */
    fun DrawScope.drawLegend(
        config: LegendConfig,
        entries: List<LegendEntryConfig>,
        textMeasurer: TextMeasurer,
        chartArea: androidx.compose.ui.geometry.Rect
    ) {
        if (!config.enabled || entries.isEmpty()) return

        val formSizePx = config.formSize.toPx()
        val formToTextSpacePx = config.formToTextSpace.toPx()
        val xEntrySpacePx = config.xEntrySpace.toPx()
        val yEntrySpacePx = config.yEntrySpace.toPx()

        val textStyle = TextStyle(
            color = config.textColor,
            fontSize = config.textSize,
            fontFamily = config.fontFamily
        )

        // Calculate starting position
        var x = when (config.horizontalAlignment) {
            LegendHorizontalAlignment.LEFT -> chartArea.left + 8f
            LegendHorizontalAlignment.CENTER -> chartArea.center.x
            LegendHorizontalAlignment.RIGHT -> chartArea.right - 8f
        }

        var y = when (config.verticalAlignment) {
            LegendVerticalAlignment.TOP -> chartArea.top + 8f
            LegendVerticalAlignment.CENTER -> chartArea.center.y
            LegendVerticalAlignment.BOTTOM -> chartArea.bottom - 8f
        }

        when (config.orientation) {
            LegendOrientation.HORIZONTAL -> {
                drawHorizontalLegend(entries, config, textMeasurer, textStyle, formSizePx, formToTextSpacePx, xEntrySpacePx, yEntrySpacePx, x, y, chartArea)
            }
            LegendOrientation.VERTICAL -> {
                drawVerticalLegend(entries, config, textMeasurer, textStyle, formSizePx, formToTextSpacePx, yEntrySpacePx, x, y)
            }
        }
    }

    private fun DrawScope.drawHorizontalLegend(
        entries: List<LegendEntryConfig>,
        config: LegendConfig,
        textMeasurer: TextMeasurer,
        textStyle: TextStyle,
        formSizePx: Float,
        formToTextSpacePx: Float,
        xEntrySpacePx: Float,
        yEntrySpacePx: Float,
        startX: Float,
        startY: Float,
        chartArea: androidx.compose.ui.geometry.Rect
    ) {
        var x = startX
        var y = startY

        for (entry in entries) {
            val label = entry.label ?: continue
            val measuredText = textMeasurer.measure(label, textStyle)
            val entryWidth = formSizePx + formToTextSpacePx + measuredText.size.width

            // Wrap to next line if needed
            if (x + entryWidth > chartArea.right - 8f && x != startX) {
                x = startX
                y += measuredText.size.height + yEntrySpacePx
            }

            // Draw form
            val formColor = if (entry.formColor != Color.Unspecified) entry.formColor else config.textColor
            drawLegendForm(
                form = if (entry.form == LegendForm.DEFAULT) config.form else entry.form,
                x = x,
                y = y + measuredText.size.height / 2f,
                size = formSizePx,
                color = formColor
            )

            // Draw label
            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(x + formSizePx + formToTextSpacePx, y)
            )

            x += entryWidth + xEntrySpacePx
        }
    }

    private fun DrawScope.drawVerticalLegend(
        entries: List<LegendEntryConfig>,
        config: LegendConfig,
        textMeasurer: TextMeasurer,
        textStyle: TextStyle,
        formSizePx: Float,
        formToTextSpacePx: Float,
        yEntrySpacePx: Float,
        startX: Float,
        startY: Float
    ) {
        var y = startY

        for (entry in entries) {
            val label = entry.label ?: continue
            val measuredText = textMeasurer.measure(label, textStyle)

            val formColor = if (entry.formColor != Color.Unspecified) entry.formColor else config.textColor
            drawLegendForm(
                form = if (entry.form == LegendForm.DEFAULT) config.form else entry.form,
                x = startX,
                y = y + measuredText.size.height / 2f,
                size = formSizePx,
                color = formColor
            )

            drawText(
                textLayoutResult = measuredText,
                topLeft = Offset(startX + formSizePx + formToTextSpacePx, y)
            )

            y += measuredText.size.height + yEntrySpacePx
        }
    }

    private fun DrawScope.drawLegendForm(
        form: LegendForm,
        x: Float,
        y: Float,
        size: Float,
        color: Color
    ) {
        val half = size / 2f

        when (form) {
            LegendForm.NONE, LegendForm.EMPTY, LegendForm.DEFAULT -> {}
            LegendForm.SQUARE -> {
                drawRect(
                    color = color,
                    topLeft = Offset(x, y - half),
                    size = Size(size, size)
                )
            }
            LegendForm.CIRCLE -> {
                drawCircle(
                    color = color,
                    radius = half,
                    center = Offset(x + half, y)
                )
            }
            LegendForm.LINE -> {
                drawLine(
                    color = color,
                    start = Offset(x, y),
                    end = Offset(x + size, y),
                    strokeWidth = 3f
                )
            }
        }
    }

    /**
     * Builds legend entries from a list of chart datasets.
     */
    fun buildEntries(dataSets: List<ChartDataSet<*>>): List<LegendEntryConfig> {
        return dataSets.filter { it.visible }.map { dataSet ->
            LegendEntryConfig(
                label = dataSet.label,
                form = LegendForm.SQUARE,
                formColor = dataSet.colors.firstOrNull() ?: Color.Gray
            )
        }
    }
}
