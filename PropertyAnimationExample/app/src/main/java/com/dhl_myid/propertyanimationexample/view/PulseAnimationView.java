package com.dhl_myid.propertyanimationexample.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PulseAnimationView extends View {

    //Constants
    private static final int COLOR_ADJUSTER = 5;
    private final Paint paint = new Paint();
    private float radius;

    public void setRadius(float radius) {
        this.radius = radius;
        paint.setColor(Color.GREEN + (int) radius / COLOR_ADJUSTER);
        invalidate();
    }

    public PulseAnimationView(Context context) {
        this(context, null);
    }

    public PulseAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
