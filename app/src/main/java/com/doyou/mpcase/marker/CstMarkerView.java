package com.doyou.mpcase.marker;

import android.content.Context;
import android.widget.TextView;

import com.doyou.mpcase.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

/**
 * 自定义标记视图
 * @autor hongbing
 * @date 2018/10/12
 */
public class CstMarkerView extends MarkerView {

    private TextView mValue;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public CstMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mValue = findViewById(R.id.marker_value);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            mValue.setText(Utils.formatNumber(ce.getHigh(), 0, false));
        } else {
            mValue.setText(Utils.formatNumber(e.getY(), 0, false));
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2),-getHeight()-24);
    }
}
