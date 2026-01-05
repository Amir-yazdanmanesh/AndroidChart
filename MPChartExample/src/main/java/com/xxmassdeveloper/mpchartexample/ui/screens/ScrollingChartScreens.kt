package com.xxmassdeveloper.mpchartexample.ui.screens

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.xxmassdeveloper.mpchartexample.ui.components.ChartScaffold
import com.xxmassdeveloper.mpchartexample.ui.components.SliderWithLabel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListViewMultiChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "ListView Multi Charts",
        onBackClick = onBackClick
    ) {
        val chartTypes = listOf("Line", "Bar", "Scatter", "Pie", "Line", "Bar", "Scatter", "Pie")

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chartTypes) { chartType ->
                when (chartType) {
                    "Line" -> LineChartListItem()
                    "Bar" -> BarChartListItem()
                    "Scatter" -> ScatterChartListItem()
                    "Pie" -> PieChartListItem()
                }
            }
        }
    }
}

@Composable
private fun LineChartListItem() {
    AndroidView(
        factory = { ctx ->
            LineChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                setDrawGridBackground(false)
                isDragEnabled = true
                setScaleEnabled(true)

                val entries = ArrayList<Entry>()
                for (i in 0 until 20) {
                    entries.add(Entry(i.toFloat(), (Math.random() * 50).toFloat()))
                }

                val dataSet = LineDataSet(entries, "Line Data").apply {
                    color = Color.rgb(33, 150, 243)
                    setCircleColor(Color.rgb(33, 150, 243))
                    lineWidth = 2f
                    circleRadius = 4f
                }

                data = LineData(dataSet)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}

@Composable
private fun BarChartListItem() {
    AndroidView(
        factory = { ctx ->
            BarChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                setDrawGridBackground(false)

                val entries = ArrayList<BarEntry>()
                for (i in 0 until 10) {
                    entries.add(BarEntry(i.toFloat(), (Math.random() * 50).toFloat()))
                }

                val dataSet = BarDataSet(entries, "Bar Data").apply {
                    color = Color.rgb(76, 175, 80)
                }

                data = BarData(dataSet).apply { barWidth = 0.8f }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}

@Composable
private fun ScatterChartListItem() {
    AndroidView(
        factory = { ctx ->
            ScatterChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                setDrawGridBackground(false)

                val entries = ArrayList<Entry>()
                for (i in 0 until 20) {
                    entries.add(Entry(i.toFloat(), (Math.random() * 50).toFloat()))
                }

                val dataSet = ScatterDataSet(entries, "Scatter Data").apply {
                    color = Color.rgb(255, 152, 0)
                    setScatterShape(ScatterChart.ScatterShape.CIRCLE)
                    scatterShapeSize = 10f
                }

                data = ScatterData(dataSet)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}

@Composable
private fun PieChartListItem() {
    AndroidView(
        factory = { ctx ->
            PieChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setUsePercentValues(true)
                isDrawHoleEnabled = true
                holeRadius = 40f

                val entries = ArrayList<PieEntry>()
                entries.add(PieEntry(40f, "A"))
                entries.add(PieEntry(30f, "B"))
                entries.add(PieEntry(30f, "C"))

                val dataSet = PieDataSet(entries, "Pie Data").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                    sliceSpace = 2f
                }

                data = PieData(dataSet)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    )
}

@Composable
fun SimpleChartDemoScreen(onBackClick: () -> Unit) {
    val tabs = listOf("Line", "Bar", "Scatter", "Pie")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    ChartScaffold(
        title = "Chart Demo",
        onBackClick = onBackClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> SimpleLineChart()
                    1 -> SimpleBarChart()
                    2 -> SimpleScatterChart()
                    3 -> SimplePieChart()
                }
            }
        }
    }
}

@Composable
private fun SimpleLineChart() {
    AndroidView(
        factory = { ctx ->
            LineChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)

                val entries = ArrayList<Entry>()
                for (i in 0 until 30) {
                    entries.add(Entry(i.toFloat(), (Math.random() * 100).toFloat()))
                }

                val dataSet = LineDataSet(entries, "Line Data").apply {
                    color = Color.rgb(33, 150, 243)
                    setCircleColor(Color.rgb(33, 150, 243))
                    lineWidth = 2f
                    circleRadius = 4f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }

                data = LineData(dataSet)
                animateX(1500)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SimpleBarChart() {
    AndroidView(
        factory = { ctx ->
            BarChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)

                val entries = ArrayList<BarEntry>()
                for (i in 0 until 15) {
                    entries.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
                }

                val dataSet = BarDataSet(entries, "Bar Data").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                }

                data = BarData(dataSet).apply { barWidth = 0.8f }
                animateY(1500)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SimpleScatterChart() {
    AndroidView(
        factory = { ctx ->
            ScatterChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)

                val entries = ArrayList<Entry>()
                for (i in 0 until 30) {
                    entries.add(Entry(i.toFloat(), (Math.random() * 100).toFloat()))
                }

                val dataSet = ScatterDataSet(entries, "Scatter Data").apply {
                    color = Color.rgb(255, 152, 0)
                    setScatterShape(ScatterChart.ScatterShape.CIRCLE)
                    scatterShapeSize = 15f
                }

                data = ScatterData(dataSet)
                animateXY(1500, 1500)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SimplePieChart() {
    AndroidView(
        factory = { ctx ->
            PieChart(ctx).apply {
                setBackgroundColor(Color.WHITE)
                description.isEnabled = false
                setUsePercentValues(true)
                isDrawHoleEnabled = true
                holeRadius = 50f
                transparentCircleRadius = 55f

                val entries = ArrayList<PieEntry>()
                entries.add(PieEntry(35f, "Category A"))
                entries.add(PieEntry(25f, "Category B"))
                entries.add(PieEntry(20f, "Category C"))
                entries.add(PieEntry(20f, "Category D"))

                val dataSet = PieDataSet(entries, "Pie Data").apply {
                    colors = ColorTemplate.VORDIPLOM_COLORS.toList()
                    sliceSpace = 3f
                }

                data = PieData(dataSet).apply {
                    setValueTextSize(12f)
                    setValueTextColor(Color.WHITE)
                }
                animateY(1500)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ScrollViewChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Tall Bar Chart",
        onBackClick = onBackClick
    ) {
        AndroidView(
            factory = { ctx ->
                BarChart(ctx).apply {
                    setBackgroundColor(Color.WHITE)
                    description.isEnabled = false
                    setTouchEnabled(true)
                    isDragEnabled = true
                    setScaleEnabled(true)
                    isScaleYEnabled = false
                    setPinchZoom(false)
                    setDrawGridBackground(false)

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        granularity = 1f
                    }

                    axisLeft.apply {
                        axisMinimum = 0f
                    }
                    axisRight.isEnabled = false

                    val entries = ArrayList<BarEntry>()
                    for (i in 0 until 100) {
                        entries.add(BarEntry(i.toFloat(), (Math.random() * 100).toFloat()))
                    }

                    val dataSet = BarDataSet(entries, "Bar Data").apply {
                        colors = listOf(
                            Color.rgb(255, 102, 0),
                            Color.rgb(255, 198, 0),
                            Color.rgb(104, 241, 175),
                            Color.rgb(164, 228, 251),
                            Color.rgb(255, 152, 255)
                        )
                    }

                    data = BarData(dataSet).apply { barWidth = 0.9f }
                    setVisibleXRangeMaximum(20f)
                    moveViewToX(0f)
                    animateY(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ListViewBarChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Many Bar Charts",
        onBackClick = onBackClick
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(20) { index ->
                AndroidView(
                    factory = { ctx ->
                        BarChart(ctx).apply {
                            setBackgroundColor(Color.WHITE)
                            description.isEnabled = false
                            setTouchEnabled(true)
                            setDrawGridBackground(false)

                            xAxis.apply {
                                position = XAxis.XAxisPosition.BOTTOM
                                setDrawGridLines(false)
                            }

                            axisLeft.axisMinimum = 0f
                            axisRight.isEnabled = false

                            val entries = ArrayList<BarEntry>()
                            for (i in 0 until 8) {
                                entries.add(BarEntry(i.toFloat(), (Math.random() * 50).toFloat() + 20))
                            }

                            val dataSet = BarDataSet(entries, "Chart ${index + 1}").apply {
                                color = when (index % 4) {
                                    0 -> Color.rgb(255, 102, 0)
                                    1 -> Color.rgb(76, 175, 80)
                                    2 -> Color.rgb(33, 150, 243)
                                    else -> Color.rgb(156, 39, 176)
                                }
                            }

                            data = BarData(dataSet).apply { barWidth = 0.8f }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun DynamicChartScreen(onBackClick: () -> Unit) {
    var chartRef by remember { mutableStateOf<LineChart?>(null) }
    var dataIndex by remember { mutableFloatStateOf(0f) }

    ChartScaffold(
        title = "Dynamic Chart",
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

                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                        axisLeft.axisMinimum = 0f
                        axisRight.isEnabled = false

                        val entries = ArrayList<Entry>()
                        val dataSet = LineDataSet(entries, "Dynamic Data").apply {
                            color = Color.rgb(33, 150, 243)
                            setCircleColor(Color.rgb(33, 150, 243))
                            lineWidth = 2f
                            circleRadius = 4f
                            mode = LineDataSet.Mode.CUBIC_BEZIER
                        }

                        data = LineData(dataSet)
                        chartRef = this
                    }
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
                androidx.compose.material3.Button(
                    onClick = {
                        chartRef?.let { chart ->
                            val data = chart.data
                            if (data != null) {
                                val set = data.getDataSetByIndex(0) as? LineDataSet
                                set?.addEntry(Entry(dataIndex, (Math.random() * 100).toFloat()))
                                dataIndex += 1f
                                data.notifyDataChanged()
                                chart.notifyDataSetChanged()
                                chart.invalidate()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Entry")
                }

                androidx.compose.material3.Button(
                    onClick = {
                        chartRef?.let { chart ->
                            chart.data?.clearValues()
                            dataIndex = 0f
                            chart.invalidate()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Clear")
                }
            }
        }
    }
}

@Composable
fun RealtimeChartScreen(onBackClick: () -> Unit) {
    var chartRef by remember { mutableStateOf<LineChart?>(null) }
    var isRunning by remember { mutableStateOf(true) }
    var dataIndex by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            chartRef?.let { chart ->
                val data = chart.data
                if (data != null) {
                    val set = data.getDataSetByIndex(0) as? LineDataSet
                    set?.addEntry(Entry(dataIndex, (Math.random() * 100).toFloat()))
                    dataIndex += 1f
                    data.notifyDataChanged()
                    chart.notifyDataSetChanged()
                    chart.setVisibleXRangeMaximum(40f)
                    chart.moveViewToX(data.entryCount.toFloat())
                }
            }
            delay(100)
        }
    }

    ChartScaffold(
        title = "Realtime Chart",
        onBackClick = {
            isRunning = false
            onBackClick()
        }
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
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                        }

                        axisLeft.apply {
                            axisMinimum = 0f
                            axisMaximum = 100f
                        }
                        axisRight.isEnabled = false

                        val entries = ArrayList<Entry>()
                        val dataSet = LineDataSet(entries, "Realtime Data").apply {
                            color = Color.rgb(244, 67, 54)
                            setCircleColor(Color.rgb(244, 67, 54))
                            lineWidth = 2f
                            circleRadius = 3f
                            setDrawCircleHole(false)
                            setDrawValues(false)
                        }

                        data = LineData(dataSet)
                        chartRef = this
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            androidx.compose.material3.Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(if (isRunning) "Pause" else "Resume")
            }
        }
    }
}

@Composable
fun HourlyChartScreen(onBackClick: () -> Unit) {
    ChartScaffold(
        title = "Hourly Chart",
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

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        valueFormatter = com.github.mikephil.charting.formatter.IAxisValueFormatter { value, _ ->
                            val hour = value.toInt() % 24
                            String.format("%02d:00", hour)
                        }
                    }

                    axisLeft.apply {
                        axisMinimum = 0f
                    }
                    axisRight.isEnabled = false

                    val entries = ArrayList<Entry>()
                    val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)

                    for (i in 0..currentHour) {
                        entries.add(Entry(i.toFloat(), (Math.random() * 100).toFloat()))
                    }

                    val dataSet = LineDataSet(entries, "Hourly Data").apply {
                        color = Color.rgb(76, 175, 80)
                        setCircleColor(Color.rgb(76, 175, 80))
                        lineWidth = 2.5f
                        circleRadius = 5f
                        setDrawFilled(true)
                        fillColor = Color.rgb(76, 175, 80)
                        fillAlpha = 50
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                    }

                    data = LineData(dataSet)
                    animateX(1500)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
