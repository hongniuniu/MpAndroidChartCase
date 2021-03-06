package com.doyou.mpcase.fragment.linechart;

import android.graphics.Color;
import android.util.Log;

import com.dongni.tools.EmptyUtils;
import com.doyou.mpcase.R;
import com.doyou.mpcase.marker.CstMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 折线图相关数据逻辑处理类
 *
 * @autor hongbing
 * @date 2018/10/11
 */
public final class LineChartModule {

    static DecimalFormat sDecimalFormat;

    static {
        sDecimalFormat = new DecimalFormat("#.##%"); // 百分比小数，精确两位小数
    }

    /**
     * 图表折线图动画时间
     */
    static int CHART_ANIM_LINE_DURATION = 1200;
    static final int MOCK_COUNT = 3;

    interface VALUE_TYPE {
        String DAY = "day";
        String WEEK = "week";
        String MONTH = "month";
        String YEAR = "year";
        String SEASONS = "seasons"; // 四季
        String MORE = "more";
    }

    public static int getCountByValueType(String valueType) {
        return xValuesProcess(valueType).length;
    }

    static String[] MOCK_YEARS = {"2016", "2017", "2018"};


    static int[] COLORS = {Color.rgb(69, 113, 214), Color.rgb(101, 226, 175),
            Color.rgb(255, 196, 0), Color.rgb(255, 105, 83),
            Color.rgb(89, 210, 252), Color.rgb(255, 140, 73), Color.rgb(255, 50, 68)};

