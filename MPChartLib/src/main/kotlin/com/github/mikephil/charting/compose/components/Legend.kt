package com.github.mikephil.charting.compose.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Shape used for legend form indicators.
 */
enum class LegendForm {
    NONE,
    EMPTY,
    DEFAULT,
    SQUARE,
    CIRCLE,
    LINE
}

/**
 * Horizontal alignment of the legend.
 */
enum class LegendHorizontalAlignment {
    LEFT,
    CENTER,
    RIGHT
}

/**
 * Vertical alignment of the legend.
 */
enum class LegendVerticalAlignment {
    TOP,
    CENTER,
    BOTTOM
}

/**
 * Orientation/layout direction of legend entries.
 */
enum class LegendOrientation {
    HORIZONTAL,
    VERTICAL
}

/**
 * Text direction for legend entries.
 */
enum class LegendDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT
}

/**
 * A single entry in the chart legend.
 *
 * @property label Text of this legend entry (null starts a group)
 * @property form Shape to draw for this entry
 * @property formColor Color of the form
 * @property formSize Size of the form (NaN = use legend default)
 * @property formLineWidth Line width for line forms (NaN = use legend default)
 * @property formPathEffect Dash effect for line forms
 */
data class LegendEntryConfig(
    val label: String? = null,
    val form: LegendForm = LegendForm.DEFAULT,
    val formColor: Color = Color.Unspecified,
    val formSize: Float = Float.NaN,
    val formLineWidth: Float = Float.NaN,
    val formPathEffect: PathEffect? = null
)

/**
 * Configuration for the chart legend.
 *
 * @property enabled Whether the legend is drawn
 * @property entries Custom legend entries (empty = auto-calculated)
 * @property extraEntries Additional entries appended after auto-calculated ones
 * @property horizontalAlignment Horizontal alignment of the legend
 * @property verticalAlignment Vertical alignment of the legend
 * @property orientation Layout orientation (horizontal/vertical)
 * @property direction Text direction
 * @property drawInside Whether to draw the legend inside the chart
 * @property form Default shape for legend entries
 * @property formSize Default size of legend forms
 * @property formLineWidth Default line width for line forms
 * @property formPathEffect Default dash effect for line forms
 * @property xEntrySpace Horizontal space between entries
 * @property yEntrySpace Vertical space between entries
 * @property formToTextSpace Space between form and label text
 * @property stackSpace Space between stacked forms
 * @property textColor Color of label text
 * @property textSize Size of label text
 * @property fontFamily Font family for labels
 * @property wordWrapEnabled Whether to wrap text when it doesn't fit
 * @property maxSizePercent Maximum size relative to chart (0-1)
 */
data class LegendConfig(
    val enabled: Boolean = true,
    val entries: List<LegendEntryConfig> = emptyList(),
    val extraEntries: List<LegendEntryConfig> = emptyList(),
    val horizontalAlignment: LegendHorizontalAlignment = LegendHorizontalAlignment.LEFT,
    val verticalAlignment: LegendVerticalAlignment = LegendVerticalAlignment.BOTTOM,
    val orientation: LegendOrientation = LegendOrientation.HORIZONTAL,
    val direction: LegendDirection = LegendDirection.LEFT_TO_RIGHT,
    val drawInside: Boolean = false,
    val form: LegendForm = LegendForm.SQUARE,
    val formSize: Dp = 8.dp,
    val formLineWidth: Dp = 3.dp,
    val formPathEffect: PathEffect? = null,
    val xEntrySpace: Dp = 6.dp,
    val yEntrySpace: Dp = 0.dp,
    val formToTextSpace: Dp = 5.dp,
    val stackSpace: Dp = 3.dp,
    val textColor: Color = Color.Black,
    val textSize: TextUnit = 10.sp,
    val fontFamily: FontFamily? = null,
    val wordWrapEnabled: Boolean = false,
    val maxSizePercent: Float = 0.95f
) {
    /** Whether custom entries have been set */
    val isCustom: Boolean get() = entries.isNotEmpty()

    fun withCustomEntries(entries: List<LegendEntryConfig>): LegendConfig =
        copy(entries = entries)

    fun withExtraEntries(extras: List<LegendEntryConfig>): LegendConfig =
        copy(extraEntries = extras)
}
