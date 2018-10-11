package com.doyou.mpcase.fragment.linechart

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doyou.mpcase.R
import com.github.mikephil.charting.charts.LineChart
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
        initLineChart(lineOne, false)
        initLineChart(lineTwo, false)

        lineSectionTv.setOnClickListener(this)
        lineAllTv.setOnClickListener(this)
        lineFillTv.setOnClickListener(this)

        loadData()
    }

    private fun loadData() {
        lineOne.postDelayed({
            LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false)
            LineChartModule.notifyDataToLine(lineTwo, LineChartModule.VALUE_TYPE.WEEK, false)
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
        lineAllTv.setTextColor(Color.rgb(194, 194, 194))
        lineFillTv.setTextColor(Color.rgb(194, 194, 194))
        when (v!!.id) {
            R.id.lineSectionTv -> {
                mIsFill = false
                lineSectionTv.setTextColor(Color.rgb(69, 113, 214))
                lineOne.viewPortHandler.setMinMaxScaleX(1f, 1f)
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, false)
            }
            R.id.lineAllTv -> {
                lineAllTv.setTextColor(Color.rgb(69, 113, 214))
                autoCalMatrixScale(lineOne, LineChartModule.getCountByValueType(LineChartModule.VALUE_TYPE.DAY).toFloat())
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, mIsFill)
            }
            R.id.lineFillTv -> {
                mIsFill = true
                lineFillTv.setTextColor(Color.rgb(69, 113, 214))
                LineChartModule.notifyDataToLine(lineOne, LineChartModule.VALUE_TYPE.DAY, true)
            }
        }
    }

    private fun initLineChart(lineChart: LineChart,needLegend:Boolean){
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
        if (needLegend){
            lineChart.legend.isEnabled = true
        }else{
            lineChart.legend.isEnabled = false
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

        // y轴设置
        val leftAxis = lineChart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.axisLineColor = Color.rgb(69, 113, 214) // 同x轴
        leftAxis.textColor = Color.rgb(153, 153, 153) // 同x轴
        leftAxis.textSize = 10f // 同x轴
        leftAxis.setDrawGridLines(false) // 同x轴
        leftAxis.setCenterAxisLabels(false) // 同x轴,y轴的这个属性设置true可能会导致y轴的最顶部被裁剪一部分，导致的显示不全的问题
        leftAxis.granularity = 1f // 同x轴
        leftAxis.isGranularityEnabled = true // 同x轴


    }
}
