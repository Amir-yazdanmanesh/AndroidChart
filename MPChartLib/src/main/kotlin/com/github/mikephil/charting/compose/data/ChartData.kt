package com.github.mikephil.charting.compose.data

/**
 * Base sealed interface for chart data containing multiple datasets.
 */
sealed interface ChartData<S : ChartDataSet<*>> {
    /** List of all datasets */
    val dataSets: List<S>

    /** Number of datasets */
    val dataSetCount: Int get() = dataSets.size

    /** Whether data is empty */
    val isEmpty: Boolean get() = dataSets.isEmpty() || dataSets.all { it.isEmpty }

    /** Total entry count across all datasets */
    val entryCount: Int get() = dataSets.sumOf { it.entryCount }

    /** Minimum Y value across all datasets */
    val yMin: Float get() = dataSets.minOfOrNull { it.yMin } ?: 0f

    /** Maximum Y value across all datasets */
    val yMax: Float get() = dataSets.maxOfOrNull { it.yMax } ?: 0f

    /** Get dataset at index */
    fun getDataSetByIndex(index: Int): S? = dataSets.getOrNull(index)

    /** Get dataset by label */
    fun getDataSetByLabel(label: String): S? = dataSets.find { it.label == label }
}

/**
 * Data for LineChart containing multiple line datasets.
 */
data class LineData(
    override val dataSets: List<LineDataSet>
) : ChartData<LineDataSet> {

    constructor(vararg dataSets: LineDataSet) : this(dataSets.toList())

    /** Minimum X value across all datasets */
    val xMin: Float get() = dataSets.minOfOrNull { it.xMin } ?: 0f

    /** Maximum X value across all datasets */
    val xMax: Float get() = dataSets.maxOfOrNull { it.xMax } ?: 0f

    fun withDataSet(dataSet: LineDataSet): LineData = copy(dataSets = dataSets + dataSet)
    fun withDataSets(newDataSets: List<LineDataSet>): LineData = copy(dataSets = newDataSets)
}

/**
 * Data for BarChart containing multiple bar datasets.
 */
data class BarData(
    override val dataSets: List<BarDataSet>,
    /** Width of bars relative to the space available (0.0 - 1.0) */
    val barWidth: Float = 0.85f
) : ChartData<BarDataSet> {

    constructor(vararg dataSets: BarDataSet) : this(dataSets.toList())

    /** Minimum X value across all datasets */
    val xMin: Float get() = dataSets.minOfOrNull { it.xMin } ?: 0f

    /** Maximum X value across all datasets */
    val xMax: Float get() = dataSets.maxOfOrNull { it.xMax } ?: 0f

    fun withDataSet(dataSet: BarDataSet): BarData = copy(dataSets = dataSets + dataSet)
    fun withBarWidth(width: Float): BarData = copy(barWidth = width.coerceIn(0f, 1f))
}

/**
 * Data for PieChart containing a single pie dataset.
 */
data class PieData(
    override val dataSets: List<PieDataSet>
) : ChartData<PieDataSet> {

    constructor(dataSet: PieDataSet) : this(listOf(dataSet))

    /** Primary dataset (pie charts typically have one) */
    val dataSet: PieDataSet? get() = dataSets.firstOrNull()

    /** Total sum of all values */
    val yValueSum: Float get() = dataSet?.yValueSum ?: 0f
}

/**
 * Data for ScatterChart containing multiple scatter datasets.
 */
data class ScatterData(
    override val dataSets: List<ScatterDataSet>
) : ChartData<ScatterDataSet> {

    constructor(vararg dataSets: ScatterDataSet) : this(dataSets.toList())

    /** Minimum X value across all datasets */
    val xMin: Float get() = dataSets.minOfOrNull { it.xMin } ?: 0f

    /** Maximum X value across all datasets */
    val xMax: Float get() = dataSets.maxOfOrNull { it.xMax } ?: 0f
}

/**
 * Data for BubbleChart containing multiple bubble datasets.
 */
data class BubbleData(
    override val dataSets: List<BubbleDataSet>
) : ChartData<BubbleDataSet> {

    constructor(vararg dataSets: BubbleDataSet) : this(dataSets.toList())

    /** Minimum X value across all datasets */
    val xMin: Float get() = dataSets.minOfOrNull { it.xMin } ?: 0f

    /** Maximum X value across all datasets */
    val xMax: Float get() = dataSets.maxOfOrNull { it.xMax } ?: 0f

    /** Maximum bubble size across all datasets */
    val maxBubbleSize: Float get() = dataSets.maxOfOrNull { it.maxSize } ?: 0f
}

/**
 * Data for CandleStickChart containing multiple candle datasets.
 */
data class CandleData(
    override val dataSets: List<CandleDataSet>
) : ChartData<CandleDataSet> {

    constructor(vararg dataSets: CandleDataSet) : this(dataSets.toList())

    /** Minimum X value across all datasets */
    val xMin: Float get() = dataSets.minOfOrNull { it.xMin } ?: 0f

    /** Maximum X value across all datasets */
    val xMax: Float get() = dataSets.maxOfOrNull { it.xMax } ?: 0f

    /** Minimum low value across all datasets */
    val lowMin: Float get() = dataSets.minOfOrNull { it.lowMin } ?: 0f

    /** Maximum high value across all datasets */
    val highMax: Float get() = dataSets.maxOfOrNull { it.highMax } ?: 0f
}

/**
 * Data for RadarChart containing multiple radar datasets.
 */
data class RadarData(
    override val dataSets: List<RadarDataSet>,
    /** Labels for each axis of the radar */
    val labels: List<String> = emptyList()
) : ChartData<RadarDataSet> {

    constructor(vararg dataSets: RadarDataSet) : this(dataSets.toList())

    /** Number of radar axes (based on first dataset) */
    val axisCount: Int get() = dataSets.firstOrNull()?.entryCount ?: 0

    fun withLabels(vararg labels: String): RadarData = copy(labels = labels.toList())
}

/**
 * Data for CombinedChart containing multiple types of datasets.
 */
data class CombinedData(
    val lineData: LineData? = null,
    val barData: BarData? = null,
    val scatterData: ScatterData? = null,
    val candleData: CandleData? = null,
    val bubbleData: BubbleData? = null
) {
    /** All chart data objects that are not null */
    val allData: List<ChartData<*>>
        get() = listOfNotNull(lineData, barData, scatterData, candleData, bubbleData)

    /** Whether all data is empty */
    val isEmpty: Boolean get() = allData.all { it.isEmpty }

    /** Minimum X value across all data */
    val xMin: Float
        get() = listOfNotNull(
            lineData?.xMin,
            barData?.xMin,
            scatterData?.xMin,
            candleData?.xMin,
            bubbleData?.xMin
        ).minOrNull() ?: 0f

    /** Maximum X value across all data */
    val xMax: Float
        get() = listOfNotNull(
            lineData?.xMax,
            barData?.xMax,
            scatterData?.xMax,
            candleData?.xMax,
            bubbleData?.xMax
        ).maxOrNull() ?: 0f

    /** Minimum Y value across all data */
    val yMin: Float get() = allData.minOfOrNull { it.yMin } ?: 0f

    /** Maximum Y value across all data */
    val yMax: Float get() = allData.maxOfOrNull { it.yMax } ?: 0f
}
