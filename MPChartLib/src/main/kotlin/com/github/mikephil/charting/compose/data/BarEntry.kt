package com.github.mikephil.charting.compose.data

import android.graphics.drawable.Drawable
import kotlin.math.abs

/**
 * Entry class for BarChart, supporting both regular and stacked bars.
 *
 * For regular bars, use the constructor with single y value.
 * For stacked bars, use the constructor with yValues array.
 *
 * @property x The X-axis value
 * @property y The Y-axis value (for stacked bars, this is the sum of all values)
 * @property yValues The individual stack values (null for non-stacked bars)
 * @property icon Optional icon drawable
 * @property data Optional additional data
 */
data class BarEntry(
    val x: Float,
    override val y: Float,
    val yValues: FloatArray? = null,
    override val icon: Drawable? = null,
    override val data: Any? = null
) : ChartEntry {

    /** The calculated ranges for individual stack values */
    val ranges: List<Range> by lazy { calculateRanges() }

    /** Sum of all negative values (as positive number) */
    val negativeSum: Float by lazy { calculateNegativeSum() }

    /** Sum of all positive values */
    val positiveSum: Float by lazy { calculatePositiveSum() }

    /** Returns true if this entry is stacked (has multiple values) */
    val isStacked: Boolean get() = yValues != null

    // Secondary constructors for convenience

    /** Constructor for non-stacked bar */
    constructor(x: Float, y: Float) : this(x, y, null, null, null)

    /** Constructor for non-stacked bar with data */
    constructor(x: Float, y: Float, data: Any?) : this(x, y, null, null, data)

    /** Constructor for non-stacked bar with icon */
    constructor(x: Float, y: Float, icon: Drawable?) : this(x, y, null, icon, null)

    /** Constructor for non-stacked bar with icon and data */
    constructor(x: Float, y: Float, icon: Drawable?, data: Any?) : this(x, y, null, icon, data)

    /** Constructor for stacked bar */
    constructor(x: Float, yValues: FloatArray) : this(x, calcSum(yValues), yValues, null, null)

    /** Constructor for stacked bar with data */
    constructor(x: Float, yValues: FloatArray, data: Any?) : this(x, calcSum(yValues), yValues, null, data)

    /** Constructor for stacked bar with icon */
    constructor(x: Float, yValues: FloatArray, icon: Drawable?) : this(x, calcSum(yValues), yValues, icon, null)

    /** Constructor for stacked bar with icon and data */
    constructor(x: Float, yValues: FloatArray, icon: Drawable?, data: Any?) : this(x, calcSum(yValues), yValues, icon, data)

    /**
     * Returns the sum of all values below the given stack index.
     */
    fun getSumBelow(stackIndex: Int): Float {
        if (yValues == null) return 0f

        var remainder = 0f
        var index = yValues.size - 1

        while (index > stackIndex && index >= 0) {
            remainder += yValues[index]
            index--
        }

        return remainder
    }

    private fun calculateNegativeSum(): Float {
        return yValues?.filter { it <= 0f }?.sumOf { abs(it).toDouble() }?.toFloat() ?: 0f
    }

    private fun calculatePositiveSum(): Float {
        return yValues?.filter { it > 0f }?.sum() ?: 0f
    }

    private fun calculateRanges(): List<Range> {
        val values = yValues ?: return emptyList()
        if (values.isEmpty()) return emptyList()

        val result = mutableListOf<Range>()
        var negRemain = -negativeSum
        var posRemain = 0f

        for (value in values) {
            if (value < 0) {
                result.add(Range(negRemain, negRemain - value))
                negRemain -= value
            } else {
                result.add(Range(posRemain, posRemain + value))
                posRemain += value
            }
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BarEntry) return false

        if (x != other.x) return false
        if (y != other.y) return false
        if (yValues != null) {
            if (other.yValues == null) return false
            if (!yValues.contentEquals(other.yValues)) return false
        } else if (other.yValues != null) return false
        if (icon != other.icon) return false
        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + (yValues?.contentHashCode() ?: 0)
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String = "BarEntry(x=$x, y=$y, isStacked=$isStacked)"

    companion object {
        private fun calcSum(values: FloatArray?): Float = values?.sum() ?: 0f
    }
}
