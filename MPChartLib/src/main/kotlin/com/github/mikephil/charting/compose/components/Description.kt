package com.github.mikephil.charting.compose.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Configuration for the chart description label.
 *
 * @property text Description text to display
 * @property enabled Whether the description is drawn
 * @property textColor Color of the description text
 * @property textSize Size of the description text
 * @property fontFamily Font family for the description
 * @property textAlign Alignment of the text
 * @property position Custom position (null = default bottom-right)
 */
data class DescriptionConfig(
    val text: String = "",
    val enabled: Boolean = true,
    val textColor: Color = Color.Black,
    val textSize: TextUnit = 8.sp,
    val fontFamily: FontFamily? = null,
    val textAlign: TextAlign = TextAlign.End,
    val position: Offset? = null
)
