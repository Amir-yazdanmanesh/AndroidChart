package com.xxmassdeveloper.mpchartexample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmassdeveloper.mpchartexample.ui.navigation.Screen
import com.xxmassdeveloper.mpchartexample.ui.screens.AnotherBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.BarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.BubbleChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.CandlestickChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.ColoredLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.CombinedChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.CubicLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.DynamicChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.FilledLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.HalfPieChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.HorizontalBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.HorizontalNegativeBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.HourlyChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.InvertedLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.LineChart1Screen
import com.xxmassdeveloper.mpchartexample.ui.screens.LineChart2Screen
import com.xxmassdeveloper.mpchartexample.ui.screens.ListViewBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.ListViewMultiChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.MainScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.MultiBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.MultiLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.PerformanceLineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.PieChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.PiePolylineChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.PositiveNegativeBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.RadarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.RealtimeChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.ScatterChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.ScrollViewChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.SimpleChartDemoScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.SineBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.StackedBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.screens.StackedNegativeBarChartScreen
import com.xxmassdeveloper.mpchartexample.ui.theme.MPChartTheme

@Composable
fun ChartApp() {
    MPChartTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Main.route
        ) {
            composable(Screen.Main.route) {
                MainScreen(
                    onNavigate = { screen ->
                        navController.navigate(screen.route)
                    }
                )
            }

            // Line Charts
            composable(Screen.LineChart1.route) {
                LineChart1Screen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.LineChart2.route) {
                LineChart2Screen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.MultiLineChart.route) {
                MultiLineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.InvertedLineChart.route) {
                InvertedLineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.CubicLineChart.route) {
                CubicLineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.ColoredLineChart.route) {
                ColoredLineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.PerformanceLineChart.route) {
                PerformanceLineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.FilledLineChart.route) {
                FilledLineChartScreen(onBackClick = { navController.popBackStack() })
            }

            // Bar Charts
            composable(Screen.BarChart.route) {
                BarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.AnotherBarChart.route) {
                AnotherBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.MultiBarChart.route) {
                MultiBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.HorizontalBarChart.route) {
                HorizontalBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.StackedBarChart.route) {
                StackedBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.PositiveNegativeBarChart.route) {
                PositiveNegativeBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.HorizontalNegativeBarChart.route) {
                HorizontalNegativeBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.StackedNegativeBarChart.route) {
                StackedNegativeBarChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.SineBarChart.route) {
                SineBarChartScreen(onBackClick = { navController.popBackStack() })
            }

            // Pie Charts
            composable(Screen.PieChart.route) {
                PieChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.PiePolylineChart.route) {
                PiePolylineChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.HalfPieChart.route) {
                HalfPieChartScreen(onBackClick = { navController.popBackStack() })
            }

            // Other Charts
            composable(Screen.CombinedChart.route) {
                CombinedChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.ScatterChart.route) {
                ScatterChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.BubbleChart.route) {
                BubbleChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.CandlestickChart.route) {
                CandlestickChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.RadarChart.route) {
                RadarChartScreen(onBackClick = { navController.popBackStack() })
            }

            // Scrolling Charts
            composable(Screen.ListViewMultiChart.route) {
                ListViewMultiChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.SimpleChartDemo.route) {
                SimpleChartDemoScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.ScrollViewChart.route) {
                ScrollViewChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.ListViewBarChart.route) {
                ListViewBarChartScreen(onBackClick = { navController.popBackStack() })
            }

            // Dynamic Charts
            composable(Screen.DynamicChart.route) {
                DynamicChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.RealtimeChart.route) {
                RealtimeChartScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Screen.HourlyChart.route) {
                HourlyChartScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}
