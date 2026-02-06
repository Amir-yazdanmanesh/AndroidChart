package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable

/**
 * Entry class for BubbleChart.
 * Holds x, y coordinates and a size value for the bubble radius.
 *
 * @property x The X-axis value
 * @property y The Y-axis value
 * @property size The size of the bubble
 * @property icon Optional icon drawable
 * @property data Optional additional data
 */
data class BubbleEntry(
    val x: Float,
    override val y: Float,
    val size: Float,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    constructor(x: Float, y: Float, size: Float) : this(x, y, size, null, null)
    constructor(x: Float, y: Float, size: Float, data: Any?) : this(x, y, size, null, data)
    constructor(x: Float, y: Float, size: Float, icon: Drawable?) : this(x, y, size, icon, null)

    override fun toString(): String = "BubbleEntry(x=$x, y=$y, size=$size)"
}
