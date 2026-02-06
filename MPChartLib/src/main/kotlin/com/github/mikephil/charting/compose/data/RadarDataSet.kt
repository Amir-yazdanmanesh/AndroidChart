package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * DataSet for RadarChart (spider/web chart).
 * Contains radar entries with a single value per axis.
 */
data class RadarDataSet(
    override val entries: List<RadarEntry>,
    override val label: String = "RadarDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Radar-specific properties
    /** Line width of the radar polygon */
    val lineWidth: Dp = 2.dp,
    /** Whether to draw the filled area */
    val drawFilled: Boolean = true,
    /** Fill color (with alpha) */
    val fillColor: Color = Color(0x808CEAFF),
    /** Fill alpha (0-1) */
    val fillAlpha: Float = 0.5f,
    /** Whether to draw highlight circle on selected */
    val drawHighlightCircleEnabled: Boolean = true,
    /** Highlight circle inner radius */
    val highlightCircleInnerRadius: Dp = 3.dp,
    /** Highlight circle outer radius */
    val highlightCircleOuterRadius: Dp = 4.dp,
    /** Highlight circle stroke color */
    val highlightCircleStrokeColor: Color = Color.White,
    /** Highlight circle stroke alpha */
    val highlightCircleStrokeAlpha: Float = 0.3f,
    /** Highlight circle stroke width */
    val highlightCircleStrokeWidth: Dp = 2.dp
) : ChartDataSet<RadarEntry> {

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<RadarEntry>): RadarDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with fill styling.
     */
    fun withFill(color: Color, alpha: Float = 0.5f): RadarDataSet =
        copy(drawFilled = true, fillColor = color, fillAlpha = alpha)

    /**
     * Creates a copy without fill.
     */
    fun withoutFill(): RadarDataSet = copy(drawFilled = false)

    companion object {
        /**
         * Creates a RadarDataSet from values.
         */
        fun fromValues(vararg values: Float, label: String = "RadarDataSet"): RadarDataSet =
            RadarDataSet(
                entries = values.map { RadarEntry(it) },
                label = label
            )
    }
}
