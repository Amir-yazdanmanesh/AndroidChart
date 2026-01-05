package com.xxmassdeveloper.mpchartexample.ui.screens

import android.graphics.Color
import android.graphics.DashPathEffect
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
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.xxmassdeveloper.mpchartexample.R
import com.xxmassdeveloper.mpchartexample.ui.components.ChartScaffold
import com.xxmassdeveloper.mpchartexample.ui.components.SliderWithLabel
import com.xxmassdeveloper.mpchartexample.ui.components.loadTypeface

@Composable
fun LineChart1Screen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var xValue by remember { mutableFloatStateOf(45f) }
    var yValue by remember { mutableFloatStateOf(180f) }
    var chart by remember { mutableFloatStateOf(0f) }

    val tfRegular = remember { loadTypeface(context, "OpenSans-Regular.ttf") }

    ChartScaffold(
        title = "Line Chart 1",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        setDrawGridBackground(false)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)

                        xAxis.enableGridDashedLine(10f, 10f, 0f)

                        val yAxis = axisLeft
                        axisRight.isEnabled = false
                        yAxis.enableGridDashedLine(10f, 10f, 0f)
                        yAxis.axisMaximum = 200f
                        yAxis.axisMinimum = -50f

                        val ll1 = LimitLine(150f, "Upper Limit").apply {
                            lineWidth = 4f
                            enableDashedLine(10f, 10f, 0f)
                            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                            textSize = 10f
                            typeface = tfRegular
                        }

                        val ll2 = LimitLine(-30f, "Lower Limit").apply {
                            lineWidth = 4f
                            enableDashedLine(10f, 10f, 0f)
                            labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
                            textSize = 10f
                            typeface = tfRegular
                        }

                        yAxis.setDrawLimitLinesBehindData(true)
                        xAxis.setDrawLimitLinesBehindData(true)
                        yAxis.addLimitLine(ll1)
                        yAxis.addLimitLine(ll2)

                        setLineChartData(this, 45, 180f, ctx)
                        animateX(1500)

                        legend.form = Legend.LegendForm.LINE
                    }
                },
                update = { lineChart ->
                    setLineChartData(lineChart, xValue.toInt(), yValue, context)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setLineChartData(chart: LineChart, count: Int, range: Float, context: android.content.Context) {
    val values = ArrayList<Entry>()
    for (i in 0 until count) {
        val value = (Math.random() * range).toFloat() - 30
        values.add(Entry(i.toFloat(), value, ContextCompat.getDrawable(context, R.drawable.star)))
    }

    val set1: LineDataSet
    if (chart.data != null && chart.data.dataSetCount > 0) {
        set1 = chart.data.getDataSetByIndex(0) as LineDataSet
        set1.values = values
        set1.notifyDataSetChanged()
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
    } else {
        set1 = LineDataSet(values, "DataSet 1").apply {
            setDrawIcons(false)
            enableDashedLine(10f, 5f, 0f)
            color = Color.BLACK
            setCircleColor(Color.BLACK)
            lineWidth = 1f
            circleRadius = 3f
            setDrawCircleHole(false)
            formLineWidth = 1f
            formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            formSize = 15f
            valueTextSize = 9f
            enableDashedHighlightLine(10f, 5f, 0f)
            setDrawFilled(true)
            fillFormatter = IFillFormatter { _, dataProvider ->
                dataProvider.yChartMin
            }
            if (Utils.getSDKInt() >= 18) {
                fillDrawable = ContextCompat.getDrawable(context, R.drawable.fade_red)
            } else {
                fillColor = Color.BLACK
            }
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        chart.data = LineData(dataSets)
    }
}

@Composable
fun LineChart2Screen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var xValue by remember { mutableFloatStateOf(20f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Dual Axis Line Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(false)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            axisMinimum = 0f
                            granularity = 1f
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                        }

                        axisRight.apply {
                            isEnabled = true
                            axisMinimum = 0f
                        }

                        setDualAxisData(this, 20, 100f)
                        animateX(1500)
                    }
                },
                update = { lineChart ->
                    setDualAxisData(lineChart, xValue.toInt(), yValue)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setDualAxisData(chart: LineChart, count: Int, range: Float) {
    val values1 = ArrayList<Entry>()
    val values2 = ArrayList<Entry>()

    for (i in 0 until count) {
        values1.add(Entry(i.toFloat(), (Math.random() * range).toFloat()))
        values2.add(Entry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = LineDataSet(values1, "DataSet 1").apply {
        axisDependency = YAxis.AxisDependency.LEFT
        color = Color.rgb(255, 117, 117)
        setCircleColor(Color.WHITE)
        lineWidth = 2f
        circleRadius = 3f
        fillAlpha = 65
        fillColor = Color.rgb(255, 117, 117)
        highLightColor = Color.rgb(244, 117, 117)
        setDrawCircleHole(false)
    }

    val set2 = LineDataSet(values2, "DataSet 2").apply {
        axisDependency = YAxis.AxisDependency.RIGHT
        color = Color.rgb(76, 175, 80)
        setCircleColor(Color.WHITE)
        lineWidth = 2f
        circleRadius = 3f
        fillAlpha = 65
        fillColor = Color.rgb(76, 175, 80)
        setDrawCircleHole(false)
        highLightColor = Color.rgb(76, 175, 80)
    }

    val dataSets = ArrayList<ILineDataSet>()
    dataSets.add(set1)
    dataSets.add(set2)

    chart.data = LineData(dataSets)
}

@Composable
fun MultiLineChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(20f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Multi Line Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            granularity = 1f
                        }

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setMultiLineData(this, 20, 100f)
                        animateX(1500)

                        legend.apply {
                            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                            orientation = Legend.LegendOrientation.HORIZONTAL
                            setDrawInside(false)
                        }
                    }
                },
                update = { lineChart ->
                    setMultiLineData(lineChart, xValue.toInt(), yValue)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setMultiLineData(chart: LineChart, count: Int, range: Float) {
    val colors = listOf(
        Color.rgb(255, 102, 0),
        Color.rgb(0, 153, 255),
        Color.rgb(0, 204, 102)
    )

    val dataSets = ArrayList<ILineDataSet>()

    for (z in 0..2) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            values.add(Entry(i.toFloat(), (Math.random() * range).toFloat() + 3))
        }

        val set = LineDataSet(values, "DataSet ${z + 1}").apply {
            color = colors[z]
            lineWidth = 2.5f
            setCircleColor(colors[z])
            circleRadius = 5f
            fillColor = colors[z]
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(true)
            valueTextSize = 10f
            valueTextColor = colors[z]
        }
        dataSets.add(set)
    }

    chart.data = LineData(dataSets)
}

@Composable
fun InvertedLineChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(25f) }
    var yValue by remember { mutableFloatStateOf(50f) }

    ChartScaffold(
        title = "Inverted Line Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        axisLeft.apply {
                            isInverted = true
                            axisMinimum = 0f
                        }
                        axisRight.isEnabled = false

                        setInvertedLineData(this, 25, 50f)
                        animateX(1500)
                    }
                },
                update = { lineChart ->
                    setInvertedLineData(lineChart, xValue.toInt(), yValue)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setInvertedLineData(chart: LineChart, count: Int, range: Float) {
    val values = ArrayList<Entry>()
    for (i in 0 until count) {
        values.add(Entry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = LineDataSet(values, "DataSet 1").apply {
        color = Color.rgb(255, 87, 34)
        setCircleColor(Color.rgb(255, 87, 34))
        lineWidth = 2f
        circleRadius = 4f
        fillAlpha = 65
        fillColor = Color.rgb(255, 87, 34)
        setDrawFilled(true)
        mode = LineDataSet.Mode.CUBIC_BEZIER
    }

    chart.data = LineData(set1)
}

@Composable
fun CubicLineChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(45f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Cubic Line Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false
                        xAxis.position = XAxis.XAxisPosition.BOTTOM

                        setCubicLineData(this, 45, 100f)
                        animateXY(2000, 2000, Easing.EaseInOutQuad)
                    }
                },
                update = { lineChart ->
                    setCubicLineData(lineChart, xValue.toInt(), yValue)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setCubicLineData(chart: LineChart, count: Int, range: Float) {
    val values = ArrayList<Entry>()
    for (i in 0 until count) {
        values.add(Entry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = LineDataSet(values, "DataSet 1").apply {
        mode = LineDataSet.Mode.CUBIC_BEZIER
        cubicIntensity = 0.2f
        setDrawFilled(true)
        setDrawCircles(false)
        lineWidth = 1.8f
        circleRadius = 4f
        setCircleColor(Color.WHITE)
        highLightColor = Color.rgb(244, 117, 117)
        color = Color.rgb(104, 241, 175)
        fillColor = Color.rgb(104, 241, 175)
        fillAlpha = 100
        setDrawHorizontalHighlightIndicator(false)
    }

    chart.data = LineData(set1)
}

@Composable
fun ColoredLineChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Colored Line Charts",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val colors = listOf(
                Color.rgb(137, 230, 81) to Color.rgb(240, 240, 30),
                Color.rgb(89, 199, 250) to Color.rgb(89, 199, 250),
                Color.rgb(250, 104, 104) to Color.rgb(250, 104, 104),
                Color.rgb(217, 80, 138) to Color.rgb(217, 80, 138)
            )

            colors.forEach { (lineColor, fillColor) ->
                AndroidView(
                    factory = { ctx ->
                        LineChart(ctx).apply {
                            setBackgroundColor(Color.rgb(44, 48, 52))
                            description.isEnabled = false
                            setTouchEnabled(true)
                            isDragEnabled = true
                            setScaleEnabled(true)
                            setDrawGridBackground(false)

                            axisLeft.apply {
                                axisMinimum = 0f
                                setDrawGridLines(false)
                                setDrawAxisLine(false)
                                textColor = Color.WHITE
                            }
                            axisRight.isEnabled = false
                            xAxis.apply {
                                position = XAxis.XAxisPosition.BOTTOM
                                setDrawGridLines(false)
                                setDrawAxisLine(false)
                                textColor = Color.WHITE
                            }
                            legend.isEnabled = false

                            val values = ArrayList<Entry>()
                            for (i in 0 until 12) {
                                values.add(Entry(i.toFloat(), (Math.random() * 65).toFloat() + 40))
                            }

                            val set = LineDataSet(values, "DataSet").apply {
                                mode = LineDataSet.Mode.CUBIC_BEZIER
                                cubicIntensity = 0.2f
                                setDrawFilled(true)
                                setDrawCircles(false)
                                lineWidth = 1.8f
                                color = lineColor
                                this.fillColor = fillColor
                                fillAlpha = 100
                            }

                            data = LineData(set)
                            animateXY(2000, 2000)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PerformanceLineChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Performance Line Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                LineChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setTouchEnabled(true)
                    isDragEnabled = true
                    setScaleEnabled(true)
                    setPinchZoom(true)
                    setDrawGridBackground(false)

                    axisLeft.axisMinimum = 0f
                    axisRight.isEnabled = false

                    val values = ArrayList<Entry>()
                    for (i in 0 until 30000) {
                        values.add(Entry(i.toFloat(), (Math.random() * 100).toFloat()))
                    }

                    val set = LineDataSet(values, "30,000 Data Points").apply {
                        setDrawCircles(false)
                        setDrawValues(false)
                        lineWidth = 1f
                        color = Color.rgb(33, 150, 243)
                    }

                    data = LineData(set)
                    invalidate()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FilledLineChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(45f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Filled Line Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    LineChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setFilledLineData(this, 45, 100f)
                        animateX(1500)
                    }
                },
                update = { lineChart ->
                    setFilledLineData(lineChart, xValue.toInt(), yValue)
                    lineChart.invalidate()
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
                    valueRange = 0f..500f
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

private fun setFilledLineData(chart: LineChart, count: Int, range: Float) {
    val values1 = ArrayList<Entry>()
    val values2 = ArrayList<Entry>()

    for (i in 0 until count) {
        values1.add(Entry(i.toFloat(), (Math.random() * range).toFloat() + range / 2))
        values2.add(Entry(i.toFloat(), (Math.random() * range).toFloat()))
    }

    val set1 = LineDataSet(values1, "DataSet 1").apply {
        setDrawCircles(false)
        setDrawFilled(true)
        fillColor = Color.rgb(255, 152, 0)
        fillAlpha = 80
        color = Color.rgb(255, 152, 0)
        lineWidth = 2f
    }

    val set2 = LineDataSet(values2, "DataSet 2").apply {
        setDrawCircles(false)
        setDrawFilled(true)
        fillColor = Color.rgb(76, 175, 80)
        fillAlpha = 80
        color = Color.rgb(76, 175, 80)
        lineWidth = 2f
    }

    val dataSets = ArrayList<ILineDataSet>()
    dataSets.add(set1)
    dataSets.add(set2)

    chart.data = LineData(dataSets)
}
