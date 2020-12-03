package com.dhl_myid.surfaceviewexample.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Context context;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private int bitmapX, bitmapY;
    private RectF winnerRect;
    private double viewWidth;
    private double viewHeight;


    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        path = new Path();
    }

    private void setUpBitmap() {
        bitmapX = (int) Math.floor(Math.random() * (viewWidth - bitmap.getWidth()));
        bitmapY = (int) Math.floor(Math.random() * (viewHeight - bitmap.getHeight()));
        winnerRect = new RectF(bitmapX, bitmapY,
                bitmapX + bitmap.getWidth(),
                bitmapY + bitmap.getHeight());
    }

    public void pause() {
        //stub
    }

    public void resume() {
        //stub
    }

    @Override
    public void run() {
        //stub
    }

}
