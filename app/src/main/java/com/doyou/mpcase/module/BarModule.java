package com.doyou.mpcase.module;

import android.graphics.Color;
import android.util.Log;

import com.doyou.mpcase.module.base.BaseBarModule;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图模拟数据
 * @autor hongbing
 * @date 2018/11/01
 *
 * 圆角柱状图参考：https://blog.csdn.net/u013947002/article/details/78004178
 */
public final class BarModule extends BaseBarModule {

    private static DecimalFormat bFormat; // 百分比
    static {
        bFormat = new DecimalFormat("#.##%"); // 百分比整数,如果有小数精确到后两位，否则按整数返回
    }

    public interface BAR_TYPE {
        int AGE = 1;
        int MONTH = 2;
        int DATE = 3;
        int TEST = 4;
    }

    public static String[] getBarXValues(int barType) {
        String[] mBarXValues = new String[0];
        switch (barType) {
            case BAR_TYPE.TEST:
                mBarXValues = new String[]{"06-11", "06-12", "06-13"};
                break;
            case BAR_TYPE.AGE:
                mBarXValues = new String[]{"20以下", "20-29", "30-39", "40-49", "50-59", "60以上"};
                break;
            case BAR_TYPE.MONTH:
                mBarXValues = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
                break;
            case BAR_TYPE.DATE:
                mBarXValues = new String[]{"06-11", "06-12", "06-13", "06-14", "06-15", "06-16", "06-17"};
                break;
            default:
                break;
        }
        return mBarXValues;
    }

    /**
     * 刷新柱形图数据
     * @param barType
     * @param chart
     */
    public static void notifyDataToBar(int barType,BarChart chart) {
        notifyDataToBar(barType,chart,0.86f, Color.rgb(88, 181, 250), true,DEFAULT_FORMAT);
    }

    /**
     * 刷新柱形图数据
     * @param barType
     * @param chart           图表
     * @param barColor        柱子颜色
     * @param isShowDrawValue 是否显示柱子上的数字
     * @param formatType 格式类型
     */
    public static void notifyDataToBar(int barType, BarChart chart, float barRatio, int barColor, boolean isShowDrawValue, final int formatType) {
        final String barValues[] = getBarXValues(barType);
        final int length = barValues.length;
        List<BarEntry> yValues = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            yValues.add(new BarEntry(i, (float) (Math.random() * 70) + 30));
        }
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getXAxis().setLabelCount(length); // 不设置的话会出现label显示不全的问题
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("柱状图X轴", "value = " + value);
                if ((int) value >= length || (int) value < 0) {
                    return "";
                }
                return barValues[(int) value];
            }
        });
        generateDataBar(chart, yValues, barRatio,barColor, isShowDrawValue,formatType);
        chart.animateY(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear);
    }

    /**
     * 刷新柱形图数据
     * @param barType 柱状图展示的数据类型
     * @param chart
     * @param formatType 数据格式类型
     */
    public static void notifyDataToBarByMultiple(int barType, BarChart chart, final int formatType, boolean isShowDrawValue) {
        final String[] xVals = getBarXValues(barType);
        final int size = xVals.length;
        final List<List<BarEntry>> setList = new ArrayList<>(size);
        final List<String> labels = new ArrayList<>(size);
        switch (barType) {
            case BAR_TYPE.AGE:
                List<BarEntry> yVals1 = new ArrayList<>(size);
                List<BarEntry> yVals2 = new ArrayList<>(size);
                for (int i = 0; i < size; i++) { // 柱子个数
                    yVals1.add(new BarEntry(i, (float) (Math.random() * 62)));
                    yVals2.add(new BarEntry(i, (float) (Math.random() * 66)));
                    labels.add(xVals[i]);
                }
                setList.add(yVals1);
                setList.add(yVals2);
                break;
            case BAR_TYPE.TEST:
                List<BarEntry> tVals1 = new ArrayList<>(size);
                List<BarEntry> tVals2 = new ArrayList<>(size);
                List<BarEntry> tVals3 = new ArrayList<>(size);
                for (int i = 0; i < size; i++) { // 柱子个数
                    tVals1.add(new BarEntry(i, (float) (Math.random() * 62)));
                    tVals2.add(new BarEntry(i, (float) (Math.random() * 66)));
                    tVals3.add(new BarEntry(i, (float) (Math.random() * 64)));
                    labels.add(xVals[i]);
                }
                setList.add(tVals1);
                setList.add(tVals2);
                setList.add(tVals3);
                break;
            default:
                break;
        }
        chart.getXAxis().setLabelCount(size); // 不设置的话会出现label显示不全的问题
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value >= size || value < 0) {
                    return "";
                }
                return xVals[(int) value];
            }
        });
        generateDataBarByMult(chart, setList, labels, size,isShowDrawValue,formatType);
    }

}
