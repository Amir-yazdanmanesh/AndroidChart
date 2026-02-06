package com.github.mikephil.charting.compose.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.compose.data.ChartEntry

/**
 * Callback for formatting marker content.
 */
fun interface MarkerContentProvider {
    fun getContent(entry: ChartEntry, dataSetIndex: Int): String
}

/**
 * Configuration for the marker/tooltip shown on data point selection.
 *
 * @property enabled Whether the marker is shown on selection
 * @property backgroundColor Background color of the marker
 * @property textColor Text color inside the marker
 * @property textSize Text size inside the marker
 * @property fontFamily Font family for marker text
 * @property padding Padding inside the marker
 * @property cornerRadius Corner radius of the marker background
 * @property offset Offset from the selected data point
 * @property contentProvider Custom content formatter
 */
data class MarkerConfig(
    val enabled: Boolean = true,
    val backgroundColor: Color = Color(0xCC000000),
    val textColor: Color = Color.White,
    val textSize: TextUnit = 12.sp,
    val fontFamily: FontFamily? = null,
    val padding: Dp = 8.dp,
    val cornerRadius: Dp = 4.dp,
    val offset: Offset = Offset(0f, -10f),
    val contentProvider: MarkerContentProvider? = null
)
