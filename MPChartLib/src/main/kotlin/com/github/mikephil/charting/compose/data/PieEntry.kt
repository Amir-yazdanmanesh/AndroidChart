package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable

/**
 * Entry class for PieChart.
 * Holds a single value and optional label for a pie slice.
 *
 * Note: PieEntry does not use X values - position is determined by order in dataset.
 *
 * @property value The value of this pie slice (same as y)
 * @property label Optional label for this slice
 * @property icon Optional icon drawable
 * @property data Optional additional data
 */
data class PieEntry(
    val value: Float,
    val label: String? = null,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    /** The Y value equals the slice value */
    override val y: Float get() = value

    constructor(value: Float) : this(value, null, null, null)
    constructor(value: Float, label: String?) : this(value, label, null, null)
    constructor(value: Float, data: Any?) : this(value, null, null, data)
    constructor(value: Float, label: String?, data: Any?) : this(value, label, null, data)
    constructor(value: Float, icon: Drawable?) : this(value, null, icon, null)
    constructor(value: Float, label: String?, icon: Drawable?) : this(value, label, icon, null)
    constructor(value: Float, icon: Drawable?, data: Any?) : this(value, null, icon, data)

    override fun toString(): String = "PieEntry(value=$value, label=$label)"
}
