package com.dhl_myid.canvasexample.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.dhl_myid.canvasexample.R;


public class CanvasView extends View {
    private Paint paint;
    private Path path;
    private int drawColor;
    private int backgroundColor;
    private Rect frame;
    private Canvas extraCanvas;
    private Bitmap extraBitmap;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.opaque_orange, null);
        drawColor = ResourcesCompat.getColor(getResources(), R.color.opaque_yellow, null);


        path = new Path();
        paint = new Paint();
        paint.setColor(drawColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);

    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        extraCanvas = new Canvas(extraBitmap);
        extraCanvas.drawColor(backgroundColor);

        int inset = 40;
        frame = new Rect(inset, inset, width - inset, height - inset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(extraBitmap, 0, 0, null);

        canvas.drawRect(frame, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }

        return true;
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float deltaX = Math.abs(x - mX);
        float deltaY = Math.abs(y - mY);
        if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);

            mX = x;
            mY = y;
            extraCanvas.drawPath(path, paint);
        }
    }

    private void touchUp() {
        path.reset();
    }
}
