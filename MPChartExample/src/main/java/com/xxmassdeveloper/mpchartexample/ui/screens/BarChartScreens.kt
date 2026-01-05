package com.xxmassdeveloper.mpchartexample.ui.screens

import android.graphics.Color
import android.graphics.RectF
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.xxmassdeveloper.mpchartexample.ui.components.ChartScaffold
import com.xxmassdeveloper.mpchartexample.ui.components.SliderWithLabel
import com.xxmassdeveloper.mpchartexample.ui.components.loadTypeface

@Composable
fun BarChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var xValue by remember { mutableFloatStateOf(12f) }
    var yValue by remember { mutableFloatStateOf(50f) }

    val tfLight = remember { loadTypeface(context, "OpenSans-Light.ttf") }

    ChartScaffold(
        title = "Bar Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(false)
                        setDrawGridBackground(false)
                        setDrawBarShadow(false)
                        setDrawValueAboveBar(true)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            typeface = tfLight
                            setDrawGridLines(false)
                            granularity = 1f
                        }

                        axisLeft.apply {
                            typeface = tfLight
                            axisMinimum = 0f
                        }
                        axisRight.isEnabled = false

                        legend.apply {
                            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                            orientation = Legend.LegendOrientation.HORIZONTAL
                            setDrawInside(false)
                            form = Legend.LegendForm.SQUARE
                            formSize = 9f
                            textSize = 11f
                            xEntrySpace = 4f
                        }

                        setBarChartData(this, 12, 50f)
                        animateY(1500)
                    }
                },
                update = { barChart ->
                    setBarChartData(barChart, xValue.toInt(), yValue)
                    barChart.invalidate()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SliderWithLabel(
                    label = "X",
                    value = xValue,
                    onValueChange = { xValue = it },
                    valueRange = 2f..50f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 0f..200f
                )
            }
        }
    }
}

private fun setBarChartData(chart: BarChart, count: Int, range: Float) {
    val values = ArrayList<BarEntry>()
    for (i in 0 until count) {
        values.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1: BarDataSet
    if (chart.data != null && chart.data.dataSetCount > 0) {
        set1 = chart.data.getDataSetByIndex(0) as BarDataSet
        set1.values = values
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
    } else {
        set1 = BarDataSet(values, "DataSet 1").apply {
            setColors(
                Color.rgb(104, 241, 175),
                Color.rgb(164, 228, 251),
                Color.rgb(242, 247, 158),
                Color.rgb(255, 102, 0),
                Color.rgb(255, 198, 255)
            )
        }

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        chart.data = BarData(dataSets).apply {
            setValueTextSize(10f)
            barWidth = 0.9f
        }
    }
}

@Composable
fun AnotherBarChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var xValue by remember { mutableFloatStateOf(10f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Bar Chart 2",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            granularity = 1f
                        }

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setAnotherBarData(this, 10, 100f)
                        animateY(1500)
                    }
                },
                update = { barChart ->
                    setAnotherBarData(barChart, xValue.toInt(), yValue)
                    barChart.invalidate()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SliderWithLabel(
                    label = "X",
                    value = xValue,
                    onValueChange = { xValue = it },
                    valueRange = 2f..50f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 0f..200f
                )
            }
        }
    }
}

private fun setAnotherBarData(chart: BarChart, count: Int, range: Float) {
    val values = ArrayList<BarEntry>()
    for (i in 0 until count) {
        values.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = BarDataSet(values, "DataSet").apply {
        color = Color.rgb(0, 150, 136)
        valueTextColor = Color.rgb(0, 150, 136)
        valueTextSize = 10f
        setDrawValues(true)
    }

    chart.data = BarData(set1).apply {
        barWidth = 0.8f
    }
}

@Composable
fun MultiBarChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(10f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Multi Bar Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setPinchZoom(false)
                        setDrawGridBackground(false)
                        setDrawBarShadow(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            axisMinimum = 0f
                            granularity = 1f
                            setCenterAxisLabels(true)
                        }

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setMultiBarData(this, 10, 100f)
                        animateY(1500)

                        groupBars(0f, 0.08f, 0.03f)
                    }
                },
                update = { barChart ->
                    setMultiBarData(barChart, xValue.toInt(), yValue)
                    barChart.groupBars(0f, 0.08f, 0.03f)
                    barChart.invalidate()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SliderWithLabel(
                    label = "X",
                    value = xValue,
                    onValueChange = { xValue = it },
                    valueRange = 2f..50f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 0f..200f
                )
            }
        }
    }
}

