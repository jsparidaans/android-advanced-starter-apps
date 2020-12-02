package com.dhl_myid.customfancontroller.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

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
    }



    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        // Calculate the radius
        mWidth = width;
        mHeight = height;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);

    }
}
