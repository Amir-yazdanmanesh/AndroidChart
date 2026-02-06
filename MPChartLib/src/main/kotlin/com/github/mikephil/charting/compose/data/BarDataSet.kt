package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * DataSet for BarChart.
 * Supports both regular and stacked bars.
 */
data class BarDataSet(
    override val entries: List<BarEntry>,
    override val label: String = "BarDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Bar-specific properties
    /** Colors for individual stacks (used when bars are stacked) */
    val stackColors: List<Color> = colors,
    /** Labels for stacked bar segments */
    val stackLabels: List<String> = emptyList(),
    /** Alpha for highlighting */
    val highlightAlpha: Float = 0.85f,
    /** Width of bar borders */
    val barBorderWidth: Dp = 0.dp,
    /** Color of bar borders */
    val barBorderColor: Color = Color.Black
) : ChartDataSet<BarEntry> {

    /** Calculated min X value */
    val xMin: Float get() = entries.minOfOrNull { it.x } ?: 0f

    /** Calculated max X value */
    val xMax: Float get() = entries.maxOfOrNull { it.x } ?: 0f

    /** Returns true if this dataset contains stacked bars */
    val isStacked: Boolean get() = entries.any { it.isStacked }

    /** Returns the maximum number of stacks in any entry */
    val stackSize: Int get() = entries.maxOfOrNull { it.yValues?.size ?: 1 } ?: 1

    /**
     * Get color for a specific stack index.
     */
    fun getStackColor(stackIndex: Int): Color = stackColors[stackIndex % stackColors.size]

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<BarEntry>): BarDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with stack colors.
     */
    fun withStackColors(stackColors: List<Color>): BarDataSet =
        copy(stackColors = stackColors)

    /**
     * Creates a copy with stack labels.
     */
    fun withStackLabels(vararg labels: String): BarDataSet =
        copy(stackLabels = labels.toList())

    /**
     * Creates a copy with border styling.
     */
    fun withBorder(width: Dp, color: Color = Color.Black): BarDataSet =
        copy(barBorderWidth = width, barBorderColor = color)

    companion object {
        /**
         * Creates a BarDataSet from x,y pairs.
         */
        fun fromPairs(vararg pairs: Pair<Float, Float>, label: String = "BarDataSet"): BarDataSet =
            BarDataSet(
                entries = pairs.map { (x, y) -> BarEntry(x, y) },
                label = label
            )

        /**
         * Creates a BarDataSet from y values with auto-generated x indices.
         */
        fun fromValues(vararg values: Float, label: String = "BarDataSet"): BarDataSet =
            BarDataSet(
                entries = values.mapIndexed { index, y -> BarEntry(index.toFloat(), y) },
                label = label
            )

        /**
         * Creates a stacked BarDataSet.
         */
        fun stacked(
            vararg stackedValues: FloatArray,
            label: String = "BarDataSet"
        ): BarDataSet =
            BarDataSet(
                entries = stackedValues.mapIndexed { index, values ->
                    BarEntry(index.toFloat(), values)
                },
                label = label
            )
    }
}
