package com.dhl_myid.clippingexample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dhl_myid.clippingexample.R;

public class ClippedView extends View {

    private final RectF rectF;
    private Paint paint;
    private Path path;
    private int clipRectRight = (int) getResources().getDimension(R.dimen.clipRectRight);
    private int clipRectBottom = (int) getResources().getDimension(R.dimen.clipRectBottom);
    private int clipRectTop = (int) getResources().getDimension(R.dimen.clipRectTop);
    private int clipRectLeft = (int) getResources().getDimension(R.dimen.clipRectLeft);
    private int rectInset = (int) getResources().getDimension(R.dimen.rectInset);
    private int smallRectOffset = (int) getResources().getDimension(R.dimen.smallRectOffset);
    private int circleRadius = (int) getResources().getDimension(R.dimen.circleRadius);
    private int textOffset = (int) getResources().getDimension(R.dimen.textOffset);
    private int textSize = (int) getResources().getDimension(R.dimen.textSize);
    //Columns
    private int columnOne = rectInset;
    private int columnTwo = columnOne + rectInset + clipRectRight;
    //Rows
    private int rowOne = rectInset;
    private int rowTwo = rowOne + rectInset + clipRectBottom;
    private int rowThree = rowTwo + rectInset + clipRectBottom;
    private int rowFour = rowThree + rectInset + clipRectBottom;
    private int textRow = rowFour + (int) (1.5 * clipRectBottom);

    public ClippedView(Context context) {
        this(context, null);
    }

    public ClippedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth((int) getResources().getDimension(R.dimen.strokeWidth));
        paint.setTextSize((int) getResources().getDimension(R.dimen.textSize));

        path = new Path();
        rectF = new RectF(new Rect(rectInset, rectInset, clipRectRight - rectInset, clipRectBottom - rectInset));

    }

    private void drawClippedRectangle(Canvas canvas) {

        //Set the boundaries
        canvas.clipRect(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom);
        //Fill the canvas with white
        canvas.drawColor(Color.WHITE);

        //Draw a red line
        paint.setColor(Color.RED);
        canvas.drawLine(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom, paint);

        //Draw green circle
        paint.setColor(Color.GREEN);
        canvas.drawCircle(circleRadius, clipRectBottom - circleRadius, circleRadius, paint);

        //Draw blue text along the right edge
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(getContext().getString(R.string.clipping), clipRectRight, textOffset, paint);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        canvas.save();
        canvas.translate(columnOne, rowOne);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(columnTwo, rowOne);
        canvas.clipRect(2 * rectInset, 2 * rectInset, clipRectRight - 2 * rectInset, clipRectBottom - 2 * rectInset);

        canvas.clipOutRect(4 * rectInset, 4 * rectInset,
                clipRectRight - 4 * rectInset,
                clipRectBottom - 4 * rectInset);
        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(columnOne, rowTwo);

        path.rewind();
        path.addCircle(circleRadius, clipRectBottom - circleRadius, circleRadius, Path.Direction.CCW);
        canvas.clipOutPath(path);

        drawClippedRectangle(canvas);
        canvas.restore();

        canvas.save();
        canvas.translate(columnTwo, rowTwo);
        canvas.clipRect(clipRectLeft, clipRectTop, clipRectRight - smallRectOffset, clipRectBottom - smallRectOffset);

        drawClippedRectangle(canvas);
        canvas.restore();
    }
}
