package com.xxmassdeveloper.mpchartexample.ui.screens

import android.graphics.Color
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
import com.github.mikephil.charting.charts.BubbleChart
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.xxmassdeveloper.mpchartexample.ui.components.ChartScaffold
import com.xxmassdeveloper.mpchartexample.ui.components.SliderWithLabel
import com.xxmassdeveloper.mpchartexample.ui.components.loadTypeface

@Composable
fun PieChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val tfLight = remember { loadTypeface(context, "OpenSans-Light.ttf") }

    ChartScaffold(
        title = "Pie Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                PieChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setUsePercentValues(true)
                    setExtraOffsets(5f, 10f, 5f, 5f)
                    dragDecelerationFrictionCoef = 0.95f
                    centerText = "MPAndroidChart"
                    setCenterTextTypeface(tfLight)
                    isDrawHoleEnabled = true
                    setHoleColor(Color.WHITE)
                    setTransparentCircleColor(Color.WHITE)
                    setTransparentCircleAlpha(110)
                    holeRadius = 58f
                    transparentCircleRadius = 61f
                    setDrawCenterText(true)
                    rotationAngle = 0f
                    isRotationEnabled = true
                    isHighlightPerTapEnabled = true

                    val entries = ArrayList<PieEntry>()
                    entries.add(PieEntry(40f, "Party A"))
                    entries.add(PieEntry(20f, "Party B"))
                    entries.add(PieEntry(15f, "Party C"))
                    entries.add(PieEntry(10f, "Party D"))
                    entries.add(PieEntry(15f, "Party E"))

                    val dataSet = PieDataSet(entries, "Election Results").apply {
                        setDrawIcons(false)
                        sliceSpace = 3f
                        selectionShift = 5f
                        colors = ColorTemplate.MATERIAL_COLORS.toList()
                    }

                    data = PieData(dataSet).apply {
                        setValueFormatter(PercentFormatter())
                        setValueTextSize(11f)
                        setValueTextColor(Color.WHITE)
                        setValueTypeface(tfLight)
                    }

                    legend.apply {
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                        orientation = Legend.LegendOrientation.VERTICAL
                        setDrawInside(false)
                        xEntrySpace = 7f
                        yEntrySpace = 0f
                        yOffset = 0f
                    }

                    setEntryLabelColor(Color.WHITE)
                    setEntryLabelTypeface(tfLight)
                    setEntryLabelTextSize(12f)

                    animateY(1400)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun PiePolylineChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val tfLight = remember { loadTypeface(context, "OpenSans-Light.ttf") }

    ChartScaffold(
        title = "Pie Chart with Polylines",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                PieChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setUsePercentValues(true)
                    setExtraOffsets(20f, 0f, 20f, 0f)
                    isDrawHoleEnabled = true
                    setHoleColor(Color.WHITE)
                    holeRadius = 58f
                    transparentCircleRadius = 61f

                    val entries = ArrayList<PieEntry>()
                    entries.add(PieEntry(45f, "Quarterly 1"))
                    entries.add(PieEntry(25f, "Quarterly 2"))
                    entries.add(PieEntry(20f, "Quarterly 3"))
                    entries.add(PieEntry(10f, "Quarterly 4"))

                    val dataSet = PieDataSet(entries, "Quarterly Revenue").apply {
                        sliceSpace = 3f
                        colors = listOf(
                            Color.rgb(255, 102, 0),
                            Color.rgb(255, 198, 0),
                            Color.rgb(104, 241, 175),
                            Color.rgb(164, 228, 251)
                        )
                        valueLinePart1OffsetPercentage = 80f
                        valueLinePart1Length = 0.3f
                        valueLinePart2Length = 0.4f
                        yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    }

                    data = PieData(dataSet).apply {
                        setValueFormatter(PercentFormatter())
                        setValueTextSize(11f)
                        setValueTextColor(Color.BLACK)
                    }

                    animateY(1400)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HalfPieChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val tfLight = remember { loadTypeface(context, "OpenSans-Light.ttf") }

    ChartScaffold(
        title = "Half Pie Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                PieChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setUsePercentValues(true)
                    isDrawHoleEnabled = true
                    setHoleColor(Color.WHITE)
                    holeRadius = 58f
                    transparentCircleRadius = 61f
                    setDrawCenterText(true)
                    centerText = "Half Pie"
                    setCenterTextTypeface(tfLight)
                    setCenterTextSize(22f)
                    maxAngle = 180f
                    rotationAngle = 180f

                    val entries = ArrayList<PieEntry>()
                    entries.add(PieEntry(25f))
                    entries.add(PieEntry(35f))
                    entries.add(PieEntry(40f))

                    val dataSet = PieDataSet(entries, "").apply {
                        sliceSpace = 3f
                        colors = listOf(
                            Color.rgb(255, 152, 0),
                            Color.rgb(76, 175, 80),
                            Color.rgb(33, 150, 243)
                        )
                        selectionShift = 5f
                    }

                    data = PieData(dataSet).apply {
                        setValueFormatter(PercentFormatter())
                        setValueTextSize(11f)
                        setValueTextColor(Color.WHITE)
                    }

                    animateY(1400)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ScatterChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(45f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Scatter Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    ScatterChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                        }

                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setScatterData(this, 45, 100f)
                        animateXY(1500, 1500)
                    }
                },
                update = { scatterChart ->
                    setScatterData(scatterChart, xValue.toInt(), yValue)
                    scatterChart.invalidate()
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

private fun setScatterData(chart: ScatterChart, count: Int, range: Float) {
    val values1 = ArrayList<Entry>()
    val values2 = ArrayList<Entry>()
    val values3 = ArrayList<Entry>()

    for (i in 0 until count) {
        values1.add(Entry(i.toFloat(), (Math.random() * range).toFloat() + range / 4))
        values2.add(Entry(i.toFloat() + 0.33f, (Math.random() * range).toFloat() + range / 4))
        values3.add(Entry(i.toFloat() + 0.66f, (Math.random() * range).toFloat() + range / 4))
    }

    val set1 = ScatterDataSet(values1, "DS 1").apply {
        setScatterShape(ScatterChart.ScatterShape.SQUARE)
        color = Color.rgb(255, 102, 0)
        scatterShapeSize = 10f
    }

    val set2 = ScatterDataSet(values2, "DS 2").apply {
        setScatterShape(ScatterChart.ScatterShape.CIRCLE)
        color = Color.rgb(104, 241, 175)
        scatterShapeSize = 10f
    }

    val set3 = ScatterDataSet(values3, "DS 3").apply {
        setScatterShape(ScatterChart.ScatterShape.CROSS)
        color = Color.rgb(164, 228, 251)
        scatterShapeSize = 10f
    }

    val dataSets = ArrayList<IScatterDataSet>()
    dataSets.add(set1)
    dataSets.add(set2)
    dataSets.add(set3)

    chart.data = ScatterData(dataSets)
}

@Composable
fun BubbleChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(20f) }
    var yValue by remember { mutableFloatStateOf(50f) }

    ChartScaffold(
        title = "Bubble Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    BubbleChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setPinchZoom(true)
                        setDrawGridBackground(false)

                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        setBubbleData(this, 20, 50f)
                        animateXY(1500, 1500)
                    }
                },
                update = { bubbleChart ->
                    setBubbleData(bubbleChart, xValue.toInt(), yValue)
                    bubbleChart.invalidate()
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

private fun setBubbleData(chart: BubbleChart, count: Int, range: Float) {
    val values1 = ArrayList<BubbleEntry>()
    val values2 = ArrayList<BubbleEntry>()
    val values3 = ArrayList<BubbleEntry>()

    for (i in 0 until count) {
        values1.add(BubbleEntry(i.toFloat(), (Math.random() * range).toFloat(), (Math.random() * range / 3).toFloat()))
        values2.add(BubbleEntry(i.toFloat(), (Math.random() * range).toFloat(), (Math.random() * range / 3).toFloat()))
        values3.add(BubbleEntry(i.toFloat(), (Math.random() * range).toFloat(), (Math.random() * range / 3).toFloat()))
    }

    val set1 = BubbleDataSet(values1, "DS 1").apply {
        setColors(Color.rgb(255, 102, 0), 130)
        setDrawValues(true)
    }

    val set2 = BubbleDataSet(values2, "DS 2").apply {
        setColors(Color.rgb(104, 241, 175), 130)
        setDrawValues(true)
    }

    val set3 = BubbleDataSet(values3, "DS 3").apply {
        setColors(Color.rgb(164, 228, 251), 130)
        setDrawValues(true)
    }

    chart.data = BubbleData(set1, set2, set3)
}

@Composable
fun CandlestickChartScreen(onBackClick: () -> Unit) {
    var xValue by remember { mutableFloatStateOf(40f) }
    var yValue by remember { mutableFloatStateOf(100f) }

    ChartScaffold(
        title = "Candlestick Chart",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    CandleStickChart(ctx).apply {
                        setBackgroundColor(Color.WHITE)
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                        }

                        axisLeft.apply {
                            setLabelCount(7, false)
                            setDrawGridLines(false)
                            setDrawAxisLine(false)
                        }

                        axisRight.isEnabled = false

                        setCandlestickData(this, 40, 100f)
                        animateXY(1500, 1500)
                    }
                },
                update = { candleChart ->
                    setCandlestickData(candleChart, xValue.toInt(), yValue)
                    candleChart.invalidate()
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
                    valueRange = 10f..100f
                )
                SliderWithLabel(
                    label = "Y",
                    value = yValue,
                    onValueChange = { yValue = it },
                    valueRange = 50f..200f
                )
            }
        }
    }
}