private fun setMultiBarData(chart: BarChart, count: Int, range: Float) {
    val values1 = ArrayList<BarEntry>()
    val values2 = ArrayList<BarEntry>()
    val values3 = ArrayList<BarEntry>()

    for (i in 0 until count) {
        values1.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
        values2.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
        values3.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = BarDataSet(values1, "Company A").apply { color = Color.rgb(104, 241, 175) }
    val set2 = BarDataSet(values2, "Company B").apply { color = Color.rgb(164, 228, 251) }
    val set3 = BarDataSet(values3, "Company C").apply { color = Color.rgb(242, 247, 158) }

    chart.data = BarData(set1, set2, set3).apply {
        barWidth = 0.25f
    }
    chart.xAxis.axisMaximum = count.toFloat()
}

@Composable
fun HorizontalBarChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(12f) }
    var yValue by remember { mutableFloatStateOf(50f) }

    ChartScaffold(
        title = "Horizontal Bar Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    HorizontalBarChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawAxisLine(true)
                            setDrawGridLines(false)
                            granularity = 1f
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                        }
                        axisRight.isEnabled = false

                        setHorizontalBarData(this, 12, 50f)
                        animateY(1500)
                    }
                },
                update = { barChart ->
                    setHorizontalBarData(barChart, xValue.toInt(), yValue)
                    barChart.invalidate()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SliderWithLabel(
                    label = "X",
                    value = xValue,
                    onValueChange = { xValue = it },
                    valueRange = 2f..50f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 0f..200f
                )
            }
        }
    }
}

private fun setHorizontalBarData(chart: HorizontalBarChart, count: Int, range: Float) {
    val values = ArrayList<BarEntry>()
    for (i in 0 until count) {
        values.add(BarEntry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = BarDataSet(values, "DataSet 1").apply {
        setColors(
            Color.rgb(255, 102, 0),
            Color.rgb(255, 198, 0),
            Color.rgb(104, 241, 175),
            Color.rgb(164, 228, 251)
        )
    }

    chart.data = BarData(set1).apply {
        setValueTextSize(10f)
        barWidth = 0.9f
    }
}

@Composable
fun StackedBarChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(12f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Stacked Bar Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setDrawGridBackground(false)
                        setDrawBarShadow(false)
                        setDrawValueAboveBar(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                            setDrawGridLines(false)
                        }

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setStackedBarData(this, 12, 100f)
                        animateY(1500)
                    }
                },
                update = { barChart ->
                    setStackedBarData(barChart, xValue.toInt(), yValue)
                    barChart.invalidate()
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SliderWithLabel(
                    label = "X",
                    value = xValue,
                    onValueChange = { xValue = it },
                    valueRange = 2f..50f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 0f..200f
                )
            }
        }
    }
}

private fun setStackedBarData(chart: BarChart, count: Int, range: Float) {
    val values = ArrayList<BarEntry>()
    for (i in 0 until count) {
        val val1 = (Math.random() * range / 4).toFloat()
        val val2 = (Math.random() * range / 4).toFloat()
        val val3 = (Math.random() * range / 4).toFloat()
        values.add(BarEntry(i.toFloat(), floatArrayOf(val1, val2, val3)))
    }

    val set1 = BarDataSet(values, "").apply {
        setColors(
            Color.rgb(255, 152, 0),
            Color.rgb(76, 175, 80),
            Color.rgb(33, 150, 243)
        )
        stackLabels = arrayOf("Stack 1", "Stack 2", "Stack 3")
    }

    chart.data = BarData(set1).apply {
        setValueTextSize(10f)
        setValueTextColor(Color.WHITE)
        barWidth = 0.9f
    }
}

