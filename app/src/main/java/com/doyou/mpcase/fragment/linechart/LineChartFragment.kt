package com.doyou.mpcase.fragment.linechart

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doyou.mpcase.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import kotlinx.android.synthetic.main.linechart_fragment.*

/**
 * 折线图
 * @autor hongbing
 * @date 2018/10/11
 */
class LineChartFragment : Fragment() ,View.OnClickListener{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.linechart_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLineChart(lineOne, false,true)
        initLineChart(lineTwo, false,true)
        initLineChart(lineThree, false,true)
        initLineChart(lineFour, true,false)
        initLineChart(lineFive, false,true)
        initLineChart(lineSix, false,true)
        initLineChart(lineSeven, false,false)

        lineSectionTv.setOnClickListener(this)
//        lineCircleTv.setOnClickListener(this)
        lineAllTv.setOnClickListener(this)
        lineMarkerTv.setOnClickListener(this)
        lineTv6.setOnClickListener(this)

        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (lineOne != null){
            releaseChartCache(lineOne)
        }
        if (lineTwo != null){
            releaseChartCache(lineTwo)
        }
        if (lineThree != null){
            releaseChartCache(lineThree)
        }
        if (lineFour != null){
            releaseChartCache(lineFour)
        }
        if (lineFive != null){
            releaseChartCache(lineFive)
        }
        if (lineSix != null){
            releaseChartCache(lineSix)
        }
        if (lineSeven != null){
            releaseChartCache(lineSeven)
        }
        LineChartModule.release()

    }

    private fun loadData() {
        lineOne.postDelayed({
            LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false)

            LineChartModule.notifyDataToLine(lineTwo, LineChartModule.VALUE_TYPE.WEEK, true)

            autoCalMatrixScale(lineThree, LineChartModule.getCountByValueType(LineChartModule.VALUE_TYPE.MONTH).toFloat())
            LineChartModule.notifyDataToLine(lineThree, LineChartModule.VALUE_TYPE.MONTH, true)

            autoCalMatrixScale(lineFour, LineChartModule.getCountByValueType(LineChartModule.VALUE_TYPE.YEAR).toFloat())
            LineChartModule.notifyDataToLineByMultiple(lineFour)

            val mean = 60f
            LineChartModule.addLimitLine(lineFive.axisLeft, mean, "平均线 = " + mean.toInt())
            LineChartModule.notifyDataToLine(lineFive, LineChartModule.VALUE_TYPE.SEASONS, mean, false,false)

            LineChartModule.notifyDataToLineBySub(lineSix, LineChartModule.VALUE_TYPE.MORE, false,true)
        }, 240)
    }

    /**
     * 自动缩放图表，超出左右滑动查看
     * @param count
     * @param bean
     */
    private val LABEL_COUNT = 6 // 每屏显示6个label
    fun autoCalMatrixScale(chart: LineChart, count: Float) {
        if (count / LABEL_COUNT > 1f) {
//            if (bean!!.time[0].contains("201")) {
//                val scale = count / LABEL_COUNT + 0.5f
//                chart.viewPortHandler.setMinMaxScaleX(scale, scale)
//            } else {
                val scale = count / LABEL_COUNT
                chart.viewPortHandler.setMinMaxScaleX(scale, scale)
//            }
        } else {
//            if (bean!!.time[0].contains("201") && bean.time.size > 4) {
//                chart.viewPortHandler.setMinMaxScaleX(1.5f, 1.5f)
//            } else {
                chart.viewPortHandler.setMinMaxScaleX(1f, 1f)
//            }
        }
        chart.invalidate()
    }

    private var mIsFill: Boolean = false
    override fun onClick(v: View?) {
        lineSectionTv.setTextColor(Color.rgb(194, 194, 194))
//        lineCircleTv.setTextColor(Color.rgb(194, 194, 194))
        lineAllTv.setTextColor(Color.rgb(194, 194, 194))
        lineMarkerTv.setTextColor(Color.rgb(194, 194, 194))
        when (v!!.id) {
            R.id.lineSectionTv -> {
                mIsFill = false
                lineSectionTv.setTextColor(Color.rgb(69, 113, 214))
                lineOne.viewPortHandler.setMinMaxScaleX(1f, 1f)
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false)
            }
//            R.id.lineCircleTv -> {
////                lineCircleTv.setTextColor(Color.rgb(69, 113, 214))
////                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false,true,true)
//                showLongToast(activity!!,"图表的圆点数显示和x轴保持一致")
//            }
            R.id.lineAllTv -> {
                lineAllTv.setTextColor(Color.rgb(69, 113, 214))
                autoCalMatrixScale(lineOne, LineChartModule.getCountByValueType(LineChartModule.VALUE_TYPE.DAY).toFloat())
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, mIsFill)
            }
            R.id.lineMarkerTv -> {
                mIsFill = true
                lineMarkerTv.setTextColor(Color.rgb(69, 113, 214))
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false,true)
            }
            R.id.lineTv6 ->{
                LineChartModule.notifyDataToLineBySub(lineSix, LineChartModule.VALUE_TYPE.MORE, true, true)
            }
            else -> {

            }
        }
    }

    /**
     * 控件初始化
     * @param lineChart 折线图控件
     * @param needLegend 是否需要显示图例
     * @param isGranularity isGranularity: Boolean // 特别注意哦，如果y轴需要显示小数，记得传false哦
     */
    private fun initLineChart(lineChart: LineChart,needLegend:Boolean,isGranularity: Boolean){
        // 基本设置
        lineChart.description.isEnabled = false
        lineChart.setDrawGridBackground(false)
        lineChart.setScaleEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.setDrawBorders(false)
        lineChart.axisRight.isEnabled = false // 隐藏右侧y轴
        // 图表数据为空的UI显示样式
        lineChart.setNoDataText("暂无统计数据")
        lineChart.setNoDataTextColor(Color.rgb(200, 200, 200))
//        lineChart.extraRightOffset = 22f
//        lineChart.extraBottomOffset = 10f
        // 拖拽的x轴值（x轴起点脱离y轴的间距）,主要目的是为了避免数据和y轴重叠导致看不清的问题
        lineChart.setDragOffsetX(15f)

        // 图例设置
        val legend = lineChart.legend
        if (needLegend){
            legend.isEnabled = true
            legend.form = Legend.LegendForm.CIRCLE
            legend.textSize = 10f
            legend.textColor = Color.rgb(149, 199, 255)
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.xOffset = -12f // 默认感觉不是很水平居中，强制往左移动12dp
            legend.setDrawInside(false) // 是否在坐标轴内，一般都是在外面的，所以此处设置false
        }else{
            legend.isEnabled = false
        }

        // x轴设置
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM // x轴相对y轴的位置,默认在上面，一般按照正常的坐标系显示在下面
        xAxis.axisLineColor = Color.rgb(69, 113, 214) // 坐标轴颜色
        xAxis.textColor = Color.rgb(153, 153, 153) // 坐标轴值的字体颜色
        xAxis.textSize = 10f // 坐标轴值的字体大小，单位dp
        xAxis.setDrawGridLines(false) // 隐藏点对应与X轴相垂直的线（即图表内纵向的线）。
//        xAxis.setAvoidFirstLastClipping(false)
//        xAxis.setCenterAxisLabels(true) // x轴中的label相对图表整体水平居中，可能导致的问题就是点图表的点和x轴的label对不上
        xAxis.granularity = 1f // 避免缩放时导致的标签重叠
        xAxis.isGranularityEnabled = true // 在轴值间隔上 启用/禁用 粒度控制。如果启用，轴间隔不允许低于某个粒度。默认值:假
//        xAxis.axisLineWidth = 1f // 设置x轴的高度
        xAxis.setLabelCount(8,false) // 第二个参数切记设置成true，不然会导致点和坐标不居中

        // y轴设置
        val leftAxis = lineChart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.axisLineColor = Color.rgb(69, 113, 214) // 同x轴
        leftAxis.textColor = Color.rgb(153, 153, 153) // 同x轴
        leftAxis.textSize = 10f // 同x轴
        leftAxis.setDrawGridLines(false) // 同x轴
        leftAxis.setCenterAxisLabels(false) // 同x轴,y轴的这个属性设置true可能会导致y轴的最顶部被裁剪一部分，导致的显示不全的问题
        leftAxis.granularity = 1f // 同x轴
        leftAxis.isGranularityEnabled = isGranularity // 同x轴
    }

    private fun releaseChartCache(lineChart: LineChart) {
        if (lineChart != null){
            lineChart.destroyDrawingCache()
        }
    }
}
