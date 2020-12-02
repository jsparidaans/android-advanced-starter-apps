package com.dhl_myid.customfancontroller.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;

public class DialView extends View {

    //Total selection options
    private static int SELECTION_COUNT = 4;

    //Custom view dimensions
    private float mWidth;
    private float mHeight;
    private float mRadius;

    //Paint for text and dial
    private Paint mTextPaint;
    private Paint mDialPaint;

    //Current selection
    private int mActiveSelection;

    //StringBuffer for dial labels and float array for computing
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(Color.GRAY);
        mActiveSelection = 0;

        //Set up onclick listener
        setOnClickListener(v -> {
            //Rotate to the next choice
            mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT;

            //Set dial background to green if selection not 0
            if (mActiveSelection >= 1) {
                mDialPaint.setColor(GREEN);
            } else {
                mDialPaint.setColor(GRAY);
            }
            invalidate();
        });
    }

    private float[] computeXYForPosition(final int position, final float radius) {
        float[] result = mTempResult;

        //Calculate angle in radians
        double startAngle = Math.PI * (9 / 8d);
        double angle = startAngle + (position * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
        result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw dial
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint);

        //Draw text labels
        final float labelRadius = mRadius + 20;
        StringBuffer label = mTempLabel;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }

        //Draw indicator
        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActiveSelection, markerRadius);

        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        // Calculate the radius
        mWidth = width;
        mHeight = height;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);

    }
}
