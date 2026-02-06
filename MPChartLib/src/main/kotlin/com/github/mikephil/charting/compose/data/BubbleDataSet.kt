package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * DataSet for BubbleChart.
 * Contains bubble entries with x, y, and size.
 */
data class BubbleDataSet(
    override val entries: List<BubbleEntry>,
    override val label: String = "BubbleDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Bubble-specific properties
    /** Whether to normalize bubble sizes */
    val normalizeSizeEnabled: Boolean = true,
    /** Highlight circle width around selected bubble */
    val highlightCircleWidth: Float = 2.5f
) : ChartDataSet<BubbleEntry> {

    /** Calculated min X value */
    val xMin: Float get() = entries.minOfOrNull { it.x } ?: 0f

    /** Calculated max X value */
    val xMax: Float get() = entries.maxOfOrNull { it.x } ?: 0f

    /** Maximum bubble size in dataset */
    val maxSize: Float get() = entries.maxOfOrNull { it.size } ?: 0f

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<BubbleEntry>): BubbleDataSet = copy(entries = newEntries)

    companion object {
        /**
         * Creates a BubbleDataSet from x, y, size triples.
         */
        fun fromTriples(
            vararg triples: Triple<Float, Float, Float>,
            label: String = "BubbleDataSet"
        ): BubbleDataSet =
            BubbleDataSet(
                entries = triples.map { (x, y, size) -> BubbleEntry(x, y, size) },
                label = label
            )
    }
}