private fun setCandlestickData(chart: CandleStickChart, count: Int, range: Float) {
    val values = ArrayList<CandleEntry>()

    for (i in 0 until count) {
        val open = (Math.random() * 6 + 1).toFloat()
        val close = (Math.random() * 6 + 1).toFloat()
        val high = (Math.random() * 9 + 8).toFloat()
        val low = (Math.random() * 9).toFloat() / 2f

        values.add(
            CandleEntry(
                i.toFloat(),
                range / 2 + high,
                range / 2 - low,
                range / 2 + open,
                range / 2 - close
            )
        )
    }

    val set1 = CandleDataSet(values, "Data Set").apply {
        setDrawIcons(false)
        axisDependency = YAxis.AxisDependency.LEFT
        shadowColor = Color.DKGRAY
        shadowWidth = 0.7f
        decreasingColor = Color.rgb(255, 82, 82)
        decreasingPaintStyle = android.graphics.Paint.Style.FILL
        increasingColor = Color.rgb(76, 175, 80)
        increasingPaintStyle = android.graphics.Paint.Style.STROKE
        neutralColor = Color.BLUE
    }

    chart.data = CandleData(set1)
}

@Composable
fun RadarChartScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val tfLight = remember { loadTypeface(context, "OpenSans-Light.ttf") }

    ChartScaffold(
        title = "Radar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                RadarChart(ctx).apply {
                    setBackgroundColor(Color.rgb(60, 65, 82))
                    description.isEnabled = false
                    webLineWidth = 1f
                    webColor = Color.LTGRAY
                    webLineWidthInner = 1f
                    webColorInner = Color.LTGRAY
                    webAlpha = 100

                    val labels = arrayOf("Speed", "Power", "Defense", "Evasion", "Accuracy", "Endurance")

                    xAxis.apply {
                        typeface = tfLight
                        textSize = 9f
                        yOffset = 0f
                        xOffset = 0f
                        valueFormatter = com.github.mikephil.charting.formatter.IAxisValueFormatter { value, _ ->
                            labels[value.toInt() % labels.size]
                        }
                        textColor = Color.WHITE
                    }

                    yAxis.apply {
                        typeface = tfLight
                        setLabelCount(5, false)
                        textSize = 9f
                        axisMinimum = 0f
                        axisMaximum = 80f
                        setDrawLabels(false)
                    }

                    val entries1 = ArrayList<RadarEntry>()
                    val entries2 = ArrayList<RadarEntry>()
                    for (i in labels.indices) {
                        entries1.add(RadarEntry((Math.random() * 80).toFloat()))
                        entries2.add(RadarEntry((Math.random() * 80).toFloat()))
                    }

                    val set1 = RadarDataSet(entries1, "Character 1").apply {
                        color = Color.rgb(103, 110, 129)
                        fillColor = Color.rgb(103, 110, 129)
                        setDrawFilled(true)
                        fillAlpha = 180
                        lineWidth = 2f
                        isDrawHighlightCircleEnabled = true
                        setDrawHighlightIndicators(false)
                    }

                    val set2 = RadarDataSet(entries2, "Character 2").apply {
                        color = Color.rgb(121, 162, 175)
                        fillColor = Color.rgb(121, 162, 175)
                        setDrawFilled(true)
                        fillAlpha = 180
                        lineWidth = 2f
                        isDrawHighlightCircleEnabled = true
                        setDrawHighlightIndicators(false)
                    }

                    val dataSets = ArrayList<IRadarDataSet>()
                    dataSets.add(set1)
                    dataSets.add(set2)

                    data = RadarData(dataSets).apply {
                        setValueTypeface(tfLight)
                        setValueTextSize(8f)
                        setDrawValues(false)
                        setValueTextColor(Color.WHITE)
                    }

                    legend.apply {
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                        orientation = Legend.LegendOrientation.HORIZONTAL
                        setDrawInside(false)
                        typeface = tfLight
                        xEntrySpace = 7f
                        yEntrySpace = 5f
                        textColor = Color.WHITE
                    }

                    animateXY(1500, 1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CombinedChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Combined Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                CombinedChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    setDrawBarShadow(false)
                    isHighlightFullBarEnabled = false
                    drawOrder = arrayOf(
                        CombinedChart.DrawOrder.BAR,
                        CombinedChart.DrawOrder.LINE
                    )

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        axisMinimum = 0f
                        granularity = 1f
                    }

                    axisLeft.apply {
                        axisMinimum = 0f
                    }
                    axisRight.isEnabled = false

                    val lineEntries = ArrayList<Entry>()
                    val barEntries = ArrayList<BarEntry>()

                    for (i in 0 until 12) {
                        lineEntries.add(Entry(i.toFloat() + 0.5f, (Math.random() * 100).toFloat() + 25))
                        barEntries.add(BarEntry(i.toFloat() + 0.5f, (Math.random() * 80).toFloat() + 10))
                    }

                    val lineDataSet = LineDataSet(lineEntries, "Line DataSet").apply {
                        color = Color.rgb(255, 87, 34)
                        lineWidth = 2.5f
                        setCircleColor(Color.rgb(255, 87, 34))
                        circleRadius = 5f
                        fillColor = Color.rgb(255, 87, 34)
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        setDrawValues(true)
                        valueTextSize = 10f
                        valueTextColor = Color.rgb(255, 87, 34)
                        axisDependency = YAxis.AxisDependency.LEFT
                    }

                    val barDataSet = BarDataSet(barEntries, "Bar DataSet").apply {
                        color = Color.rgb(33, 150, 243)
                        valueTextColor = Color.rgb(33, 150, 243)
                        valueTextSize = 10f
                        axisDependency = YAxis.AxisDependency.LEFT
                    }

                    val combinedData = CombinedData()
                    combinedData.setData(LineData(lineDataSet))
                    combinedData.setData(BarData(barDataSet).apply { barWidth = 0.45f })

                    xAxis.axisMaximum = combinedData.xMax + 0.25f

                    data = combinedData

                    animateY(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