@Composable
fun PositiveNegativeBarChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Positive/Negative Bar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                BarChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    setDrawBarShadow(false)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                    }

                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawZeroLine(true)
                    }
                    axisRight.isEnabled = false

                    val values = ArrayList<BarEntry>()
                    val colors = ArrayList<Int>()

                    val green = Color.rgb(76, 175, 80)
                    val red = Color.rgb(255, 82, 82)

                    for (i in 0 until 25) {
                        val value = (Math.random() * 100 - 50).toFloat()
                        values.add(BarEntry(i.toFloat(), value))
                        colors.add(if (value >= 0) green else red)
                    }

                    val set = BarDataSet(values, "Values").apply {
                        setColors(colors)
                        valueTextSize = 9f
                    }

                    data = BarData(set).apply {
                        barWidth = 0.8f
                    }

                    animateY(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HorizontalNegativeBarChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Horizontal Negative Bar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                HorizontalBarChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setDrawGridBackground(false)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        setDrawAxisLine(false)
                    }

                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawZeroLine(true)
                    }
                    axisRight.isEnabled = false

                    val values = ArrayList<BarEntry>()
                    val colors = ArrayList<Int>()

                    for (i in 0 until 15) {
                        val value = (Math.random() * 100 - 50).toFloat()
                        values.add(BarEntry(i.toFloat(), value))
                        colors.add(if (value >= 0) Color.rgb(76, 175, 80) else Color.rgb(255, 82, 82))
                    }

                    val set = BarDataSet(values, "Values").apply {
                        setColors(colors)
                        valueTextSize = 9f
                    }

                    data = BarData(set).apply {
                        barWidth = 0.8f
                    }

                    animateY(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun StackedNegativeBarChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Stacked Negative Bar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                BarChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    setDrawBarShadow(false)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                    }

                    axisLeft.apply {
                        setDrawGridLines(false)
                        setDrawZeroLine(true)
                    }
                    axisRight.isEnabled = false

                    val values = ArrayList<BarEntry>()
                    for (i in 0 until 12) {
                        val val1 = (Math.random() * 50).toFloat()
                        val val2 = -(Math.random() * 50).toFloat()
                        val val3 = (Math.random() * 30).toFloat()
                        values.add(BarEntry(i.toFloat(), floatArrayOf(val1, val2, val3)))
                    }

                    val set = BarDataSet(values, "").apply {
                        setColors(
                            Color.rgb(76, 175, 80),
                            Color.rgb(255, 82, 82),
                            Color.rgb(33, 150, 243)
                        )
                        stackLabels = arrayOf("Positive", "Negative", "Neutral")
                    }

                    data = BarData(set).apply {
                        setValueTextSize(10f)
                        barWidth = 0.8f
                    }

                    animateY(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SineBarChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Sine Bar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                BarChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    setDrawBarShadow(false)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                    }

                    axisLeft.apply {
                        axisMinimum = -1.2f
                        axisMaximum = 1.2f
                    }
                    axisRight.isEnabled = false

                    val values = ArrayList<BarEntry>()
                    val colors = ArrayList<Int>()

                    for (i in 0 until 150) {
                        val value = Math.sin(i * 0.1).toFloat()
                        values.add(BarEntry(i.toFloat(), value))
                        colors.add(if (value >= 0) Color.rgb(76, 175, 80) else Color.rgb(255, 82, 82))
                    }

                    val set = BarDataSet(values, "Sine").apply {
                        setColors(colors)
                        setDrawValues(false)
                    }

                    data = BarData(set).apply {
                        barWidth = 0.9f
                    }

                    setVisibleXRangeMaximum(50f)
                    invalidate()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
