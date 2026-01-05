package com.xxmassdeveloper.mpchartexample.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object LineChart1 : Screen("line_chart_1")
    object LineChart2 : Screen("line_chart_2")
    object MultiLineChart : Screen("multi_line_chart")
    object InvertedLineChart : Screen("inverted_line_chart")
    object CubicLineChart : Screen("cubic_line_chart")
    object ColoredLineChart : Screen("colored_line_chart")
    object PerformanceLineChart : Screen("performance_line_chart")
    object FilledLineChart : Screen("filled_line_chart")
    object BarChart : Screen("bar_chart")
    object AnotherBarChart : Screen("another_bar_chart")
    object MultiBarChart : Screen("multi_bar_chart")
    object HorizontalBarChart : Screen("horizontal_bar_chart")
    object StackedBarChart : Screen("stacked_bar_chart")
    object PositiveNegativeBarChart : Screen("positive_negative_bar_chart")
    object HorizontalNegativeBarChart : Screen("horizontal_negative_bar_chart")
    object StackedNegativeBarChart : Screen("stacked_negative_bar_chart")
    object SineBarChart : Screen("sine_bar_chart")
    object PieChart : Screen("pie_chart")
    object PiePolylineChart : Screen("pie_polyline_chart")
    object HalfPieChart : Screen("half_pie_chart")
    object CombinedChart : Screen("combined_chart")
    object ScatterChart : Screen("scatter_chart")
    object BubbleChart : Screen("bubble_chart")
    object CandlestickChart : Screen("candlestick_chart")
    object RadarChart : Screen("radar_chart")
    object ListViewMultiChart : Screen("listview_multi_chart")
    object SimpleChartDemo : Screen("simple_chart_demo")
    object ScrollViewChart : Screen("scrollview_chart")
    object ListViewBarChart : Screen("listview_bar_chart")
    object DynamicChart : Screen("dynamic_chart")
    object RealtimeChart : Screen("realtime_chart")
    object HourlyChart : Screen("hourly_chart")
}

data class ChartItem(
    val title: String,
    val description: String = "",
    val isSection: Boolean = false,
    val screen: Screen? = null
)

fun getChartItems(): List<ChartItem> = listOf(
    ChartItem("Line Charts", isSection = true),
    ChartItem("Basic", "Simple line chart.", screen = Screen.LineChart1),
    ChartItem("Multiple", "Show multiple data sets.", screen = Screen.MultiLineChart),
    ChartItem("Dual Axis", "Line chart with dual y-axes.", screen = Screen.LineChart2),
    ChartItem("Inverted Axis", "Inverted y-axis.", screen = Screen.InvertedLineChart),
    ChartItem("Cubic", "Line chart with a cubic line shape.", screen = Screen.CubicLineChart),
    ChartItem("Colorful", "Colorful line chart.", screen = Screen.ColoredLineChart),
    ChartItem("Performance", "Render 30.000 data points smoothly.", screen = Screen.PerformanceLineChart),
    ChartItem("Filled", "Colored area between two lines.", screen = Screen.FilledLineChart),

    ChartItem("Bar Charts", isSection = true),
    ChartItem("Basic", "Simple bar chart.", screen = Screen.BarChart),
    ChartItem("Basic 2", "Variation of the simple bar chart.", screen = Screen.AnotherBarChart),
    ChartItem("Multiple", "Show multiple data sets.", screen = Screen.MultiBarChart),
    ChartItem("Horizontal", "Render bar chart horizontally.", screen = Screen.HorizontalBarChart),
    ChartItem("Stacked", "Stacked bar chart.", screen = Screen.StackedBarChart),
    ChartItem("Negative", "Positive and negative values with unique colors.", screen = Screen.PositiveNegativeBarChart),
    ChartItem("Negative Horizontal", "Horizontal bar chart with positive and negative values.", screen = Screen.HorizontalNegativeBarChart),
    ChartItem("Stacked 2", "Stacked bar chart with negative values.", screen = Screen.StackedNegativeBarChart),
    ChartItem("Sine", "Sine function in bar chart format.", screen = Screen.SineBarChart),

    ChartItem("Pie Charts", isSection = true),
    ChartItem("Basic", "Simple pie chart.", screen = Screen.PieChart),
    ChartItem("Value Lines", "Stylish lines drawn outward from slices.", screen = Screen.PiePolylineChart),
    ChartItem("Half Pie", "180 degree (half) pie chart.", screen = Screen.HalfPieChart),

    ChartItem("Other Charts", isSection = true),
    ChartItem("Combined Chart", "Bar and line chart together.", screen = Screen.CombinedChart),
    ChartItem("Scatter Plot", "Simple scatter plot.", screen = Screen.ScatterChart),
    ChartItem("Bubble Chart", "Simple bubble chart.", screen = Screen.BubbleChart),
    ChartItem("Candlestick", "Simple financial chart.", screen = Screen.CandlestickChart),
    ChartItem("Radar Chart", "Simple web chart.", screen = Screen.RadarChart),

    ChartItem("Scrolling Charts", isSection = true),
    ChartItem("Multiple", "Various types of charts as fragments.", screen = Screen.ListViewMultiChart),
    ChartItem("View Pager", "Swipe through different charts.", screen = Screen.SimpleChartDemo),
    ChartItem("Tall Bar Chart", "Bars bigger than your screen!", screen = Screen.ScrollViewChart),
    ChartItem("Many Bar Charts", "More bars than your screen can handle!", screen = Screen.ListViewBarChart),

    ChartItem("Even More Line Charts", isSection = true),
    ChartItem("Dynamic", "Build a line chart by adding points and sets.", screen = Screen.DynamicChart),
    ChartItem("Realtime", "Add data points in realtime.", screen = Screen.RealtimeChart),
    ChartItem("Hourly", "Uses the current time to add a data point for each hour.", screen = Screen.HourlyChart)
)
