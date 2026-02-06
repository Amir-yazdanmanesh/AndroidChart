package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Shape of scatter chart data points.
 */
enum class ScatterShape {
    CIRCLE,
    SQUARE,
    TRIANGLE,
    CROSS,
    X,
    CHEVRON_UP,
    CHEVRON_DOWN
}

/**
 * DataSet for ScatterChart.
 * Contains scatter points with customizable shapes.
 */
data class ScatterDataSet(
    override val entries: List<Entry>,
    override val label: String = "ScatterDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Scatter-specific properties
    /** Shape of data points */
    val shape: ScatterShape = ScatterShape.CIRCLE,
    /** Size of data points */
    val shapeSize: Dp = 10.dp,
    /** Hole radius for hollow shapes */
    val shapeHoleRadius: Dp = 0.dp,
    /** Color of shape holes */
    val shapeHoleColor: Color = Color.White
) : ChartDataSet<Entry> {

    /** Calculated min X value */
    val xMin: Float get() = entries.minOfOrNull { it.x } ?: 0f

    /** Calculated max X value */
    val xMax: Float get() = entries.maxOfOrNull { it.x } ?: 0f

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<Entry>): ScatterDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with a different shape.
     */
    fun withShape(shape: ScatterShape, size: Dp = this.shapeSize): ScatterDataSet =
        copy(shape = shape, shapeSize = size)

    companion object {
        /**
         * Creates a ScatterDataSet from x,y pairs.
         */
        fun fromPairs(vararg pairs: Pair<Float, Float>, label: String = "ScatterDataSet"): ScatterDataSet =
            ScatterDataSet(
                entries = pairs.map { (x, y) -> Entry(x, y) },
                label = label
            )
    }
}
