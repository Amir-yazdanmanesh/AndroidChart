package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable

/**
 * Base sealed interface for all chart entries.
 * Provides common properties shared across different chart entry types.
 */
sealed interface ChartEntry {
    /** The Y value of this entry */
    val y: Float

    /** Optional icon drawable for this entry */
    val icon: Drawable?

    /** Optional additional data this entry represents */
    val data: Any?
}

/**
 * Standard entry representing a single data point with X and Y coordinates.
 * Used for LineChart, ScatterChart, and as base for other chart types.
 *
 * @property x The X-axis value
 * @property y The Y-axis value (the actual value of the entry)
 * @property icon Optional icon drawable
 * @property data Optional additional data this entry represents
 */
data class Entry(
    val x: Float,
    override val y: Float,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    constructor(x: Float, y: Float) : this(x, y, null, null)
    constructor(x: Float, y: Float, data: Any?) : this(x, y, null, data)
    constructor(x: Float, y: Float, icon: Drawable?) : this(x, y, icon, null)

    override fun toString(): String = "Entry(x=$x, y=$y)"

    companion object {
        const val FLOAT_EPSILON = 0.00001f
    }

    /**
     * Compares value, x and data of the entries.
     * Returns true if entries are equal in those points.
     */
    fun equalTo(other: Entry?): Boolean {
        if (other == null) return false
        if (other.data != this.data) return false
        if (kotlin.math.abs(other.x - this.x) > FLOAT_EPSILON) return false
        if (kotlin.math.abs(other.y - this.y) > FLOAT_EPSILON) return false
        return true
    }
}
