package com.example.bertumcamera;

import android.content.res.Resources;
import android.util.TypedValue;
import android.content.Context;

public class Converters {

    public static int dpToPixels(Context mContext, int valueDp) {
        Resources r = mContext.getResources();
        int valuePx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueDp,
                r.getDisplayMetrics()
        );
        return valuePx;
    }
}
