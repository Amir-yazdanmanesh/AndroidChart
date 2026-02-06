package com.github.mikephil.charting.compose.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Paint style for candle bodies.
 */
enum class CandlePaintStyle {
    FILL,
    STROKE,
    FILL_AND_STROKE
}

/**
 * DataSet for CandleStickChart (OHLC financial charts).
 * Contains candlestick entries with open, high, low, close values.
 */
data class CandleDataSet(
    override val entries: List<CandleEntry>,
    override val label: String = "CandleDataSet",
    override val colors: List<Color> = listOf(Color(0xFF8CEAFF)),
    override val axisDependency: AxisDependency = AxisDependency.LEFT,
    override val highlightEnabled: Boolean = true,
    override val visible: Boolean = true,
    override val drawValues: Boolean = true,
    override val valueTextColor: Color = Color.Black,
    override val valueTextSize: TextUnit = 12.sp,
    override val valueFontFamily: FontFamily? = null,

    // Candle-specific properties
    /** Width of the shadow (wick) lines */
    val shadowWidth: Dp = 1.dp,
    /** Color of shadow lines (null = use candle body color) */
    val shadowColor: Color? = null,
    /** Whether shadow color follows body color */
    val shadowColorSameAsCandle: Boolean = true,
    /** Color for increasing candles (close > open) */
    val increasingColor: Color = Color(0xFF4CAF50),
    /** Color for decreasing candles (close < open) */
    val decreasingColor: Color = Color(0xFFF44336),
    /** Color for neutral candles (close == open) */
    val neutralColor: Color = Color.Gray,
    /** Paint style for increasing candles */
    val increasingPaintStyle: CandlePaintStyle = CandlePaintStyle.FILL,
    /** Paint style for decreasing candles */
    val decreasingPaintStyle: CandlePaintStyle = CandlePaintStyle.FILL,
    /** Whether to show candle bar (body) */
    val showCandleBar: Boolean = true,
    /** Relative bar width (0.0 - 1.0) */
    val barSpace: Float = 0.1f
) : ChartDataSet<CandleEntry> {

    /** Calculated min X value */
    val xMin: Float get() = entries.minOfOrNull { it.x } ?: 0f

    /** Calculated max X value */
    val xMax: Float get() = entries.maxOfOrNull { it.x } ?: 0f

    /** Calculated min low value */
    val lowMin: Float get() = entries.minOfOrNull { it.low } ?: 0f

    /** Calculated max high value */
    val highMax: Float get() = entries.maxOfOrNull { it.high } ?: 0f

    /**
     * Creates a copy with updated entries.
     */
    fun withEntries(newEntries: List<CandleEntry>): CandleDataSet = copy(entries = newEntries)

    /**
     * Creates a copy with custom bull/bear colors.
     */
    fun withColors(increasing: Color, decreasing: Color): CandleDataSet =
        copy(increasingColor = increasing, decreasingColor = decreasing)

    companion object {
        /**
         * Creates a CandleDataSet from OHLC arrays.
         */
        fun fromOHLC(
            vararg ohlc: FloatArray,
            label: String = "CandleDataSet"
        ): CandleDataSet {
            require(ohlc.all { it.size == 4 }) { "Each OHLC array must have exactly 4 values" }
            return CandleDataSet(
                entries = ohlc.mapIndexed { index, values ->
                    CandleEntry(
                        x = index.toFloat(),
                        high = values[1],
                        low = values[2],
                        open = values[0],
                        close = values[3]
                    )
                },
                label = label
            )
        }
    }
}