    public static String[] xValuesProcess(String valueType) {
        switch (valueType) {
            case VALUE_TYPE.DAY:
                return new String[]{"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00",
                        "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                        "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
            case VALUE_TYPE.WEEK:
                return new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            case VALUE_TYPE.MONTH:
                return new String[]{"10-01", "10-02", "10-03", "10-04", "10-05", "10-06", "10-07", "10-08",
                        "10-09", "10-10", "10-11", "10-12", "10-13", "10-14", "10-15", "10-16",
                        "10-17", "10-18", "10-19", "10-20", "10-21", "10-22", "10-23", "10-24",
                        "10-25", "10-26", "10-27", "10-28", "10-29", "10-30", "10-31"};
            case VALUE_TYPE.YEAR:
                return new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
            case VALUE_TYPE.SEASONS:
                return new String[]{"春", "夏", "秋", "冬"};
            case VALUE_TYPE.MORE:
                return new String[]{
                        "10-01", "10-02", "10-03", "10-04", "10-05", "10-06", "10-07", "10-08", "10-09",
                        "10-10", "10-11", "10-12", "10-13", "10-14", "10-15",
                        "10-16", "10-17", "10-18", "10-19", "10-20", "10-21", "10-22", "10-23", "10-25",
                        "10-26", "10-27", "10-28", "10-29", "10-30", "10-31", "11-01", "11-02", "11-03",
                        "11-04", "11-05", "11-06", "11-07", "11-08", "11-09",
                        "11-10", "11-11", "11-12", "11-13", "11-14", "11-15", "11-16", "11-17", "11-18",
                        "11-19", "11-20", "11-21", "11-22", "11-23", "11-24",
                        "11-25", "11-26", "11-27", "11-28", "11-29", "11-30", "12-01", "12-02", "12-03",
                        "12-04", "12-05", "12-06", "12-07", "12-08", "12-09",
                        "12-10", "12-11", "12-12", "12-13", "12-14", "12-15", "12-16", "12-17", "12-18",
                        "12-19", "12-20", "12-21", "12-22", "12-23", "12-24",
                        "12-25", "12-26", "12-27", "12-28", "12-29", "12-30", "12-31", "01-01", "01-02"
                };
            default:
                break;
        }
        return null;
    }

    /**
     * 添加限制线
     *
     * @param axis
     * @param mean
     */
    public static void addLimitLine(YAxis axis, float mean) {
        addLimitLine(axis, mean, "");
    }

    /**
     * 添加限制线
     *
     * @param axis
     * @param mean
     * @param label
     */
    public static void addLimitLine(YAxis axis, float mean, String label) {
//        LimitLine avgLine = new LimitLine(Float.valueOf(sDecimalFormat.format(mean)));
        if (EmptyUtils.isNotEmpty(axis.getLimitLines())) {
            axis.getLimitLines().remove(0);
        }
        LimitLine avgLine = new LimitLine(mean);
        avgLine.enableDashedLine(5.0f, 3.0f, 3.0f); // 虚线
        avgLine.setLineColor(Color.rgb(255, 196, 1)); // 线颜色
        if (EmptyUtils.isNotEmpty(label)) {
            avgLine.setLabel(label);
            avgLine.setTextColor(Color.rgb(255, 196, 1));
        }
        axis.addLimitLine(avgLine);
    }

    private static CstMarkerView cmv;
    private static void creatMarkerView(LineChart chart) {
        if (cmv == null) {
            cmv = new CstMarkerView(chart.getContext(), R.layout.chart_marker_view);
        } else {
            if (cmv.getChartView() != null) {
                cmv.getChartView().removeAllViewsInLayout();
            }
        }
        cmv.setChartView(chart);
        chart.setMarker(cmv);
    }

    /**
     * 资源释放
     */
    public static void release() {
        cmv = null;
    }

    /**
     * 动态获取x轴最终label个数
     * @param chart
     * @param entries
     * @param isFill 是否填充
     * @return
     */
    private static Object[] caclEndLabelCount(LineChart chart, List<Entry> entries,boolean isFill) {
        LineDataSet dataSet = new LineDataSet(entries);
        dataSet.setLineWidth(0.8f); // 曲线，线宽
        dataSet.setCircleRadius(2.f);
        dataSet.setColor(Color.rgb(69, 113, 214)); // 曲线颜色自定义
        dataSet.setCircleColor(Color.rgb(69, 113, 214)); // 曲线上的圆点颜色自定义
        dataSet.setDrawFilled(isFill); // 填充统计点下方的区域
        if (isFill) {
            dataSet.setFillColor(Color.rgb(95, 124, 252)); // 填充区域颜色自定义
        }
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); // 曲线
        dataSet.setDrawValues(false);// 隐藏每条数据的顶部值,默认显示
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));// 高亮颜色,默认RGB(255, 187, 115)
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        int endLabelCount = chart.getXAxis().mEntryCount;
        Object object[] = new Object[]{endLabelCount, entries};
        return object;
    }

    /**
     * 计算统计点 | 数据更新 | 数据填充
     * @param entries 统计点集合对象
     * @param isMarker 是否需要显示标注，在点击的时候
     * @return
     */
    private static void setDataLineBySub(LineChart chart, List<Entry> entries, boolean isMarker) {

        if (isMarker) {
            creatMarkerView(chart);
        }

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) { // 数据更新
            Log.d("201810151101", "->mEntryCount->3 = " + chart.getXAxis().mEntryCount);
            LineDataSet dataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        }

        Log.d("201810151101", "->mEntryCount->2 = " + chart.getXAxis().mEntryCount + "->chart.getData().getEntryCount()" + chart.getData().getEntryCount());
        chart.animateX(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear);
    }


    /**
     * 计算统计点 | 数据更新 | 数据填充
     * @return
     */
    private static void setDataLine(LineChart chart, List<Entry> entries, boolean isFill,boolean isMarker) {
        if (isMarker) {
            creatMarkerView(chart);
        }
        LineDataSet dataSet;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) { // 数据更新
            dataSet = (LineDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(entries);
            dataSet.setDrawFilled(isFill); // 填充统计点下方的区域
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else { // 数据初始填充
            dataSet = new LineDataSet(entries, "客流量折线图统计");
            dataSet.setLineWidth(0.8f); // 曲线，线宽
            dataSet.setCircleRadius(2.f);
            dataSet.setColor(Color.rgb(69, 113, 214)); // 曲线颜色自定义
            dataSet.setCircleColor(Color.rgb(69, 113, 214)); // 曲线上的圆点颜色自定义
            dataSet.setDrawFilled(isFill); // 填充统计点下方的区域
            dataSet.setFillColor(Color.rgb(95, 124, 252)); // 填充区域颜色自定义
            dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); // 曲线
//            dataSet.setDrawHighlightIndicators(true);   // 画高亮指示器,默认true
            dataSet.setHighLightColor(Color.rgb(244, 117, 117));// 高亮颜色,默认RGB(255, 187, 115)
            dataSet.setDrawValues(false);// 隐藏每条数据的顶部值,默认显示
            dataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return (int) value + "";
                }
            });
            dataSet.setValueTextColor(Color.rgb(153, 153, 153)); // 设置每条数据的顶部值的颜色
            dataSet.setValueTextSize(9); // 设置每条数据的顶部值的大小
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
        }

        Log.d("201810151101","->mEntryCount->2 = " + chart.getXAxis().mEntryCount + "->chart.getData().getEntryCount()" + chart.getData().getEntryCount());
        chart.animateX(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear);
    }

    /**
     * 更新图表
     * @param chart     图表
     * @param valueType 模拟数据类型
     * @param isFill 是否填充底部区域
     */
    public static void notifyDataToLine(LineChart chart, String valueType, boolean isFill) {
        notifyDataToLine(chart, valueType, isFill,false);
    }

    /**
     * 更新图表
     * @param chart     图表
     * @param valueType 模拟数据类型
     * @param isFill 是否填充底部区域
     */
    public static void notifyDataToLine(LineChart chart, String valueType, boolean isFill,boolean isMarker) {
        notifyDataToLine(chart, valueType,0f, isFill,isMarker);
    }

    /**
     * 更新图表
     * @param chart     图表
     * @param valueType 模拟数据类型
     * @param mean      平均数
     * @param isFill    是否填充背景
     * @param isMarker 是否需要显示marker
     */
    public static void notifyDataToLine(LineChart chart, String valueType, float mean, boolean isFill,boolean isMarker) {
        int maxValue = 0; // 图表数据最大值
        final String[] xVals = xValuesProcess(valueType);
        final List<Entry> yVals = new ArrayList<>(xVals.length);
        int xValsLength = xVals.length;
        for (int i = 0; i < xValsLength; i++) {
            int y = (int) (Math.random() * 85) + 40;
            yVals.add(new Entry(i, y));
            maxValue = Math.max(maxValue, y);
        }

        chart.getAxisLeft().setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(xVals.length);// 最后一条数据绘制不出来的时候，设置这个可以解决问题
        if (xVals.length < 6) {
            chart.getXAxis().setLabelCount(xVals.length);
        }

        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() { // 自定义x轴显示内容
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value >= xVals.length || (int) value < 0) {
                    return "";
                }
                String x = xVals[(int) value];
//                Log.d("201810151101", "x轴value = " + value + "-->xvalue = " + x);
                return x;
            }
        });
        if (mean > maxValue) { // 重新设置y轴最大值，当平均数大于图表数据最大值的时候
            chart.getAxisLeft().setAxisMaximum(mean + 6);
        } else { // 记得回刷y轴坐标值
            chart.getAxisLeft().setAxisMaximum(maxValue + 6);
        }

        Log.d("201810151101","->mEntryCount = " + chart.getXAxis().mEntryCount);
        setDataLine(chart, yVals, isFill,isMarker);
    }


    /**
     * 更新图表,一屏分段显示,函数重载
     * @param chart
     * @param valueType
     * @param isFill
     * @param isMarker
     */
    public static void notifyDataToLineBySub(LineChart chart, String valueType, boolean isFill,boolean isMarker) {
        notifyDataToLineBySub(chart,valueType,0f,isFill,isMarker);
    }

    /**
     * 更新图表,一屏分段显示
     * @param chart     图表
     * @param valueType 模拟数据类型
     * @param mean      平均数
     * @param isFill    是否填充背景
     * @param isMarker 是否需要显示标注，点击的时候
     */
    public static void notifyDataToLineBySub(LineChart chart, String valueType, float mean, boolean isFill,boolean isMarker) {
        int maxValue = 0; // 图表数据最大值
        final String[] xVals = xValuesProcess(valueType);
        final List<Entry> yVals = new ArrayList<>(xVals.length);

        // 1.图表数据组装
        int xValsLength = xVals.length;
        for (int i = 0; i < xValsLength; i++) {
            int y = (int) (Math.random() * 85) + 40;
            yVals.add(new Entry(i, y, false));
            maxValue = Math.max(maxValue, y);
        }

        // 2.图表数据初始化
        Object[] objs = caclEndLabelCount(chart, yVals,isFill);
        // 分段数
        int subCount = (int) objs[0];
        // 那么每段之间的间隔值
        subCount = xValsLength / subCount;
        if (xValsLength % subCount > 0) {
            subCount = subCount + 1;
        }

        // 3.更新统计点对象Entry，哪些是需要显示的点
        List<Entry> entries = (List<Entry>) objs[1];
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = entries.get(i);
            boolean isNeedCircle = (i % subCount == 0);
            if (isNeedCircle) {
                Log.d("201810151101", "需要圆点的索引 = " + i
                        + "->label = " + xVals[i] + "->subCount = " + subCount
                        + "->xValsLength = " + xValsLength);
                entry.setCircle(true);
            }
        }

        // 4.x轴值，自定义
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() { // 自定义x轴显示内容
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if ((int) value >= xVals.length || (int) value < 0) {
                    return "";
                }
                String x = xVals[(int) value];
//                Log.d("201810151101", "x轴value = " + value + "-->xvalue = " + x);
                return x;
            }
        });

        // 5.平均线线处理
        if (mean > maxValue) { // 重新设置y轴最大值，当平均数大于图表数据最大值的时候
            chart.getAxisLeft().setAxisMaximum(mean + 6);
        } else { // 记得回刷y轴坐标值
            chart.getAxisLeft().setAxisMaximum(maxValue + 6);
        }

        // 6.数据刷新
        setDataLineBySub(chart, yVals,isMarker);
    }

    /**
     * 计算统计点 | 数据更新 | 数据填充
     *
     * @return
     */
    private static void setDataLineByMultiple(LineChart chart, List<List<Entry>> setList, List<String> labels) {
        LineDataSet set = null;
        List<ILineDataSet> idsets = new ArrayList<>(setList.size());
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            chart.clearValues();
            chart.setData(null);
            chart.notifyDataSetChanged();
            chart.invalidate();
        }

        for (int i = 0; i < setList.size(); i++) {
            set = new LineDataSet(setList.get(i), labels.get(i)); // 每条线的对象
            int color = Color.rgb(255, 50, 68);
            switch (i) {
                case 0:
                    color = Color.rgb(69, 113, 214);
                    break;
                case 1:
                    color = Color.rgb(101, 226, 175);
                    break;
                case 2:
                    color = Color.rgb(255, 196, 0);
                    break;
                case 3:
                    color = Color.rgb(255, 105, 83);
                    break;
                case 4:
                    color = Color.rgb(89, 210, 252);
                    break;
                case 5:
                    color = Color.rgb(255, 140, 73);
                    break;
                default:
                    break;
            }
            set.setColor(color); // 曲线颜色
            set.setCircleColor(color); // 曲线上的圆点颜色
            set.setCircleRadius(2.f); // 点的半径
            set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); // 曲线
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setDrawCircleHole(false); // 是否空心，false实心 true：空心，默认true
            set.setDrawValues(true);// 显示每条数据的顶部值
            set.setValueFormatter(new IValueFormatter() { // 显示的统计点的y轴的值格式化
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return sDecimalFormat.format(value);
                }
            });
            idsets.add(set);
        }

        LineData data = new LineData(idsets);
        data.setValueTextColor(Color.rgb(153, 153, 153));
        data.setValueTextSize(9f);
        chart.setData(data);
        chart.animateX(CHART_ANIM_LINE_DURATION, Easing.EasingOption.Linear); // 图表显示动画
    }

    public static void notifyDataToLineByMultiple(LineChart chart) {
        // 申明外部集合
        List<String> labels = new ArrayList<>(MOCK_COUNT);
        List<List<Entry>> setList = new ArrayList<>(MOCK_COUNT);
        final String[] xVals = xValuesProcess(VALUE_TYPE.YEAR);
        final List<Entry> yVals1 = new ArrayList<>(xVals.length);
        final List<Entry> yVals2 = new ArrayList<>(xVals.length);
        final List<Entry> yVals3 = new ArrayList<>(xVals.length);
        for (int i = 0; i < MOCK_YEARS.length; i++) {
            labels.add(MOCK_YEARS[i]);
        }
        for (int i = 0; i < xVals.length; i++) {
            yVals1.add(new Entry(i, (float) (Math.random() * 0.2415)));
            yVals2.add(new Entry(i, (float) (Math.random() * 0.3214)));
            yVals3.add(new Entry(i, (float) (Math.random() * 0.1234)));
        }
        setList.add(yVals1);
        setList.add(yVals2);
        setList.add(yVals3);

        chart.getXAxis().setAxisMaximum(xVals.length);// 最后一条数据绘制不出来的时候，设置这个可以解决问题
        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() { // 自定义x轴显示内容
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value >= xVals.length || value < 0) {
                    return "";
                }
                String x = xVals[(int) value];
                Log.d("201806291546", "value = " + value + "-->xvalue = " + x);
                return x;
            }
        });
        chart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() { // 自定义y轴并格式化坐标值
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sDecimalFormat.format(value);
            }
        });
        setDataLineByMultiple(chart, setList, labels);
    }


}
