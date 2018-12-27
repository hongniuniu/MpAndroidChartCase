package com.doyou.mpcase.module.base;

import android.graphics.Color;
import android.support.annotation.ColorRes;

import com.dongni.tools.Common;
import com.dongni.tools.EmptyUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图数据基础类
 * @autor hongbing
 * @date 2018/11/8
 */
public abstract class BaseBarModule {

    /**
     * 图表折线图动画时间
     */
    public static int CHART_ANIM_LINE_DURATION = 1200;

    /**
     * 保留两位小数
     */
    public static final DecimalFormat sPointFormat = new DecimalFormat("0.00");
    /**
     * 百分比
     */
    public static final DecimalFormat sPercentFormat = new DecimalFormat("#.##%");

    public static final int DEFAULT_FORMAT = 0x01;
    public static final int POINT_FORMAT = 0x02;
    public static final int PERCENT_FORMAT = 0x03;

    /**
     * 当数据为空的时候，刷新图表
     */
    public static void refeshChartWhenEmpty(BarChart barChart) {
        barChart.setData(null);
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    /**
     * 填充和刷新柱状图数据
     * @param chart
     * @param entries         数据源
     * @param barColor        柱子颜色
     * @param isShowDrawValue 是否显示柱子上的数字
     * @param formatType 数据格式类型
     */
    protected static void generateDataBar(BarChart chart, List<BarEntry> entries, float barRatio, @ColorRes int barColor, boolean isShowDrawValue, final int formatType) {
        BarDataSet d;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) { // 数据更新
            d = (BarDataSet) chart.getData().getDataSetByIndex(0);
            d.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            d = new BarDataSet(entries, "客流量柱状图统计");
            d.setBarShadowColor(Color.rgb(203, 203, 203));
            d.setDrawValues(isShowDrawValue); // 设置柱状图顶部不显示数值
            d.setValueTextColor(Color.rgb(153, 153, 153)); // 设置每条数据的顶部值的颜色
            d.setValueTextSize(9f); // 设置每条数据的顶部值的字体大小
            d.setColor(barColor); // 设置柱子颜色
//            d.setHighlightEnabled(false); // 屏蔽高亮显示
            d.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    switch (formatType) {
                        case POINT_FORMAT:
                            return sPointFormat.format(value);
                        case PERCENT_FORMAT:
                            return sPercentFormat.format(value);
                        default:
                            return (int) value + "";
                    }
                }
            });
            ArrayList<IBarDataSet> sets = new ArrayList<>();
            sets.add(d);
            BarData cd = new BarData(sets);
            cd.setBarWidth(barRatio);
            chart.setData(cd);
            chart.invalidate();
        }
        chart.animateY(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear);
    }


    /**
     * 填充和刷新柱状图数据(分组)
     * @param chart
     * @param setList 数据源
     * @param labels
     * @param groupCount 多少组
     * @param formatType 数据格式类型
     * @return
     */
    protected static void generateDataBarByMult(BarChart chart, List<List<BarEntry>> setList, List<String> labels,
                                                int groupCount, boolean isShowDrawValue, final int formatType) {
        BarDataSet set;
        if (EmptyUtils.isEmpty(setList) || (chart.getData() != null && chart.getData().getDataSetCount() > 0)) { // 数据更新
//            for (int i = 0; i < setList.size(); i++) {
//                set = (BarDataSet) chart.getData().getDataSetByIndex(i);
//                set.setValues(setList.get(i));
//                chart.getData().notifyDataChanged();
//            }
//            chart.notifyDataSetChanged();


            chart.getData().clearValues();
            chart.setData(null);
            chart.notifyDataSetChanged();
//            chart.invalidate();
        }


        IBarDataSet[] idSet = new IBarDataSet[setList.size()];
        for (int i = 0; i < setList.size(); i++) {
            set = new BarDataSet(setList.get(i), labels.get(i));
            if (i == 0) {
                set.setColor(Color.rgb(88, 181, 250)); // 设置柱子颜色
            } else if (i == 1) {
                set.setColor(Color.rgb(101, 226, 175));
            } else if (i == 2) {
                set.setColor(Color.rgb(255, 196, 0));
            }
            set.setHighlightEnabled(false); // 屏蔽高亮显示
            idSet[i] = set;
        }
        BarData data = new BarData(idSet);
//        if (formatType == DEFAULT_FORMAT) {
            data.setValueTextSize(8f);
//        } else {
//            data.setValueTextSize(6f);
//        }
        data.setDrawValues(isShowDrawValue);
        data.setValueTextColor(Color.rgb(153, 153, 153));
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                switch (formatType) {
                    case POINT_FORMAT:
                        return sPointFormat.format(value);
                    case PERCENT_FORMAT:
                        return sPercentFormat.format(value);
                    default:
                        return (int) value + "";
                }
            }
        });
        chart.setData(data);

        float groupSpace = 0.1f;
        float barSpace = 0.03f;
        float barWidth = 0.27f;
        Common.log_d("201808151602", "每组柱子的个数 = " + setList.size());
        switch (setList.size()) {
            case 2:
                // 测试正常
//                groupSpace = 0.28f;
//                barSpace = 0.06f;
//                barWidth = 0.3f;
//                 (0.3 + 0.06) * 2 + 0.28 = 1 groupSpace、barSpace、barWidth计算公式，保证等于1
//                if (formatType == DEFAULT_FORMAT) {
                    groupSpace = 0.28f;
                    barSpace = 0.06f;
                    barWidth = 0.3f;
//                } else {
//                    groupSpace = 0.16f;
//                    barSpace = 0.1f;
//                    barWidth = 0.32f;
//                }
                break;
            case 3:
                groupSpace = 0.1f;
                barSpace = 0.03f;
                barWidth = 0.27f;
                // (0.27 + 0.03) * 3 + 0.1 = 1
                break;
            default:
                break;
        }

        float startX = 0.f;
        Common.log_d("201808011531", "startX = " + startX + "->groupCount = " + groupCount);

        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startX);
        chart.getXAxis().setAxisMaximum(startX + chart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        chart.groupBars(startX, groupSpace, barSpace);
        chart.invalidate();
        chart.animateY(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear);
    }
}
