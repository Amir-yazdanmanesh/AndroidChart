package com.github.mikephil.charting.compose.data

/**
 * Represents a range of values in a stacked bar entry.
 * e.g., stack values are -10, 5, 20 -> ranges are (-10 - 0, 0 - 5, 5 - 25).
 */
data class Range(
    val from: Float,
    val to: Float
) {
    /**
     * Returns true if this range contains the given value (value is between from and to).
     */
    operator fun contains(value: Float): Boolean = value > from && value <= to

    /**
     * Returns true if the value is larger than the upper bound of this range.
     */
    fun isLarger(value: Float): Boolean = value > to

    /**
     * Returns true if the value is smaller than the lower bound of this range.
     */
    fun isSmaller(value: Float): Boolean = value < from
}
