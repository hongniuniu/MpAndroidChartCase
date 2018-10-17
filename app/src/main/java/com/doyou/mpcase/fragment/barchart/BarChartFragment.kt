package com.doyou.mpcase.fragment.barchart

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doyou.mpcase.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.barchart_fragment.*

/**
 * 柱状图
 * @autor hongbing
 * @date 2018/10/11
 */
class BarChartFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.barchart_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBar(stackBar)

        stackBar.postDelayed({
            setData()
        }, 1200)
    }

    private fun initBar(barChart: BarChart){
        barChart.description.isEnabled = false
        barChart.setMaxVisibleValueCount(12) // 有效最大个数12
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setDrawBorders(false)
        barChart.setNoDataText("暂无数据")
        barChart.setNoDataTextColor(Color.rgb(200, 200, 200))
        barChart.isHighlightFullBarEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.legend.isEnabled = false
//        barChart.extraBottomOffset = 10f

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f

        val leftAxis = barChart.axisLeft
//        leftAxis.setValueFormatter(My)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setCenterAxisLabels(true)
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true
        leftAxis.granularity = 1f
        leftAxis.axisMinimum = 0f
        leftAxis.spaceBottom = 0f


//        barChart.setFitBars(true)
    }

    private fun setData(){
        var yValues = ArrayList<BarEntry>()

        // 确定柱子和对应的值
        for (i in 0..11) {
            val val1 = (Math.random() + 24).toFloat()
            val val2 = (Math.random() + 32).toFloat()
            yValues.add(BarEntry(i.toFloat(), floatArrayOf(val1, val2)))
        }

        // 数据填充
        var set1: BarDataSet
        if (stackBar.data != null && stackBar.data.dataSetCount > 0){ // 数据结构已存在的时候，直接刷新
            set1 = stackBar.data.getDataSetByIndex(0) as BarDataSet
            set1.values = yValues
            stackBar.data.notifyDataChanged()
            stackBar.notifyDataSetChanged()
        }else{
            set1 = BarDataSet(yValues,"")
//            set1.setDrawIcons(false)
            set1.setDrawValues(true)
            set1.isHighlightEnabled = true
            set1.setColors(getColors().toList())
            set1.stackLabels = arrayOf("Births", "Divorces")

            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
//            data.setValueFormatter(MyValueFormatter())
            data.setValueTextColor(Color.WHITE)

            stackBar.setData(data)
            stackBar.invalidate()
        }

//        stackBar.setFitBars(true)

    }

    private fun getColors(): IntArray {

        val stacksize = 2

        // have as many colors as stack-values per entry
        val colors = IntArray(stacksize)

        for (i in colors.indices) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i]
        }

        return colors
    }
}
