package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Value position for pie chart labels.
 */
enum class ValuePosition {
    INSIDE_SLICE,
    OUTSIDE_SLICE
}

/**
 * DataSet for PieChart.
 * Contains pie slices with styling options.
 */
data class PieDataSet(
    override val entries: List<PieEntry>,
    override val label: String = "PieDataSet",
    override val colors: List<Color> = listOf(
        Color(0xFFE91E63),
        Color(0xFF2196F3),
        Color(0xFFFFEB3B),
        Color(0xFF4CAF50),
        Color(0xFFFF9800)
    ),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.White,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Pie-specific properties
    /** Space between slices in degrees */
    val sliceSpace: Float = 0f,
    /** How far selected slice is shifted outward */
    val selectionShift: Dp = 18.dp,
    /** Position of value labels */
    val valuePosition: ValuePosition = ValuePosition.INSIDE_SLICE,
    /** Position of entry labels (slice names) */
    val entryLabelPosition: ValuePosition = ValuePosition.INSIDE_SLICE,
    /** Color for entry labels */
    val entryLabelColor: Color = Color.White,
    /** Text size for entry labels */
    val entryLabelTextSize: TextUnit = 12.sp,
    /** Whether to draw entry labels */
    val drawEntryLabels: Boolean = true,
    /** Length of polylines connecting labels to slices */
    val valueLinePart1Length: Float = 0.3f,
    val valueLinePart2Length: Float = 0.4f,
    /** Color of value polylines */
    val valueLineColor: Color = Color.Gray,
    /** Width of value polylines */
    val valueLineWidth: Dp = 1.dp,
    /** Whether value line varies with slice color */
    val valueLineVariableLength: Boolean = true,
    /** Whether to use automatic slice colors for value lines */
    val useValueColorForLine: Boolean = false
) : ChartDataSet<PieEntry> {

    /** Total sum of all values */
    val yValueSum: Float get() = entries.sumOf { it.value.toDouble() }.toFloat()

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<PieEntry>): PieDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with outside value labels and polylines.
     */
    fun withOutsideValues(): PieDataSet = copy(
        valuePosition = ValuePosition.OUTSIDE_SLICE,
        entryLabelPosition = ValuePosition.OUTSIDE_SLICE
    )

    /**
     * Creates a copy with slice spacing.
     */
    fun withSliceSpace(degrees: Float): PieDataSet = copy(sliceSpace = degrees)

    companion object {
        /**
         * Creates a PieDataSet from value-label pairs.
         */
        fun fromLabeledValues(
            vararg pairs: Pair<Float, String>,
            label: String = "PieDataSet"
        ): PieDataSet =
            PieDataSet(
                entries = pairs.map { (value, sliceLabel) -> PieEntry(value, sliceLabel) },
                label = label
            )

        /**
         * Creates a PieDataSet from values only.
         */
        fun fromValues(vararg values: Float, label: String = "PieDataSet"): PieDataSet =
            PieDataSet(
                entries = values.map { PieEntry(it) },
                label = label
            )
    }
}
