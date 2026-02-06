package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Drawing mode for line charts.
 */
enum class LineMode {
    /** Standard linear interpolation between points */
    LINEAR,
    /** Step-wise horizontal then vertical lines */
    STEPPED,
    /** Smooth cubic bezier curves */
    CUBIC_BEZIER,
    /** Horizontal bezier curves */
    HORIZONTAL_BEZIER
}

/**
 * Configuration for line chart circles (data point indicators).
 */
data class CircleConfig(
    val enabled: Boolean = true,
    val radius: Dp = 4.dp,
    val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    val holeEnabled: Boolean = true,
    val holeRadius: Dp = 2.dp,
    val holeColor: Color = Color.White
) {
    fun getColor(index: Int): Color = colors[index % colors.size]
}

/**
 * Configuration for line fill (area under line).
 */
data class FillConfig(
    val enabled: Boolean = false,
    val color: Color = Color(0x808CEAFF),
    val alpha: Float = 0.5f
)

/**
 * DataSet for LineChart.
 * Immutable data class representing a line with its styling.
 */
data class LineDataSet(
    override val entries: List<Entry>,
    override val label: String = "LineDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Line-specific properties
    val mode: LineMode = LineMode.LINEAR,
    val lineWidth: Dp = 2.dp,
    val lineCap: StrokeCap = StrokeCap.Round,
    val cubicIntensity: Float = 0.2f,
    val pathEffect: PathEffect? = null,
    val circle: CircleConfig = CircleConfig(),
    val fill: FillConfig = FillConfig()
) : ChartDataSet<Entry> {

    /** Calculated min X value */
    val xMin: Float get() = entries.minOfOrNull { it.x } ?: 0f

    /** Calculated max X value */
    val xMax: Float get() = entries.maxOfOrNull { it.x } ?: 0f

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<Entry>): LineDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with a single color.
     */
    fun withColor(color: Color): LineDataSet = copy(colors = listOf(color))

    /**
     * Creates a copy with fill enabled.
     */
    fun withFill(color: Color = this.fill.color, alpha: Float = this.fill.alpha): LineDataSet =
        copy(fill = fill.copy(enabled = true, color = color, alpha = alpha))

    /**
     * Creates a copy with circles disabled.
     */
    fun withoutCircles(): LineDataSet = copy(circle = circle.copy(enabled = false))

    /**
     * Creates a copy with cubic bezier mode.
     */
    fun asCubic(intensity: Float = 0.2f): LineDataSet =
        copy(mode = LineMode.CUBIC_BEZIER, cubicIntensity = intensity.coerceIn(0.05f, 1f))

    /**
     * Creates a copy with stepped mode.
     */
    fun asStepped(): LineDataSet = copy(mode = LineMode.STEPPED)

    /**
     * Creates a dashed line effect.
     */
    fun withDash(lineLength: Float, spaceLength: Float, phase: Float = 0f): LineDataSet =
        copy(pathEffect = PathEffect.dashPathEffect(floatArrayOf(lineLength, spaceLength), phase))

    companion object {
        /**
         * Creates a LineDataSet from x,y pairs.
         */
        fun fromPairs(vararg pairs: Pair<Float, Float>, label: String = "LineDataSet"): LineDataSet =
            LineDataSet(
                entries = pairs.map { (x, y) -> Entry(x, y) },
                label = label
            )

        /**
         * Creates a LineDataSet from y values with auto-generated x indices.
         */
        fun fromValues(vararg values: Float, label: String = "LineDataSet"): LineDataSet =
            LineDataSet(
                entries = values.mapIndexed { index, y -> Entry(index.toFloat(), y) },
                label = label
            )
    }
}
