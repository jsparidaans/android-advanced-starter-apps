package com.dhl_myid.customfancontroller.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dhl_myid.customfancontroller.R;

import static android.graphics.Color.CYAN;
import static android.graphics.Color.GRAY;

public class DialView extends View {

    //Total selection options
    private static int SELECTION_COUNT = 4;
    private int mSelectionCount;

    //Custom view dimensions
    private float mWidth;
    private float mHeight;
    private float mRadius;

    //Paint for text and dial
    private Paint mTextPaint;
    private Paint mDialPaint;

    //Attribute set for custom attributes
    AttributeSet attrs;
    //Fan on and off colors
    private int mFanOnColor;

    //Current selection
    private int mActiveSelection;

    //StringBuffer for dial labels and float array for computing
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];
    private int mFanOffColor;

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        init();
    }

    private void init() {
        mFanOffColor = GRAY;
        mFanOnColor = CYAN;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(mFanOffColor);
        mActiveSelection = 0;

        //Get custom attributes
        if (attrs != null) {
            Context context;
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                    R.styleable.DialView,
                    0,
                    0);

            //Set number of selections
            mSelectionCount = typedArray.getInt(R.styleable.DialView_selectionIndicators, mSelectionCount);

            //Set the colors
            mFanOnColor = typedArray.getColor(R.styleable.DialView_fanOnColor, mFanOnColor);
            mFanOffColor = typedArray.getColor(R.styleable.DialView_fanOffColor, mFanOffColor);

            typedArray.recycle();
        }

        //Set up onclick listener
        setOnClickListener(v -> {
            //Rotate to the next choice
            mActiveSelection = (mActiveSelection + 1) % mSelectionCount;

            //Set dial background to green if selection not 0
            if (mActiveSelection >= 1) {
                mDialPaint.setColor(mFanOnColor);
            } else {
                mDialPaint.setColor(mFanOffColor);
            }
            invalidate();
        });
    }

    private float[] computeXYForPosition(final int position, final float radius, boolean isLabel) {
        float[] result = mTempResult;
        double startAngle;
        double angle;
        if (mSelectionCount > 4) {
            //Calculate angle in radians
            startAngle = Math.PI * (9 / 8d);
            angle = startAngle + (position * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle * 2)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle * 2)) + (mHeight / 2);
            if ((angle > Math.toRadians(360)) && isLabel) {
                result[1] += 20;
            }
        } else {
            startAngle = Math.PI * (9 / 8d);
            angle = startAngle + (position * (Math.PI / mSelectionCount));
            result[0] = (float) (radius * Math.cos(angle)) + (mWidth / 2);
            result[1] = (float) (radius * Math.sin(angle)) + (mHeight / 2);
        }
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
        for (int i = 0; i < mSelectionCount; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius, true);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, mTextPaint);
        }

        //Draw indicator
        final float markerRadius = mRadius - 35;
        float[] xyData = computeXYForPosition(mActiveSelection, markerRadius, false);

        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, mTextPaint);
    }

    public void setSelectionCount(int mSelectionCount) {
        this.mSelectionCount = mSelectionCount;
        this.mActiveSelection = 0;
        mDialPaint.setColor(mFanOffColor);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        // Calculate the radius
        mWidth = width;
        mHeight = height;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);

    }
}
