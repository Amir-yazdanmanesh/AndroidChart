package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable
import kotlin.math.abs

/**
 * Entry class for CandleStickChart (OHLC financial charts).
 * Holds open, high, low, close values for a single candlestick.
 *
 * @property x The X-axis value
 * @property high The shadow high value (upper wick)
 * @property low The shadow low value (lower wick)
 * @property open The opening price
 * @property close The closing price
 * @property icon Optional icon drawable
 * @property data Optional additional data
 */
data class CandleEntry(
    val x: Float,
    val high: Float,
    val low: Float,
    val open: Float,
    val close: Float,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    /** The Y value is the center of the candle (average of high and low) */
    override val y: Float get() = (high + low) / 2f

    /** Returns the overall range between shadow-high and shadow-low */
    val shadowRange: Float get() = abs(high - low)

    /** Returns the body size (difference between open and close) */
    val bodyRange: Float get() = abs(open - close)

    /** Returns true if this is a bullish candle (close > open) */
    val isBullish: Boolean get() = close > open

    /** Returns true if this is a bearish candle (close < open) */
    val isBearish: Boolean get() = close < open

    constructor(
        x: Float,
        high: Float,
        low: Float,
        open: Float,
        close: Float
    ) : this(x, high, low, open, close, null, null)

    constructor(
        x: Float,
        high: Float,
        low: Float,
        open: Float,
        close: Float,
        data: Any?
    ) : this(x, high, low, open, close, null, data)

    constructor(
        x: Float,
        high: Float,
        low: Float,
        open: Float,
        close: Float,
        icon: Drawable?
    ) : this(x, high, low, open, close, icon, null)

    override fun toString(): String = "CandleEntry(x=$x, open=$open, high=$high, low=$low, close=$close)"
}
