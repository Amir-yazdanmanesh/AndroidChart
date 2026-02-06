package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable

/**
 * Entry class for RadarChart (spider/web chart).
 * Holds a single value for one axis of the radar.
 *
 * Note: RadarEntry does not use X values - position is determined by order in dataset.
 *
 * @property value The value on this radar axis (same as y)
 * @property icon Optional icon drawable
 * @property data Optional additional data
 */
data class RadarEntry(
    val value: Float,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    /** The Y value equals the radar value */
    override val y: Float get() = value

    constructor(value: Float) : this(value, null, null)
    constructor(value: Float, data: Any?) : this(value, null, data)
    constructor(value: Float, icon: Drawable?) : this(value, icon, null)

    override fun toString(): String = "RadarEntry(value=$value)"
}
