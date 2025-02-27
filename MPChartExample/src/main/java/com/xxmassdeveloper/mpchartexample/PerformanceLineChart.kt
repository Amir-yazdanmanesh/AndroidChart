package com.xxmassdeveloper.mpchartexample

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.xxmassdeveloper.mpchartexample.databinding.ActivityLinechartBinding
import com.xxmassdeveloper.mpchartexample.databinding.ActivityPerformanceLinechartBinding
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase

class PerformanceLineChart : DemoBase(), SeekBar.OnSeekBarChangeListener {
    private lateinit var chart: LineChart
    private lateinit var seekBarValues: SeekBar
    private lateinit var tvCount: TextView
    private lateinit var binding: ActivityPerformanceLinechartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityPerformanceLinechartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = "PerformanceLineChart"
        tvCount = binding.tvValueCount
        seekBarValues = binding.seekbarValues
        seekBarValues.setOnSeekBarChangeListener(this)
        chart = binding.barChart
        chart.setDrawGridBackground(false)

        // no description text
        chart.getDescription().isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.getAxisLeft().setDrawGridLines(false)
        chart.getAxisRight().isEnabled = false
        chart.getXAxis().setDrawGridLines(true)
        chart.getXAxis().setDrawAxisLine(false)
        seekBarValues.setProgress(9000)

        // don't forget to refresh the drawing
        chart.invalidate()
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val `val` = (Math.random() * (range + 1)).toFloat() + 3
            values.add(Entry(i * 0.001f, `val`))
        }

        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        set1.color = Color.BLACK
        set1.lineWidth = 0.5f
        set1.setDrawValues(false)
        set1.setDrawCircles(false)
        set1.mode = LineDataSet.Mode.LINEAR
        set1.setDrawFilled(false)

        // create a data object with the data sets
        val data = LineData(set1)

        // set data
        chart!!.data = data

        // get the legend (only possible after setting data)
        val l = chart!!.legend
        l.isEnabled = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.only_github, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.viewGithub -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data =
                    Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/PerformanceLineChart.java")
                startActivity(i)
            }
        }
        return true
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val count = seekBarValues!!.progress + 1000
        tvCount!!.text = count.toString()
        chart!!.resetTracking()
        setData(count, 500f)

        // redraw
        chart!!.invalidate()
    }

    public override fun saveToGallery() { /* Intentionally left empty */
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}