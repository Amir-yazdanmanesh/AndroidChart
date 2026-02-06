package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Enum defining which Y-axis this dataset should be plotted against.
 */
enum class AxisDependency {
    LEFT,
    RIGHT
}

/**
 * Rounding mode for finding entries by X value.
 */
enum class Rounding {
    UP,
    DOWN,
    CLOSEST
}

/**
 * Base sealed interface for all chart data sets.
 * Provides common properties and computed values.
 */
sealed interface ChartDataSet<T : ChartEntry> {
    /** The entries in this dataset */
    val entries: List<T>

    /** Label describing this dataset */
    val label: String

    /** Colors for rendering data points (cycled if fewer than entries) */
    val colors: List<Color>

    /** Which Y-axis to use for scaling */
    val axisDependency: AxisDependency

    /** Whether highlighting is enabled for this dataset */
    val highlightEnabled: Boolean

    /** Whether this dataset is visible */
    val visible: Boolean

    /** Whether to draw values on data points */
    val drawValues: Boolean

    /** Text color for value labels */
    val valueTextColor: Color

    /** Text size for value labels */
    val valueTextSize: TextUnit

    /** Font family for value labels */
    val valueFontFamily: FontFamily?

    // Computed properties
    val entryCount: Int get() = entries.size
    val isEmpty: Boolean get() = entries.isEmpty()

    val yMin: Float get() = entries.minOfOrNull { it.y } ?: 0f
    val yMax: Float get() = entries.maxOfOrNull { it.y } ?: 0f

    /** Get color for entry at given index (cycles through colors) */
    fun getColor(index: Int): Color = colors[index % colors.size]

    /** Get entry at index */
    fun getEntryForIndex(index: Int): T? = entries.getOrNull(index)
}

/**
 * Base configuration for all datasets.
 */
data class DataSetConfig(
    val label: String = "DataSet",
    val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    val axisDependency: AxisDependency = AxisDependency.LEFT,
    val highlightEnabled: Boolean = true,
    val visible: Boolean = true,
    val drawValues: Boolean = true,
    val valueTextColor: Color = Color.Black,
    val valueTextSize: TextUnit = 12.sp,
    val valueFontFamily: FontFamily? = null
)
