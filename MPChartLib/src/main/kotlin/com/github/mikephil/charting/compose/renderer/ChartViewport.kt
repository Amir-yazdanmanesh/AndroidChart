package com.github.mikephil.charting.compose.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines the insets/padding around the chart content area.
 */
data class ChartInsets(
    val left: Dp = 0.dp,
    val top: Dp = 0.dp,
    val right: Dp = 0.dp,
    val bottom: Dp = 0.dp
)

/**
 * Manages the viewport for chart rendering - maps data coordinates to pixel coordinates.
 *
 * The viewport represents the visible window into the chart data space.
 * It handles coordinate transformations between data values and screen pixels.
 *
 * @property chartSize Total size of the chart canvas in pixels
 * @property contentRect The area where chart data is drawn (inside padding/axis labels)
 * @property dataXMin Minimum X value in data space
 * @property dataXMax Maximum X value in data space
 * @property dataYMin Minimum Y value in data space
 * @property dataYMax Maximum Y value in data space
 */
data class ChartViewport(
    val chartSize: Size,
    val contentRect: Rect,
    val dataXMin: Float,
    val dataXMax: Float,
    val dataYMin: Float,
    val dataYMax: Float
) {
    /** Width of the content area in pixels */
    val contentWidth: Float get() = contentRect.width

    /** Height of the content area in pixels */
    val contentHeight: Float get() = contentRect.height

    /** Data range on X axis */
    val dataXRange: Float get() = (dataXMax - dataXMin).let { if (it == 0f) 1f else it }

    /** Data range on Y axis */
    val dataYRange: Float get() = (dataYMax - dataYMin).let { if (it == 0f) 1f else it }

    /** Scale factor: pixels per data unit on X axis */
    val scaleX: Float get() = contentWidth / dataXRange

    /** Scale factor: pixels per data unit on Y axis */
    val scaleY: Float get() = contentHeight / dataYRange

    /**
     * Convert a data X value to pixel X coordinate.
     */
    fun dataToPixelX(dataX: Float): Float {
        return contentRect.left + (dataX - dataXMin) * scaleX
    }

    /**
     * Convert a data Y value to pixel Y coordinate.
     * Note: Y axis is inverted (higher values at top = lower pixel Y).
     */
    fun dataToPixelY(dataY: Float): Float {
        return contentRect.bottom - (dataY - dataYMin) * scaleY
    }

    /**
     * Convert a data point to pixel offset.
     */
    fun dataToPixel(dataX: Float, dataY: Float): Offset {
        return Offset(dataToPixelX(dataX), dataToPixelY(dataY))
    }

    /**
     * Convert pixel X to data X value.
     */
    fun pixelToDataX(pixelX: Float): Float {
        return dataXMin + (pixelX - contentRect.left) / scaleX
    }

    /**
     * Convert pixel Y to data Y value.
     */
    fun pixelToDataY(pixelY: Float): Float {
        return dataYMin + (contentRect.bottom - pixelY) / scaleY
    }

    /**
     * Check if a pixel X coordinate is within the content bounds.
     */
    fun isInBoundsX(pixelX: Float): Boolean {
        return pixelX >= contentRect.left && pixelX <= contentRect.right
    }

    /**
     * Check if a pixel Y coordinate is within the content bounds.
     */
    fun isInBoundsY(pixelY: Float): Boolean {
        return pixelY >= contentRect.top && pixelY <= contentRect.bottom
    }

    /**
     * Check if a pixel offset is within the content bounds.
     */
    fun isInBounds(offset: Offset): Boolean {
        return isInBoundsX(offset.x) && isInBoundsY(offset.y)
    }

    companion object {
        /**
         * Creates a viewport that fills the entire canvas with optional insets.
         */
        fun create(
            canvasSize: Size,
            leftInset: Float = 0f,
            topInset: Float = 0f,
            rightInset: Float = 0f,
            bottomInset: Float = 0f,
            dataXMin: Float = 0f,
            dataXMax: Float = 1f,
            dataYMin: Float = 0f,
            dataYMax: Float = 1f
        ): ChartViewport {
            return ChartViewport(
                chartSize = canvasSize,
                contentRect = Rect(
                    left = leftInset,
                    top = topInset,
                    right = canvasSize.width - rightInset,
                    bottom = canvasSize.height - bottomInset
                ),
                dataXMin = dataXMin,
                dataXMax = dataXMax,
                dataYMin = dataYMin,
                dataYMax = dataYMax
            )
        }
    }
}
