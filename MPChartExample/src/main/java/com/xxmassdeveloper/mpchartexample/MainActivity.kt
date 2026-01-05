package com.xxmassdeveloper.mpchartexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.github.mikephil.charting.utils.Utils
import com.xxmassdeveloper.mpchartexample.ui.ChartApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the charting utilities
        Utils.init(this)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ChartApp()
        }
    }
}
